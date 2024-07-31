package org.example.marketingservice.dispatchers;

import org.example.marketingservice.models.Campaign;
import org.example.marketingservice.models.providers.Provider;

import java.util.List;

public interface Dispatcher {
    void dispatch(Campaign campaign, Provider provider, List<String> recipients);
}
