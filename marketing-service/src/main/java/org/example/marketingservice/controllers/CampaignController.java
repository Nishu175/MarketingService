package org.example.marketingservice.controllers;

import org.example.marketingservice.models.Campaign;
import org.example.marketingservice.services.interfaces.CampaignService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/campaigns")
public class CampaignController {
    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    //because other client can't see other client campaign
    @GetMapping()
    public Page<Campaign> getAllCampaignsByCreatedBy(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(required = true) Long clientId,
                                                     @RequestParam(defaultValue = "updatedAt") String sortBy,
                                                     @RequestParam(defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return campaignService.findAllCampaignsByCreatedBy(clientId, pageable);

    }

    //clientID + campaignId

    @GetMapping("/{campaignId}")
    public Campaign getCampaignsByCreatedByAndId(@PathVariable Long campaignId,
                                                 @RequestParam(required = true) Long clientId) {

        return campaignService.findCampaignByIdAndCreatedBy(campaignId, clientId);

    }

    @PostMapping
    public Campaign createCampaign(@RequestBody Campaign campaign) {
        return campaignService.createCampaign(campaign);
    }

    @PatchMapping("/{campaignId}/send")
    public Campaign sendCampaign(@PathVariable Long campaignId, @RequestParam(required = true) Long clientId) {
        return campaignService.sendCampaign(campaignId, clientId);
    }
}
