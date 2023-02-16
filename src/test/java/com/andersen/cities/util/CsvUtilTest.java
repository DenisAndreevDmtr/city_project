package com.andersen.cities.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {CsvUtil.class})
@ExtendWith(MockitoExtension.class)
class CsvUtilTest {

    @InjectMocks
    private CsvUtil csvUtil;

    @Test
    void shouldReturnListCity_whenParseCsvCity_givenValidAndInvalidFile() throws IOException {
        assertTrue(csvUtil.parseCsvCity(new MockMultipartFile("file", "test".getBytes("UTF-8"))).isEmpty());
        assertTrue(csvUtil.parseCsvCity(null).isEmpty());
    }
}