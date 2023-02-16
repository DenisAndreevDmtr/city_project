package com.andersen.cities.repository;

import com.andersen.cities.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findCityById(int id);

    Page<City> findCityByNameContaining(String searchName, Pageable pageable);
}