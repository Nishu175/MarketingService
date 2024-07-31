package org.example.marketingservice.repositories;

import org.example.marketingservice.models.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    List<UserSubscription> findAllByCampaignId(Long campaignId);

    UserSubscription findAllByCampaignIdAndUserId(Long campaignId, Long userId);
}
