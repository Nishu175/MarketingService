package org.example.marketingservice.controllers;

import org.example.marketingservice.models.UserSubscriptionPreference;
import org.example.marketingservice.services.interfaces.UserSubscriptionPreferenceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preferences")
public class UserSubscriptionPreferenceController {
    private final UserSubscriptionPreferenceService userSubscriptionPreferenceService;

    public UserSubscriptionPreferenceController(UserSubscriptionPreferenceService userSubscriptionPreferenceService) {
        this.userSubscriptionPreferenceService = userSubscriptionPreferenceService;
    }
    @GetMapping("/{userId}")
    public UserSubscriptionPreference getUserSubscriptionPreferenceByUserId(@PathVariable Long userId) {
        return userSubscriptionPreferenceService.findPreferenceByUserId(userId);
    }

    @PostMapping()
    public UserSubscriptionPreference saveUserSubscriptionPreferenceByUserId(@RequestBody UserSubscriptionPreference subscriptionPreference){
        return userSubscriptionPreferenceService.save(subscriptionPreference);
    }
    // create API to change DND flag for users
}
