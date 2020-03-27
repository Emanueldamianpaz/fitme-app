package ar.edu.davinci.infraestructure;

import ar.edu.davinci.infraestructure.utils.ObjectMapperProvider;
import com.google.gson.Gson;
import com.google.inject.AbstractModule;

public class GsonModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Gson.class).toProvider(ObjectMapperProvider.class);
    }
}
