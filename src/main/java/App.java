
import com.google.inject.Guice;
import com.google.inject.Injector;

import infraestructure.conf.ParametersModuleProvider;
import infraestructure.security.SecurityModule;
import lombok.extern.slf4j.Slf4j;
import infraestructure.GsonModule;
import infraestructure.persistence.*;
import infraestructure.ui.*;
import routers.RouterModule;
import service.ServiceModule;


@Slf4j
public final class App {

    public static void main(String[] args) {

        log.info("Initializing Modules...");

        Injector injector = Guice.createInjector(
                ParametersModuleProvider.get(),
                new GsonModule(),
                new SecurityModule(),
                new PersistenceModule(),
                new StaticFilesModule(),
                new RouterModule(),
                new ServiceModule()
        );

        ApiContext context = injector.getBinding(ApiContext.class).getProvider().get();
        log.info("Defining API context...");

        context.init();

    }

}
