package org.example.marketingservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name="clients")
public class Client  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long clientId;
    /*
    simple auth param for any client/system user
     */
    private long accountId;
    private String passcode;
}
