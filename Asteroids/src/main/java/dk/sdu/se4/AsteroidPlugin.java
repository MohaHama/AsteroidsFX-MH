package dk.sdu.se4.asteroids;

import dk.sdu.se4.common.asteroids.Asteroid;
import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private final Random random = new Random();

    @Override
    public void start(GameData gameData, World world) {
        Entity newAsteroid = createAsteroid(gameData);
        world.addEntity(newAsteroid);
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroidEntity : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroidEntity);
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Entity asteroidEntity = new Asteroid();

        int asteroidSize = random.nextInt(10) + 5;

        asteroidEntity.setPolygonCoordinates(
                asteroidSize, -asteroidSize,
                -asteroidSize, -asteroidSize,
                -asteroidSize, asteroidSize,
                asteroidSize, asteroidSize
        );

        // Spawn in top-left corner, far from player at center
        asteroidEntity.setX(50);
        asteroidEntity.setY(50);
        asteroidEntity.setRadius(asteroidSize);
        asteroidEntity.setRotation(random.nextInt(360));

        return asteroidEntity;
    }
}