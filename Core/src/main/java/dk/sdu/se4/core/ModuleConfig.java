package dk.sdu.se4.core;

import dk.sdu.se4.common.services.IEntityProcessingService;
import dk.sdu.se4.common.services.IGamePluginService;
import dk.sdu.se4.common.services.IPostEntityProcessingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

@Configuration
class ModuleConfig {

    @Bean
    Game game() {
        return new Game(gamePluginServices(), entityProcessingServiceList(), postEntityProcessingServices());
    }

    @Bean
    List<IEntityProcessingService> entityProcessingServiceList() {
        return ServiceLoader.load(ModuleLayer.boot(), IEntityProcessingService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }

    @Bean
    List<IGamePluginService> gamePluginServices() {
        return ServiceLoader.load(ModuleLayer.boot(), IGamePluginService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }

    @Bean
    List<IPostEntityProcessingService> postEntityProcessingServices() {
        return ServiceLoader.load(ModuleLayer.boot(), IPostEntityProcessingService.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }
}