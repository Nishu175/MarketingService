package org.example.marketingservice.services.interfaces;

import org.example.marketingservice.models.providers.Provider;

import java.util.List;

public interface ProviderService {
    Provider findById(Long id);

    Provider save(Provider provider);

    List<Provider> findAllProviders();
}
