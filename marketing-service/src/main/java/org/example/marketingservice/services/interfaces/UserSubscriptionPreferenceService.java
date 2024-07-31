package org.example.marketingservice.services.interfaces;

import org.example.marketingservice.models.UserSubscriptionPreference;

public interface UserSubscriptionPreferenceService {
    UserSubscriptionPreference findPreferenceByUserId(Long userId);

    UserSubscriptionPreference save(UserSubscriptionPreference subscriptionPreference);
}
