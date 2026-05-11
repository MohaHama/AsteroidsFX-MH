package dk.sdu.se4.common.util;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

public enum ServiceLocator {

    INSTANCE;

    public <T> List<T> locateAll(Class<T> service) {
        List<T> result = new ArrayList<>();
        Path pluginsPath = Paths.get("plugins");

        if (!Files.exists(pluginsPath)) {
            return result;
        }

        try {
            ModuleFinder finder = ModuleFinder.of(pluginsPath);

            Set<String> modules = finder.findAll()
                    .stream()
                    .map(ModuleReference::descriptor)
                    .map(moduleDescriptor -> moduleDescriptor.name())
                    .filter(name -> !name.equals("Core"))
                    .filter(name -> !name.equals("Common"))
                    .filter(name -> !name.equals("CommonBullet"))
                    .filter(name -> !name.equals("CommonAsteroids"))
                    .filter(name -> !name.equals("Scoring"))
                    .collect(Collectors.toSet());

            if (modules.isEmpty()) {
                return result;
            }

            Configuration config = ModuleLayer.boot()
                    .configuration()
                    .resolve(finder, ModuleFinder.of(), modules);

            ModuleLayer layer = ModuleLayer.boot()
                    .defineModulesWithOneLoader(config, ClassLoader.getSystemClassLoader());

            ServiceLoader<T> loader = ServiceLoader.load(layer, service);

            for (T item : loader) {
                if (!result.contains(item)) {
                    result.add(item);
                }
            }
        } catch (ServiceConfigurationError ignored) {
        } catch (Exception ignored) {
        }

        return result;
    }
}