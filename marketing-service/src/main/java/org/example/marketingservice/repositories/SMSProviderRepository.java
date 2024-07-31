package org.example.marketingservice.repositories;

import org.example.marketingservice.models.providers.Provider;
import org.example.marketingservice.models.providers.SMSProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMSProviderRepository extends JpaRepository<SMSProvider, Long> {
}
