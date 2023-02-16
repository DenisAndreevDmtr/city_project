package com.andersen.cities.controller;

import com.andersen.cities.dto.request.CityDto;
import com.andersen.cities.dto.response.GeneralErrorResponse;
import com.andersen.cities.dto.response.SuccessResponse;
import com.andersen.cities.entity.City;
import com.andersen.cities.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.andersen.cities.dto.response.ResponseMessageConstant.CITIES_UPLOADED;
import static com.andersen.cities.dto.response.ResponseMessageConstant.CITY_EDITED;
import static com.andersen.cities.dto.response.ResponseMessageConstant.CITY_WAS_NOT_FOUND;
import static com.andersen.cities.dto.response.ResponseMessageConstant.FILE_WAS_NOT_FOUND;
import static com.andersen.cities.dto.response.ResponseMessageConstant.INFORMATION_IN_FILE_INCORRECT;
import static com.andersen.cities.util.Constants.FILE;

@Tag(name = "city", description = "The city API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/city")
public class CityController {
    private final CityService cityService;

    @Operation(summary = "Upload cities from CSV file and save to database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cities were successfully uploaded",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - forbidden access"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = GeneralErrorResponse.class),
                            examples = {@ExampleObject(FILE_WAS_NOT_FOUND),
                                    @ExampleObject(INFORMATION_IN_FILE_INCORRECT)}))
    })
    @PostMapping(path = "/uploader", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse uploadCitiesFromCsvFile(@RequestParam(FILE) MultipartFile file) {
        cityService.uploadCityFromCsvFile(file);
        return new SuccessResponse(CITIES_UPLOADED);
    }

    @Operation(summary = "Edit city information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "City information was edited",
                    content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - forbidden access"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = GeneralErrorResponse.class),
                            examples = {@ExampleObject(CITY_WAS_NOT_FOUND)}))
    })
    @PatchMapping("/editor")
    public SuccessResponse editCity(@Valid @RequestBody CityDto cityDto) {
        cityService.editCity(cityDto);
        return new SuccessResponse(CITY_EDITED);
    }

    @Operation(summary = "Get all cities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get city page",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = City.class)))),
            @ApiResponse(responseCode = "204", description = "Couldn't find anything by your request")
    })
    @GetMapping("/view")
    public ResponseEntity<List<City>> getAllCities(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        List<City> cities = cityService.getAllCities(pageNumber, pageSize);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @Operation(summary = "Get cities by page and search parameter")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get page with cities by search parameter",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = City.class)))
            ),
            @ApiResponse(responseCode = "204", description = "Couldn't find anything by your request")
    })
    @GetMapping("/search")
    public ResponseEntity<List<City>> findCitiesByName(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam String parameter) {
        List<City> cities = cityService.findCitiesByName(pageNumber, pageSize, parameter);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }
}