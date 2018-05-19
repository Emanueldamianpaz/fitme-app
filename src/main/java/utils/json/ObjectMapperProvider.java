package utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vividsolutions.jts.geom.*;

import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class ObjectMapperProvider implements Provider<Gson> {
    @Override
    public Gson get() {
        return new GsonBuilder().create();
    }
}
