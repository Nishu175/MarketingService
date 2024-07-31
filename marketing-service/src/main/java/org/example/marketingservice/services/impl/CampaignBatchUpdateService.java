package org.example.marketingservice.services.impl;

import org.example.marketingservice.exceptions.ResourceNotFoundException;
import org.example.marketingservice.models.Campaign;
import org.example.marketingservice.models.enums.FrequencyType;
import org.example.marketingservice.models.enums.Status;
import org.example.marketingservice.repositories.CampaignRepository;
import org.springframework.stereotype.Service;

@Service
public class CampaignBatchUpdateService {
    private final CampaignRepository campaignRepository;

    public CampaignBatchUpdateService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }


    public void updateCampaignStatusById(Long campaignId, Status status) {
        if (campaignRepository.findById(campaignId).isPresent()) {
            //TODO check projection query
            Campaign campaign = campaignRepository.findById(campaignId).get();
            campaign.setStatus(status);
            //update nextRunTs for recurring campaigns
            if(campaign.getFrequencyType() == FrequencyType.RECURRING && status == Status.FINISHED){
                campaign.setNextRunTs(campaign.getNextRunTs()+campaign.getCadenceInSec());
                campaign.setStatus(Status.PENDING);
            }
           campaignRepository.save(campaign);
        }
        else {
            throw new ResourceNotFoundException("campaign not found");
        }
    }
}
