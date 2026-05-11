module Asteroids {
    requires Common;
    requires CommonAsteroids;

    provides dk.sdu.se4.common.services.IGamePluginService
            with dk.sdu.se4.asteroids.AsteroidPlugin;

    provides dk.sdu.se4.common.services.IEntityProcessingService
            with dk.sdu.se4.asteroids.AsteroidProcessor;

    provides dk.sdu.se4.common.asteroids.IAsteroidSplitter
            with dk.sdu.se4.asteroids.AsteroidSplitterImplementation;
}