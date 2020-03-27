package ar.edu.davinci.infraestructure.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ResponseTransformer;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JsonTransformer<T> implements ResponseTransformer {

    private Gson objectMapper;

    @Inject
    public JsonTransformer() {
        this.objectMapper = new GsonBuilder()
                .serializeNulls()
                .create();
        ;
    }

    @Override
    public String render(Object model) {
        return objectMapper.toJson(model);
    }

    public T asJson(String json, Class<T> clazz) {
        return objectMapper.fromJson(json, clazz);
    }

}
