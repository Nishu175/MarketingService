package org.example.marketingservice.controllers;

import org.example.marketingservice.models.UserSubscription;
import org.example.marketingservice.services.interfaces.UserSubscriptionService;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/subscribe")
public class UserSubscriptionController {
    private final UserSubscriptionService userSubscriptionService;

    public UserSubscriptionController(UserSubscriptionService userSubscriptionService) {
        this.userSubscriptionService = userSubscriptionService;
    }

    @PostMapping("/campaigns/{campaignId}")
    public void subscribe(@PathVariable(required = true) Long campaignId,@RequestParam Long userId) {
        userSubscriptionService.subscribe(campaignId,userId);
    }
}
