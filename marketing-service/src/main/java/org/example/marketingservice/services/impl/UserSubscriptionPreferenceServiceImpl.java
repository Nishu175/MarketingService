package org.example.marketingservice.services.impl;

import org.example.marketingservice.exceptions.InvalidRequest;
import org.example.marketingservice.models.UserSubscriptionPreference;
import org.example.marketingservice.repositories.UserSubscriptionPreferenceRepository;
import org.example.marketingservice.services.interfaces.UserSubscriptionPreferenceService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserSubscriptionPreferenceServiceImpl implements UserSubscriptionPreferenceService {
    private final UserSubscriptionPreferenceRepository userSubscriptionPreferenceRepository;

    public UserSubscriptionPreferenceServiceImpl(UserSubscriptionPreferenceRepository userSubscriptionPreferenceRepository) {
        this.userSubscriptionPreferenceRepository = userSubscriptionPreferenceRepository;
    }
    public UserSubscriptionPreference getUserSubscriptionPreference(Long id) {
        return userSubscriptionPreferenceRepository.findById(id).orElse(null);
    }

    @Override
    public UserSubscriptionPreference save(UserSubscriptionPreference userSubscriptionPreference) {
        if (Objects.isNull(userSubscriptionPreference)) {
            throw new InvalidRequest("Invalid userSubscriptionPreference details");
        }
        return userSubscriptionPreferenceRepository.save(userSubscriptionPreference);
    }

    @Override
    public UserSubscriptionPreference findPreferenceByUserId(Long userId) {
        return userSubscriptionPreferenceRepository.findByUserId(userId);
    }


}
