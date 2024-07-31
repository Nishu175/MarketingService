package org.example.marketingservice.services.impl;

import org.example.marketingservice.dispatchers.EmailDispatcher;
import org.example.marketingservice.exceptions.ResourceNotFoundException;
import org.example.marketingservice.exceptions.InvalidRequest;
import org.example.marketingservice.models.Campaign;
import org.example.marketingservice.models.User;
import org.example.marketingservice.models.UserSubscriptionPreference;
import org.example.marketingservice.models.enums.FrequencyType;
import org.example.marketingservice.models.enums.Status;
import org.example.marketingservice.models.providers.Provider;
import org.example.marketingservice.repositories.CampaignRepository;
import org.example.marketingservice.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository campaignRepository;

    @Autowired
    private UserSubscriptionService userSubscriptionService;

    @Autowired
    private  UserService userService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private EmailDispatcher emailDispatcher;

    @Autowired
    private UserSubscriptionPreferenceService userSubscriptionPreferenceService;

    public CampaignServiceImpl(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public Campaign findCampaignById(Long id) {
        return campaignRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("campaign not found"));
    }

    @Override
    public Campaign createCampaign(Campaign campaign) {
        validateSave(campaign);

        campaign.setCreatedAt(System.currentTimeMillis());
        campaign.setUpdatedAt(System.currentTimeMillis());
        campaign.setStatus(Status.CREATED);
        if(campaign.getFrequencyType() == FrequencyType.ONETIME){
            campaign.setCadenceInSec(-1);
            campaign.setNextRunTs(System.currentTimeMillis());// run ASAP
        }else{
            //run after next recurring cycle
            campaign.setNextRunTs(System.currentTimeMillis()+campaign.getCadenceInSec());
        }
        return campaignRepository.save(campaign);
    }

    private void validateSave(Campaign campaign) {
        if (Objects.isNull(campaign)) {
            throw new InvalidRequest("Invalid campaign");
        }
        if(providerService.findById(campaign.getProviderId()) == null){
            throw new InvalidRequest("Provider Not exist");
        }

        if(campaign.getMessage() == null){
            throw new InvalidRequest("campaign message is null");
        }

        if(campaign.getCreatedBy() <= 0){
            throw new InvalidRequest("Invalid CreatedBy value");

        }
    }

    @Override
    public Campaign updateCampaign(Long id,Campaign campaign) {
        if (campaignRepository.findById(id).isPresent()) {
            validateSave(campaign);
            campaign.setUpdatedAt(System.currentTimeMillis());
            return campaignRepository.save(campaign);
        }

       throw new ResourceNotFoundException("campaign not found");
    }

    @Override
    public void deleteCampaignById(Long id) {
        if (campaignRepository.findById(id).isPresent()) {
            campaignRepository.deleteById(id);
        }
        throw new ResourceNotFoundException("campaign not found");
    }

    @Override
    public Campaign findCampaignByIdAndCreatedBy(Long id, Long createdBy) {
        Campaign campaign =  campaignRepository.findByCampaignIdAndCreatedBy(id,createdBy);
        if (Objects.isNull(campaign)) {
            throw new ResourceNotFoundException("campaign not found");
        }
        return campaign;
    }

    @Override
    public List<Campaign> findAllCampaigns() {
        return campaignRepository.findAll();
    }

    @Override
    public Page<Campaign> findAllCampaigns(Pageable pageable) {
        return campaignRepository.findAll(pageable);
    }

    @Override
    public List<Campaign> findAllCampaignsByCreatedBy(Long id) {
        return campaignRepository.findAllByCreatedBy(id);
    }

    @Override
    public Page<Campaign> findAllCampaignsByCreatedBy(Long id, Pageable pageable) {
        return campaignRepository.findAllByCreatedBy(id,pageable);
    }

    @Override
    public Campaign sendCampaign(Long campaignId, Long clientId) {
        Campaign campaign = findCampaignByIdAndCreatedBy(campaignId, clientId);
        validateSendCampaign(campaignId, campaign);
        List<User> users = new ArrayList<>();
        //Lets fetch subscriber on runtime if some user has subscribed it by some other flow
        List<Long> usersIds = userSubscriptionService.findAllUsersSubscriptionsByCampaignId(campaignId).stream().map(userSubscription -> userSubscription.getUserId()).toList();
        users.addAll(userService.findAllByUserIds(usersIds));
        if(!CollectionUtils.isEmpty(campaign.getSubscribers())) {
            users.addAll(campaign.getSubscribers());
        }

        if (users.isEmpty()) {
            // we should have multiple type of exceptions but using same for simplicity
            throw new InvalidRequest("no subscribers found to send campaign");
        }

        // check for user level DND
        users = updateSubscribersAsPerCommunicationPreferences(users);

        campaign.setStatus(Status.RUNNING);

        List<String> receipts = users.stream().map(User::getEmail).toList();

        Provider provider = providerService.findById(campaign.getProviderId());

        emailDispatcher.dispatch(campaign,provider,receipts);

        return campaignRepository.save(campaign);
    }

    private List<User> updateSubscribersAsPerCommunicationPreferences(List<User> users) {
        List<User> finalUsers = new ArrayList<>();
        for (User user : users) {
            UserSubscriptionPreference preference = userSubscriptionPreferenceService.findPreferenceByUserId(user.getUserId());
            if (Objects.isNull(preference) || (!preference.isEmailDND())) {
                finalUsers.add(user);
            }
        }
        return finalUsers;
    }

    private void validateSendCampaign(Long campaignId, Campaign campaign) {
        if (campaign.getStatus() == Status.RUNNING || campaign.getStatus() == Status.STOP
             ||campaign.getStatus() == Status.FINISHED || campaign.getStatus() == Status.FAILED) {
            throw new InvalidRequest("campaign is already "+ campaign.getStatus());
        }

        if(campaign.getMessage() == null){
            throw new InvalidRequest("campaign message is null");
        }

        /**
         * RECURRING campaign can have Pending status as well and
         * assuming we have forcefully schedule recurring campaign without waiting for their nextRunTs
         */

        //Pending + one time  throw error because for oneTime it should be created only
        if (campaign.getStatus() == Status.PENDING && campaign.getFrequencyType()!= FrequencyType.RECURRING) {
            throw new InvalidRequest("Invalid status - "+ campaign.getStatus() + " for campaign " + campaignId);
        }
        //this should not happen as we have validation in create flow
        if(campaign.getProviderId() <= 0){
            throw new InvalidRequest("No provider id given");
        }
    }
}
