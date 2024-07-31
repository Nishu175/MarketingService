package org.example.marketingservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.marketingservice.models.enums.*;
import org.example.marketingservice.models.message.Message;

import java.util.List;

@Entity(name = "campaigns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "campaignId")
    private long campaignId;
    private String campaignName;
    private String campaignDescription;
    private CampaignType campaignType;
    private Channel channel;
    private Status status;
    private FrequencyType frequencyType;//support schedule campaign
    private long providerId; // email Provider
    private long createdBy;//clientId for simplicity
    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "messageId", referencedColumnName = "messageId")
    private Message message;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_subscriptions_adhoc",
            joinColumns = @JoinColumn(name = "campaignId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private List<User> subscribers;
    private long createdAt;
    private long updatedAt;
    private long nextRunTs;
    private int cadenceInSec;//recurring interval
}
