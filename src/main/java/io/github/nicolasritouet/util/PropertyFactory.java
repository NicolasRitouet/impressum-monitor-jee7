package io.github.nicolasritouet.util;

import io.github.nicolasritouet.util.annotations.Property;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Factory to inject the properties from the property files.
 */
public class PropertyFactory {
    private static final String ENVIRONMENT_NAME_KEY = "environment.name";
    private static final String DEFAULT_PROPS_FILENAME = "environment.properties";
    private static final String PROPS_FILENAME_FORMAT = "environment_%s.properties";
    private Properties environmentProps;

    /**
     * Initialize: load the property file in memory
     * @throws IOException
     */
    @PostConstruct
    public void initEnvironmentProps() throws IOException {
        environmentProps = new Properties();
        String propsFilename = DEFAULT_PROPS_FILENAME;
        String environmentName = System.getProperty(ENVIRONMENT_NAME_KEY);
        if (environmentName != null) {
            propsFilename = String.format(PROPS_FILENAME_FORMAT, environmentName);
        }
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propsFilename);
        System.out.println("Loading property file: " + propsFilename);
        if (inputStream == null) {
            throw new FileNotFoundException("Properties file for environment " + environmentName + " not found in the classpath.");
        }
        environmentProps.load(inputStream);
    }

    /**
     *
     * Get the value requested from the property file.
     * @param ip The Injection Point (The variable where we want to inject the property
     * @return The property value
     */
    @Produces
    @Property
    public String getConfigValue(InjectionPoint ip) {
        Property config = ip.getAnnotated().getAnnotation(Property.class);
        String configKey = config.key();
        if (configKey.isEmpty()) {
            // For larger applications, also use the classname to avoid duplicates
            configKey = ip.getMember().getName();
        }
        return environmentProps.getProperty(configKey);
    }
}
