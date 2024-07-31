package org.example.marketingservice.repositories;

import org.example.marketingservice.models.UserSubscriptionPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubscriptionPreferenceRepository extends JpaRepository<UserSubscriptionPreference, Long> {
    UserSubscriptionPreference findByUserId(Long userId);
}
