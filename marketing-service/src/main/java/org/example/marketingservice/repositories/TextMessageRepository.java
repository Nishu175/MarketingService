package org.example.marketingservice.repositories;

import org.example.marketingservice.models.message.TextMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextMessageRepository extends JpaRepository<TextMessage,Long> {
}
