package org.example.marketingservice.models.providers;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("sms_provider")
public class SMSProvider extends Provider {
    private String endpoint;
    private String requestType;
    private String clientId;
    private String clientSecret;
}
