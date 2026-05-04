package dk.sdu.se4.common.util;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public enum ServiceLocator {

    INSTANCE;

    private static final Map<Class, ServiceLoader> loaders = new HashMap<>();
    private final ModuleLayer layer;

    ServiceLocator() {
        try {
            Path pluginsPath = Paths.get("plugins");

            ModuleFinder pluginFinder = ModuleFinder.of(pluginsPath);

            List<String> pluginNames = pluginFinder
                    .findAll()
                    .stream()
                    .map(ModuleReference::descriptor)
                    .map(ModuleDescriptor::name)
                    .collect(Collectors.toList());

            Configuration pluginConfiguration = ModuleLayer
                    .boot()
                    .configuration()
                    .resolve(pluginFinder, ModuleFinder.of(), pluginNames);

            layer = ModuleLayer
                    .boot()
                    .defineModulesWithOneLoader(pluginConfiguration, ClassLoader.getSystemClassLoader());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public <T> List<T> locateAll(Class<T> service) {
        ServiceLoader<T> loader = loaders.get(service);

        if (loader == null) {
            loader = ServiceLoader.load(layer, service);
            loaders.put(service, loader);
        }

        List<T> services = new ArrayList<>();

        try {
            for (T instance : loader) {
                services.add(instance);
            }
        } catch (ServiceConfigurationError error) {
            error.printStackTrace();
        }

        return services;
    }
}