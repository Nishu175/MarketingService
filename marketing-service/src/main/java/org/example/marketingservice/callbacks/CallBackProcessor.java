package org.example.marketingservice.callbacks;

import org.example.marketingservice.models.DispatchResponse;
import org.example.marketingservice.services.impl.CampaignBatchUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class CallBackProcessor {
    @Autowired
    private CampaignBatchUpdateService campaignBatchUpdateService;

    private static final LinkedBlockingQueue<DispatchResponse> dispatchResponseQueue = new LinkedBlockingQueue<>();

    public void submitDispatchResponse(DispatchResponse dispatchResponse) {
        dispatchResponseQueue.add(dispatchResponse);
    }


    @Scheduled(fixedRate = 1000)
    public void updateCampaignPostDispatch() {

        try {
            while(!dispatchResponseQueue.isEmpty()) {
                DispatchResponse response = dispatchResponseQueue.poll();
                if (Objects.nonNull(response)) {
                    campaignBatchUpdateService.updateCampaignStatusById(response.getCampaignId(), response.getStatus());
                    System.out.println("callback done for campaign " + response.getCampaignId() + " Campaign status:" + response.getStatus());
                }
            }

        } catch (Exception e) {
            System.out.println("Unable to process sent response " + e.getStackTrace());
            e.printStackTrace();

            //we can re-try as well
        }

    }
}
