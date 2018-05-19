package infraestructure.conf;

import com.github.racc.tscg.TypesafeConfigModule;
import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ParametersModuleProvider {

    public static AbstractModule get() {

        Config config = ConfigFactory.load("application.conf");

        return TypesafeConfigModule.fromConfigWithPackage(config, "");
    }
}
