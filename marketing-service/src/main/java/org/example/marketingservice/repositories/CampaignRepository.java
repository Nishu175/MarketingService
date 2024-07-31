package org.example.marketingservice.repositories;

import org.example.marketingservice.models.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Campaign findByCampaignIdAndCreatedBy(Long campaignId, Long createdBy);
    List<Campaign> findAllByCreatedBy(Long createdBy);
    Page<Campaign> findAllByCreatedBy(Long createdBy, Pageable pageable);
}
