package be.albatroz.javacase.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by stefaanmoreels on 06/02/17.
 */
@Slf4j
@UtilityClass
public class Json {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false)
            .configure(MapperFeature.USE_ANNOTATIONS, true);
//            .setDateFormat(new ISO8601DateFormat());

    public String objectToJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Could not convert '{}'", object);
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
    }

    public <T> T jsonStringToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("Could not convert '{}' to Object of class {}", json, clazz);
            log.debug(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
    }


    public <T> T jsonBytesToObject(byte[] json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("Could not convert '{}' to Object of class {}", json, clazz);
            log.debug(e.getMessage(), e);
            throw new IllegalArgumentException(e);
        }
    }
}
