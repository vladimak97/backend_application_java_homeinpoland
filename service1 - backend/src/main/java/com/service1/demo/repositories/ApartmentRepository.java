package com.service1.demo.repositories;

import com.service1.demo.entities.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
    Apartment findByIdAndOwnerUsername(int id, String username);
    void deleteByIdAndOwnerUsername(int id, String wonerName);
}
