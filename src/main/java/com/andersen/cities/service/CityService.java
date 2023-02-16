package com.andersen.cities.service;

import com.andersen.cities.dto.request.CityDto;
import com.andersen.cities.entity.City;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CityService {
    void uploadCityFromCsvFile(MultipartFile multipartFile);

    void editCity(CityDto cityDto);

    List<City> findCitiesByName(int pageNumber, int pageSize, String name);

    List<City> getAllCities(int pageNumber, int pageSize);
}