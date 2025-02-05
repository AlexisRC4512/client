package com.nttdata.client.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nttdata.client.model.enums.TypeClient;

import java.io.IOException;

public class ClientTypeDeserializer extends JsonDeserializer<TypeClient> {
    @Override
    public TypeClient deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText().toUpperCase();
        return TypeClient.valueOf(value);
    }
}
