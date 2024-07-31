package org.example.marketingservice.models.providers;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name="providers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",
        discriminatorType = DiscriminatorType.STRING)
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long providerId;
    private String providerName;
    private String nickName;
    private String callbackUrl;
    private Long createdAt;
    private Long updatedAt;
    private Long createdBy;
    private String userName;
    private String password;
}
