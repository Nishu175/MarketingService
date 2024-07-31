package org.example.marketingservice.controllers;

import org.example.marketingservice.models.providers.EmailProvider;
import org.example.marketingservice.models.providers.Provider;
import org.example.marketingservice.models.providers.SMSProvider;
import org.example.marketingservice.services.interfaces.ProviderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/providers")
public class ProviderController {
    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }
    @GetMapping("/{providerId}")
    public Provider getAllProviders(@PathVariable Long providerId) {
        return providerService.findById(providerId);
    }

    @GetMapping()
    public List<Provider> getAllProviders() {
        return providerService.findAllProviders();
    }

    @PostMapping("/email")
    public Provider createEmailProvider(@RequestBody EmailProvider provider) {
        return providerService.save(provider);
    }

    @PostMapping("/sms")
    public Provider createSmsProvider(@RequestBody SMSProvider provider) {
        return providerService.save(provider);
    }
}
