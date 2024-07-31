package org.example.marketingservice.services.interfaces;

import org.example.marketingservice.models.UserSubscription;

import java.util.List;

public interface UserSubscriptionService {
    UserSubscription saveUserSubscription(UserSubscription userSubscription);

    List<UserSubscription> findAllUsersSubscriptionsByCampaignId(Long campaignId);

    UserSubscription subscribe(Long campaignId, Long userId);
}
