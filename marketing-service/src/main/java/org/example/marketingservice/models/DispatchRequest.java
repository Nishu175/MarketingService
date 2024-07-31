package org.example.marketingservice.models;

import lombok.Data;
import org.example.marketingservice.models.providers.Provider;

import java.util.List;

@Data
public class DispatchRequest {
    private Campaign campaign;
    private Provider provider;
    private List<String> receipts;
}
