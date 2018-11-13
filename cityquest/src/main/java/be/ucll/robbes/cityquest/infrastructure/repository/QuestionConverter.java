package be.ucll.robbes.cityquest.infrastructure.repository;

import be.ucll.robbes.cityquest.model.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.List;

@Converter
public class QuestionConverter implements AttributeConverter<List<Question>, String>
{
    @Override
    public String convertToDatabaseColumn(List<Question> attribute) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error during JSON Serialization", e);
        }
    }

    @Override
    public List<Question> convertToEntityAttribute(String dbData) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Question.class);
            return mapper.readValue(dbData, type);
        } catch (IOException e) {
            throw new RuntimeException("Error during JSON Deserialization", e);
        }
    }
}
