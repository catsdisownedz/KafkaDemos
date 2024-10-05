package org.example.paymentconsumer.repository;

import org.example.paymentconsumer.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
    CustomerProfile findByEmail(String email);
}
