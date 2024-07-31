package org.example.marketingservice.services.impl;

import org.example.marketingservice.exceptions.InvalidRequest;
import org.example.marketingservice.models.UserSubscription;
import org.example.marketingservice.repositories.UserSubscriptionRepository;
import org.example.marketingservice.services.interfaces.UserSubscriptionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserSubscriptionServiceImpl implements UserSubscriptionService {
    private final UserSubscriptionRepository userSubscriptionRepository;

    public UserSubscriptionServiceImpl(UserSubscriptionRepository userSubscriptionRepository) {
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    public UserSubscription findUserSubscriptionById(Long id) {
        return userSubscriptionRepository.findById(id).orElse(null);
    }

    @Override
    public UserSubscription saveUserSubscription(UserSubscription userSubscription) {
        if (Objects.isNull(userSubscription)) {
            throw new InvalidRequest("Invalid userSubscriptions details");
        }
        return userSubscriptionRepository.save(userSubscription);
    }

    @Override
    public List<UserSubscription> findAllUsersSubscriptionsByCampaignId(Long campaignId) {
        return userSubscriptionRepository.findAllByCampaignId(campaignId);
    }

    @Override
    public UserSubscription subscribe(Long campaignId, Long userId) {
        UserSubscription subscription = userSubscriptionRepository.findAllByCampaignIdAndUserId(campaignId,userId);
        if (subscription != null) {
            return subscription;
        }
        subscription = new UserSubscription();
        subscription.setCampaignId(campaignId);
        subscription.setUserId(userId);
        return userSubscriptionRepository.save(subscription);

    }
}
