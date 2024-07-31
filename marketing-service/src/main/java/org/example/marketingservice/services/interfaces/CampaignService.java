package org.example.marketingservice.services.interfaces;

import org.example.marketingservice.models.Campaign;
import org.example.marketingservice.models.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CampaignService {
    Campaign findCampaignById(Long id);

    Campaign createCampaign(Campaign campaign);

    Campaign updateCampaign(Long id,Campaign campaign);

    void deleteCampaignById(Long id);

    Campaign findCampaignByIdAndCreatedBy(Long id, Long createdBy);

    List<Campaign> findAllCampaigns();

    Page<Campaign> findAllCampaigns(Pageable pageable);

    List<Campaign> findAllCampaignsByCreatedBy(Long id);

    Page<Campaign> findAllCampaignsByCreatedBy(Long id, Pageable pageable);

    Campaign sendCampaign(Long campaignId, Long clientId);
}
