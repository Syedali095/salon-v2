package com.mysalon.constantconfig;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MapToJsonConverter implements AttributeConverter<Map<String, BigDecimal>, String>{
	private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, BigDecimal> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting Map to JSON String", e);
        }
    }

    @Override
    public Map<String, BigDecimal> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
        	return objectMapper.readValue(dbData, new TypeReference<Map<String, BigDecimal>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON String to Map", e);
        }
    }
}
