package org.example.marketingservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "user_subscription_preferences")
public class UserSubscriptionPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long userId;
    private boolean emailDND;
    private boolean smsDND;
    // we can also have push notification and  user best time to receive notification
}
