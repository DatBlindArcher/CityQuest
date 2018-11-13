package be.ucll.robbes.cityquest.infrastructure.repository;

import be.ucll.robbes.cityquest.model.City;
import be.ucll.robbes.cityquest.model.Question;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@Converter
public class JpaJsonConverter<T> implements AttributeConverter<T, String>  {
    private JavaType type;

    protected void SetClass(Class classType)
    {
        ObjectMapper mapper = new ObjectMapper();
        type = mapper.getTypeFactory().constructType(classType);
    }

    protected void SetListClass(Class classType)
    {
        ObjectMapper mapper = new ObjectMapper();
        type = mapper.getTypeFactory().constructCollectionType(List.class, classType);
    }

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
    @SuppressWarnings("unchecked")
    public T convertToEntityAttribute(String data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (T)mapper.readValue(data, type);
        } catch (IOException e) {
            throw new RuntimeException("Error during JSON Deserialization", e);
        }
    }

    public class CityConverter extends JpaJsonConverter<City> implements AttributeConverter<City, String>
    {
        public CityConverter()
        {
            SetClass(City.class);
        }
    }

    public class QuestionConverter extends JpaJsonConverter<List<Question>> implements AttributeConverter<List<Question>, String>
    {
        public QuestionConverter()
        {
            SetListClass(Question.class);
        }
    }
}