package com.andersen.cities.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.andersen.cities.dto.request.CityDto;
import com.andersen.cities.entity.City;
import com.andersen.cities.exception.badrequest.IncorrectFileInformationException;
import com.andersen.cities.exception.nocontent.NothingFoundException;
import com.andersen.cities.exception.badrequest.CityNotFoundException;
import com.andersen.cities.service.CityService;

import java.util.Collections;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {CityController.class})
@ExtendWith(SpringExtension.class)
public class CityControllerTest {

    private static final String URL = "/api/v1/city";

    private static final String EDITOR_URL = URL + "/editor";
    private static final String UPLOADER_URL = URL + "/uploader";
    private static final String VIEW_URL = URL + "/view";
    private static final String SEARCH_URL = URL + "/search";

    @Autowired
    private CityController cityController;
    @MockBean
    private CityService cityService;

    @Test
    public void shouldReturnSuccessResponse_whenUploadCitiesFromCsvFile_givenValidFile() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("name", "originalName", "contentType", new byte[100]);

        doNothing().when(cityService).uploadCityFromCsvFile(any(MultipartFile.class));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart(UPLOADER_URL)
                .file("file", multipartFile.getBytes());
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(cityController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnResponseBadRequest_whenUploadCitiesFromCsv_givenInvalidFile() throws Exception {
        MultipartFile multipartFile = new MockMultipartFile("name", "originalName", "contentType", new byte[100]);

        doThrow(new IncorrectFileInformationException()).when(cityService).uploadCityFromCsvFile(any(MultipartFile.class));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart(UPLOADER_URL)
                .file("file", multipartFile.getBytes());
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(cityController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnSuccessResponse_whenEditCity_givenValidCity() throws Exception {
        CityDto cityDto = CityDto.builder().id(1).name("name").photo("photo").build();
        String json = convertRequestDtoToJson(cityDto);

        doNothing().when(cityService).editCity(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch(EDITOR_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        MockMvcBuilders.standaloneSetup(cityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnBadRequestResponse_whenEditCity_givenInvalidCity() throws Exception {
        CityDto cityDto = CityDto.builder().id(1).name("name").photo("photo").build();
        String json = convertRequestDtoToJson(cityDto);

        doThrow(new CityNotFoundException(1)).when(cityService).editCity(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch(EDITOR_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        MockMvcBuilders.standaloneSetup(cityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldReturnSuccessResponse_whenGetAllCities_givenPageParams() throws Exception {
        Page<City> pageCity = new PageImpl<>(Collections.emptyList());
        when(cityService.getAllCities(anyInt(), anyInt())).thenReturn(pageCity);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(VIEW_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNumber", String.valueOf(1)).param("pageSize", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(cityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnSuccessResponse_whenFindCitiesByName_givenSearchParams() throws Exception {
        Page<City> pageCity = new PageImpl<>(Collections.emptyList());
        when(cityService.findCitiesByName(anyInt(), anyInt(), anyString())).thenReturn(pageCity);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(SEARCH_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNumber", String.valueOf(1)).param("pageSize", String.valueOf(1)).param("parameter", "searchParam");
        MockMvcBuilders.standaloneSetup(cityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnNoContentResponse_whenFindCitiesByName_givenInvalidSearchParams() throws Exception {
        doThrow(new NothingFoundException()).when(cityService).findCitiesByName(anyInt(), anyInt(), anyString());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(SEARCH_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNumber", String.valueOf(1)).param("pageSize", String.valueOf(1)).param("parameter", "searchParam");
        MockMvcBuilders.standaloneSetup(cityController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private <T> String convertRequestDtoToJson(T requestDto) {
        Gson gson = new Gson();
        return gson.toJson(requestDto);
    }
}