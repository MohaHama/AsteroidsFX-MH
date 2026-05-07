package dk.sdu.se4.common.util;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
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

    private static final Map<Class, List> services = new HashMap<>();
    private final ModuleLayer pluginLayer;

    ServiceLocator() {
        ModuleLayer layer = null;

        try {
            Path pluginsPath = Paths.get("plugins");

            if (Files.exists(pluginsPath)) {
                ModuleFinder pluginFinder = ModuleFinder.of(pluginsPath);

                List<String> pluginNames = pluginFinder
                        .findAll()
                        .stream()
                        .map(ModuleReference::descriptor)
                        .map(ModuleDescriptor::name)
                        .collect(Collectors.toList());

                if (!pluginNames.isEmpty()) {
                    Configuration pluginConfiguration = ModuleLayer
                            .boot()
                            .configuration()
                            .resolve(pluginFinder, ModuleFinder.of(), pluginNames);

                    layer = ModuleLayer
                            .boot()
                            .defineModulesWithOneLoader(pluginConfiguration, ClassLoader.getSystemClassLoader());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pluginLayer = layer;
    }

    public <T> List<T> locateAll(Class<T> service) {
        List<T> located = services.get(service);

        if (located != null) {
            return located;
        }

        located = new ArrayList<>();
        addServices(located, ServiceLoader.load(service));

        if (pluginLayer != null) {
            addServices(located, ServiceLoader.load(pluginLayer, service));
        }

        services.put(service, located);
        return located;
    }

    private <T> void addServices(List<T> result, ServiceLoader<T> loader) {
        try {
            for (T service : loader) {
                if (!result.contains(service)) {
                    result.add(service);
                }
            }
        } catch (ServiceConfigurationError error) {
            error.printStackTrace();
        }
    }
}
