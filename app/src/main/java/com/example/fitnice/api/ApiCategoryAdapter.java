package com.example.fitnice.api;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ApiCategoryAdapter extends TypeAdapter<String> {
    @Override
    public void write(JsonWriter out, String value) throws IOException {
        if (value == null)
            out.nullValue();
        else
            out.value(1);
    }

    @Override
    public String read(JsonReader in) throws IOException {
        if (in!=null) {
            switch (in.nextInt()) {
                case 0:
                    return "Piernas";
                default:
                    return "Brazos";
            }
        }

        return null;
    }
}
