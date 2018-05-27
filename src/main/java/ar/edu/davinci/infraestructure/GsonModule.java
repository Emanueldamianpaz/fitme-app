package ar.edu.davinci.infraestructure;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import ar.edu.davinci.utils.ObjectMapperProvider;

public class GsonModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Gson.class).toProvider(ObjectMapperProvider.class);
    }
}
