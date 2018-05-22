package infraestructure;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import utils.ObjectMapperProvider;

public class GsonModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Gson.class).toProvider(ObjectMapperProvider.class);
    }
}
