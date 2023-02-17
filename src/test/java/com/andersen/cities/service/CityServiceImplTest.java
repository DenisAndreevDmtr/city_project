package com.andersen.cities.service;

import com.andersen.cities.dto.request.CityDto;
import com.andersen.cities.entity.City;
import com.andersen.cities.exception.badrequest.FileNotFoundException;
import com.andersen.cities.exception.badrequest.IncorrectFileInformationException;
import com.andersen.cities.exception.nocontent.NothingFoundException;
import com.andersen.cities.exception.badrequest.CityNotFoundException;
import com.andersen.cities.repository.CityRepository;
import com.andersen.cities.service.impl.CityServiceImpl;
import com.andersen.cities.util.CsvUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityServiceImplTest {
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CsvUtil csvUtil;
    @InjectMocks
    private CityServiceImpl cityService;

    @Test
    public void shouldSaveCities_whenUploadCityFromCsvFile() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("name", "originalName", "contentType", new byte[100]);
        List<City> cities = generateListCities();
        when(csvUtil.parseCsvCity(any(MultipartFile.class))).thenReturn(cities);
        when(cityRepository.saveAll(any())).thenReturn(cities);

        cityService.uploadCityFromCsvFile(multipartFile);

        verify(csvUtil).parseCsvCity(multipartFile);
        verify(cityRepository).saveAll(cities);
    }

    @Test
    public void shouldThrowFileNotFound_whenUploadCityFromCsvFile_givenNoFile() {
        MultipartFile multipartFile = new MockMultipartFile("name", new byte[100]);

        assertThrows(FileNotFoundException.class, () -> cityService.uploadCityFromCsvFile(multipartFile));
    }

    @Test
    public void shouldThrowIncorrectFileInformation_whenUploadCityFromCsvFile_givenEmptyFile() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("name", "originalName", "contentType", new byte[100]);
        List<City> cities = Collections.emptyList();
        when(csvUtil.parseCsvCity(any(MultipartFile.class))).thenReturn(cities);

        assertThrows(IncorrectFileInformationException.class, () -> cityService.uploadCityFromCsvFile(multipartFile));
    }

    @Test
    public void shouldThrowIncorrectFileInformation_whenUploadCityFromCsvFile_givenInvalidFileData() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("name", "originalName", "contentType", new byte[100]);
        when(csvUtil.parseCsvCity(any(MultipartFile.class))).thenThrow(new IOException());

        assertThrows(IncorrectFileInformationException.class, () -> cityService.uploadCityFromCsvFile(multipartFile));
    }

    @Test
    public void shouldSaveEditedCity_whenEditCity() {
        City city = generateCity(1, "name", "photo");
        CityDto cityDto = generateCityDto();
        when(cityRepository.findById(anyInt())).thenReturn(Optional.of(city));
        when(cityRepository.save(any())).thenReturn(city);

        cityService.editCity(cityDto);

        verify(cityRepository).findById(1);
        verify(cityRepository).save(city);
    }

    @Test
    public void shouldThrowCityNotFound_whenEditCity_givenNonExistingCity() {
        CityDto cityDto = generateCityDto();
        when(cityRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> cityService.editCity(cityDto));
    }

    @Test
    public void shouldReturnListCity_whenFindCitiesByName_givenPageParamsAndSearchParam() {
        List<City> expected = List.of(generateCity(1, "name", "photo"));
        Page<City> pageCity = new PageImpl<>(expected);
        Pageable expectedPage = PageRequest.of(1, 1);
        when(cityRepository.findCityByNameContaining(any(), any())).thenReturn(pageCity);

        List<City> actual = cityService.findCitiesByName(1, 1, "param");

        verify(cityRepository).findCityByNameContaining("param", expectedPage);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowNothingFound_whenFindCitiesByName_givenInvalidSearchParam() {
        Page<City> pageCity = new PageImpl<>(Collections.emptyList());
        when(cityRepository.findCityByNameContaining(any(), any())).thenReturn(pageCity);

        assertThrows(NothingFoundException.class, () -> cityService.findCitiesByName(1, 1, "param"));
    }

    @Test
    public void shouldReturnListCity_whenGetAllCities_givenPageParams() {
        List<City> expected = List.of(generateCity(1, "name", "photo"));
        Page<City> pageCity = new PageImpl<>(expected);
        Pageable expectedPage = PageRequest.of(1, 1);
        when(cityRepository.findAll(expectedPage)).thenReturn(pageCity);

        List<City> actual = cityService.getAllCities(1, 1);

        verify(cityRepository).findAll(expectedPage);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowNothingFound_whenGetAllCities() {
        Page<City> pageCity = new PageImpl<>(Collections.emptyList());
        Pageable paging = PageRequest.of(1, 1);

        when(cityRepository.findAll(paging)).thenReturn(pageCity);

        assertThrows(NothingFoundException.class, () -> cityService.getAllCities(1, 1));
    }

    private List<City> generateListCities() {
        City firstCity = generateCity(1, "name1", "photo1");
        City secondCity = generateCity(1, "name1", "photo1");
        return List.of(firstCity, secondCity);
    }

    private City generateCity(int id, String cityName, String cityPhoto) {
        return City.builder().id(id).name(cityName).photo(cityPhoto).build();
    }

    private CityDto generateCityDto() {
        return CityDto.builder().id(1).name("editName").photo("editPhoto").build();
    }
}