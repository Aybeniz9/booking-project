package az.edu.turing.controller;
import az.edu.turing.dao.entity.Cities;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

    public class CitiesDeserializer extends JsonDeserializer<Cities> {
        @Override
        public Cities deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String city = jsonParser.getText().toUpperCase();
            return Cities.valueOf(city);
        }
    }

