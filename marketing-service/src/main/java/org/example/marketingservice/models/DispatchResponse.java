package org.example.marketingservice.models;

import lombok.Data;
import org.example.marketingservice.models.enums.Status;

@Data
public class DispatchResponse {
    private Long campaignId;
    private Status status;
    private String dispatchSummary;
}
