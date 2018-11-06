package be.ucll.robbes.cityquest.infrastructure.repository;

import be.ucll.robbes.cityquest.model.City;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter
public class JpaJsonConverter<T> implements AttributeConverter<T, String> {
    private Class classType;

    @Override
    public String convertToDatabaseColumn(T obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error during JSON Serialization", e);
        }
    }

    @Override
    public T convertToEntityAttribute(String data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (T)mapper.readValue(data, classType);
        } catch (IOException e) {
            throw new RuntimeException("Error during JSON Deserialization", e);
        }
    }

    public class CityConverter extends JpaJsonConverter<City> implements AttributeConverter<City, String>
    {
        public CityConverter()
        {
            classType = City.class;
        }
    }
}