package org.example.marketingservice.repositories;

import org.example.marketingservice.models.message.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailMessageRepository extends JpaRepository<EmailMessage, Long> {
}
