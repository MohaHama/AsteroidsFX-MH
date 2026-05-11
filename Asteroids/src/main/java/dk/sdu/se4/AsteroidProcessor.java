package dk.sdu.se4.asteroids;

import dk.sdu.se4.common.asteroids.Asteroid;
import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IEntityProcessingService;

import java.util.Random;

public class AsteroidProcessor implements IEntityProcessingService {

    private final Random random = new Random();
    private long lastSpawnTime = System.currentTimeMillis();
    private static final long SPAWN_TIME = 2000;
    private static final int MAX_ASTEROIDS = 12;

    @Override
    public void process(GameData gameData, World world) {
        long now = System.currentTimeMillis();

        if (now - lastSpawnTime >= SPAWN_TIME && world.getEntities(Asteroid.class).size() < MAX_ASTEROIDS) {
            world.addEntity(createAsteroid(gameData));
            lastSpawnTime = now;
        }

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            double x = Math.cos(Math.toRadians(asteroid.getRotation()));
            double y = Math.sin(Math.toRadians(asteroid.getRotation()));

            asteroid.setX(asteroid.getX() + x * 0.45);
            asteroid.setY(asteroid.getY() + y * 0.45);

            if (asteroid.getX() < 0) {
                asteroid.setX(gameData.getDisplayWidth());
            }

            if (asteroid.getX() > gameData.getDisplayWidth()) {
                asteroid.setX(0);
            }

            if (asteroid.getY() < 0) {
                asteroid.setY(gameData.getDisplayHeight());
            }

            if (asteroid.getY() > gameData.getDisplayHeight()) {
                asteroid.setY(0);
            }
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();

        int size = random.nextInt(16) + 10;
        int side = random.nextInt(4);

        asteroid.setHealth(1);
        asteroid.setRadius(size);
        asteroid.setRotation(random.nextInt(360));

        asteroid.setPolygonCoordinates(
                size, -size,
                -size, -size,
                -size, size,
                size, size
        );

        if (side == 0) {
            asteroid.setX(random.nextInt(gameData.getDisplayWidth()));
            asteroid.setY(0);
        } else if (side == 1) {
            asteroid.setX(random.nextInt(gameData.getDisplayWidth()));
            asteroid.setY(gameData.getDisplayHeight());
        } else if (side == 2) {
            asteroid.setX(0);
            asteroid.setY(random.nextInt(gameData.getDisplayHeight()));
        } else {
            asteroid.setX(gameData.getDisplayWidth());
            asteroid.setY(random.nextInt(gameData.getDisplayHeight()));
        }

        return asteroid;
    }
}