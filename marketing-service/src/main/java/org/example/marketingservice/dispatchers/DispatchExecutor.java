package org.example.marketingservice.dispatchers;

import org.example.marketingservice.models.DispatchRequest;
import org.example.marketingservice.models.User;
import org.example.marketingservice.models.providers.EmailProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class DispatchExecutor {
    private static final int DISPATCH_THREADS = 10;

    @Autowired
    private SendbirdClient sendbirdClient;

    private static final LinkedBlockingQueue<DispatchRequest> dispatchQueue = new LinkedBlockingQueue<>();



    private static final Executor EXECUTOR = Executors.newFixedThreadPool(DISPATCH_THREADS);


    public void submitDispatchRequest(DispatchRequest dispatchRequest) {
        dispatchQueue.add(dispatchRequest);
    }

    @Scheduled(fixedRate = 1000)
    public void dispatch() {
        while(!dispatchQueue.isEmpty()) {
            DispatchRequest request = null;

            request = dispatchQueue.poll();

            if (request != null) {
                System.out.println("dispatch request: " + request);
                List<String> sendTo = request.getCampaign().getSubscribers().stream().map(User::getEmail).toList();
                List<String> ccList = request.getCampaign().getMessage().getCcList();
                List<String> bccList = request.getCampaign().getMessage().getBccList();
                DispatchRequest finalRequest = request;
                EXECUTOR.execute(() -> {
                    try {
                        sendbirdClient.send(sendTo, ccList, bccList, finalRequest.getCampaign().getMessage(), (EmailProvider) finalRequest.getProvider(), finalRequest.getCampaign().getCampaignId());
                    } catch (Exception e) {
                        System.out.println("Error while sending email");
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }
}
