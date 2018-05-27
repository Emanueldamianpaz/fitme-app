package ar.edu.davinci.infraestructure.conf;

import com.github.racc.tscg.TypesafeConfigModule;
import com.google.inject.AbstractModule;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class ParametersModuleProvider {

    public static AbstractModule get() {

        Config config = ConfigFactory.load();

        return TypesafeConfigModule.fromConfigWithPackage(config, "ar.edu.davinci");
    }
}
