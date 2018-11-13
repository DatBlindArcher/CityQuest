package be.ucll.robbes.cityquest.infrastructure.repository;

import be.ucll.robbes.cityquest.model.City;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter
public class CityConverter implements AttributeConverter<City, String>
{
    @Override
    public String convertToDatabaseColumn(City attribute) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error during JSON Serialization", e);
        }
    }

    @Override
    public City convertToEntityAttribute(String dbData) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(dbData, City.class);
        } catch (IOException e) {
            throw new RuntimeException("Error during JSON Deserialization", e);
        }
    }
}