import dk.sdu.se4.common.services.IEntityProcessingService;
import dk.sdu.se4.common.services.IGamePluginService;
import dk.sdu.se4.common.asteroids.IAsteroidSplitter;

module Asteroids {
    requires Common;
    requires CommonAsteroids;

    provides IGamePluginService with dk.sdu.se4.asteroids.AsteroidPlugin;
    provides IEntityProcessingService with dk.sdu.se4.asteroids.AsteroidProcessor;
    provides IAsteroidSplitter with dk.sdu.se4.asteroids.AsteroidSplitterImplementation;
}