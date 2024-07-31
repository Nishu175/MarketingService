package org.example.marketingservice.services.impl;

import org.example.marketingservice.models.providers.Provider;
import org.example.marketingservice.repositories.ProviderRepository;
import org.example.marketingservice.services.interfaces.ProviderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    private final ProviderRepository providerRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public Provider findById(Long id) {
        return providerRepository.findById(id).orElse(null);
    }

    @Override
    public Provider save(Provider provider) {
        provider.setCreatedAt(System.currentTimeMillis());
        provider.setUpdatedAt(System.currentTimeMillis());
        return providerRepository.save(provider);
    }

    @Override
    public List<Provider> findAllProviders() {
        return providerRepository.findAll();
    }
}
