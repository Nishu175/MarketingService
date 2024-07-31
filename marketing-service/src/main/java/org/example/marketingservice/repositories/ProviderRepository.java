package org.example.marketingservice.repositories;

import org.example.marketingservice.models.providers.EmailProvider;
import org.example.marketingservice.models.providers.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
