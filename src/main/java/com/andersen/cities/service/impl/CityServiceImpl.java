package com.andersen.cities.service.impl;

import com.andersen.cities.dto.request.CityDto;
import com.andersen.cities.entity.City;
import com.andersen.cities.exception.badrequest.IncorrectFileInformationException;
import com.andersen.cities.exception.badrequest.CityNotFoundException;
import com.andersen.cities.exception.badrequest.FileNotFoundException;
import com.andersen.cities.exception.nocontent.NothingFoundException;
import com.andersen.cities.repository.CityRepository;
import com.andersen.cities.service.CityService;
import com.andersen.cities.util.CsvUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.andersen.cities.util.Constants.ID;

@Log4j
@Service
public class CityServiceImpl implements CityService {
    private CityRepository cityRepository;
    private CsvUtil csvUtil;

    public CityServiceImpl(CityRepository cityRepository, CsvUtil csvUtil) {
        this.cityRepository = cityRepository;
        this.csvUtil = csvUtil;
    }

    @Override
    public void uploadCityFromCsvFile(MultipartFile multipartFile) {
        if (multipartFile.getOriginalFilename().isEmpty()) {
            throw new FileNotFoundException();
        }
        try {
            List<City> cities = csvUtil.parseCsvCity(multipartFile);
            saveListCity(cities);
            log.info("Cities from file" + multipartFile.getOriginalFilename() + "have been saved");
        } catch (Exception e) {
            log.error("Сan`t save cities from file " + multipartFile.getOriginalFilename());
            throw new IncorrectFileInformationException();
        }
    }

    @Override
    public void editCity(CityDto cityDto) {
        Optional<City> optionalCity = cityRepository.findCityById(cityDto.getId());
        if (optionalCity.isEmpty()) {
            log.error("City with name " + cityDto.getId() + " was not found");
            throw new CityNotFoundException(cityDto.getId());
        }
        City city = optionalCity.get();
        city.setName(cityDto.getName());
        city.setPhoto(cityDto.getPhoto());
        cityRepository.save(city);
    }

    @Override
    public List<City> findCitiesByName(int pageNumber, int pageSize, String searchParameter) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(ID).ascending());
        Page<City> cityPage = cityRepository.findCityByNameContaining(searchParameter, paging);
        List<City> cityList = cityPage.getContent();
        if (cityList.size() == 0) {
            throw new NothingFoundException();
        }
        return cityList;
    }

    @Override
    public List<City> getAllCities(int pageNumber, int pageSize) {
        Pageable paging = PageRequest.of(pageNumber, pageSize, Sort.by(ID).ascending());
        Page<City> cityPage = cityRepository.findAll(paging);
        List<City> cityList = cityPage.getContent();
        if (cityList.size() == 0) {
            throw new NothingFoundException();
        }
        return cityList;
    }

    private void saveListCity(List<City> cities) {
        if (Optional.ofNullable(cities).isEmpty() || cities.size() == 0) {
            throw new IncorrectFileInformationException();
        }
        cityRepository.saveAll(cities);
    }
}