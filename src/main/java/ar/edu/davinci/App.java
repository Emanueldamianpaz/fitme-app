package ar.edu.davinci;

import ar.edu.davinci.infraestructure.GsonModule;
import ar.edu.davinci.infraestructure.conf.ParametersModuleProvider;
import ar.edu.davinci.infraestructure.persistence.PersistenceModule;
import ar.edu.davinci.infraestructure.security.SecurityModule;
import ar.edu.davinci.infraestructure.ui.StaticFilesModule;
import ar.edu.davinci.routers.RouterModule;
import ar.edu.davinci.service.ServiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.extern.slf4j.Slf4j;


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
