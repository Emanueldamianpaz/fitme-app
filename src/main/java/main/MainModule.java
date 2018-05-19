package main;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import utils.json.ObjectMapperProvider;

public class MainModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Gson.class).toProvider(ObjectMapperProvider.class);
    }
}
