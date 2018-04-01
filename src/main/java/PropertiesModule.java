import com.google.inject.AbstractModule;
import com.google.inject.ProvisionException;
import com.google.inject.name.Names;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class PropertiesModule extends AbstractModule {

    final Logger logger = LoggerFactory.getLogger(PropertiesModule.class);

    @Override
    protected void configure() {
        loadEnvironmentalProperties();
    }

    private void loadEnvironmentalProperties() {
        try {
            logger.info("Loading default properties");
            Properties properties = new Properties();
            properties.load(Main.class.getResourceAsStream("/application.properties"));
            Names.bindProperties(binder(), properties);
        } catch (Exception e) {
            throw new ProvisionException("Can not read property file(s)", e);
        }
    }
}
