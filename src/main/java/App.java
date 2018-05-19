
import com.google.inject.Guice;
import com.google.inject.Injector;

import infraestructure.conf.ParametersModuleProvider;
import infraestructure.security.SecurityModule;
import lombok.extern.slf4j.Slf4j;
import main.MainModule;
import persistence.PersistenceModule;
import ui.UiModule;

@Slf4j
public final class App {

    public static void main(String[] args) {

        log.info("Initializing Modules...");

        Injector injector = Guice.createInjector(
                ParametersModuleProvider.get(),
                new MainModule(),
                new SecurityModule(),
                new PersistenceModule(),
                new UiModule()
        );

        ApiContext context = injector.getBinding(ApiContext.class).getProvider().get();
        log.info("Defining API context...");

        context.init();

    }

}
