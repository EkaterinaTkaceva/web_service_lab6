package com.example.cityservice;

import com.example.cityservice.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findByNameContainingIgnoreCase(String name);
    List<City> findByCountryContainingIgnoreCase(String country);
}
