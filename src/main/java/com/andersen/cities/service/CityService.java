package com.andersen.cities.service;

import com.andersen.cities.dto.request.CityDto;
import com.andersen.cities.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface CityService {
    void uploadCityFromCsvFile(MultipartFile multipartFile);

    void editCity(CityDto cityDto);

    Page<City> findCitiesByName(int pageNumber, int pageSize, String name);

    Page<City> getAllCities(int pageNumber, int pageSize);
}