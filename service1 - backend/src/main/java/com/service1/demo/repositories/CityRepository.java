package com.service1.demo.repositories;

import com.service1.demo.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByCityName(String cityName);

}
