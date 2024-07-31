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
@DiscriminatorValue("email_provider")
public class EmailProvider extends Provider {
    private String host;
    private int port;
    private String apiKey;
    private String fromAddress;
    private String replyAddress;
}
