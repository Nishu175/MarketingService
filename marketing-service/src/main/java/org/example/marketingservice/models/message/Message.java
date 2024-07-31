package org.example.marketingservice.models.message;

import jakarta.persistence.*;
import lombok.Data;
import org.example.marketingservice.models.Campaign;

import java.util.List;

@Entity(name = "messages")
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "messageId")
    private Long messageId;
    private String body;
    private MessageType messageType;
    private String subject;

    @ElementCollection
    @CollectionTable(name = "ccList", joinColumns = @JoinColumn(name = "messageId"))
    @Column(name = "ccList")
    private List<String> ccList;

    @ElementCollection
    @CollectionTable(name = "bccList", joinColumns = @JoinColumn(name = "messageId"))
    @Column(name = "bccList")
    private List<String> bccList;

    @OneToOne
    @JoinColumn(name = "campaignId")
    private Campaign campaignId;

}
