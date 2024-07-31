package org.example.marketingservice.dispatchers;

import org.example.marketingservice.models.Campaign;
import org.example.marketingservice.models.DispatchRequest;
import org.example.marketingservice.models.providers.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailDispatcher implements Dispatcher {
    @Autowired
    private DispatchExecutor dispatchExecutor;

    @Override
    public void dispatch(Campaign campaign, Provider provider, List<String> recipients) {
        DispatchRequest dispatchRequest = new DispatchRequest();
        dispatchRequest.setCampaign(campaign);
        dispatchRequest.setProvider(provider);
        dispatchRequest.setReceipts(recipients);

        dispatchExecutor.submitDispatchRequest(dispatchRequest);
    }
}
