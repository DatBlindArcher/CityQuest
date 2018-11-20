package be.ucll.robbes.cityquest.infrastructure.repository;

import be.ucll.robbes.cityquest.model.Coordinates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

@Converter
public class CoordinatesConverter implements AttributeConverter<Coordinates, String>
{
    @Override
    public String convertToDatabaseColumn(Coordinates attribute) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error during JSON Serialization", e);
        }
    }

    @Override
    public Coordinates convertToEntityAttribute(String dbData) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(dbData, Coordinates.class);
        } catch (IOException e) {
            throw new RuntimeException("Error during JSON Deserialization", e);
        }
    }
}