package com.nttdata.client.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.nttdata.client.model.enums.SubTypeClient;
import com.nttdata.client.model.enums.TypeClient;

import java.io.IOException;

public class ClientSubTypeDeserializer extends JsonDeserializer<SubTypeClient> {
    @Override
    public SubTypeClient deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText().toUpperCase();
        return SubTypeClient.valueOf(value);
    }
}
