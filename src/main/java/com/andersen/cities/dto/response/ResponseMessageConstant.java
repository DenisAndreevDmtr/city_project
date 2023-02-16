package com.andersen.cities.dto.response;

public class ResponseMessageConstant {
    public static final String INCORRECT_FILE_INFORMATION = "Incorrect file information";
    public static final String CITIES_UPLOADED = "Cities were successfully uploaded";
    public static final String CITY_EDITED = "City was successfully edited";
    public static final String TEMPLATE = """
            {
                "dateTime": "2023-01-20T13:37:19.408Z",
                "message":\040""";
    public static final String TEMPLATE_END = "\"\n}";

    public static final String INCORRECT_CREDENTIALS = TEMPLATE + "\"" + "Incorrect credentials." + TEMPLATE_END;
    public static final String FILE_WAS_NOT_FOUND = TEMPLATE + "\"" + "File was not found." + TEMPLATE_END;
    public static final String INFORMATION_IN_FILE_INCORRECT = TEMPLATE + "\"" + "Information in file is incorrect." + TEMPLATE_END;
    public static final String CITY_WAS_NOT_FOUND = TEMPLATE + "\"" + "City with id '111111' not found." + TEMPLATE_END;
}