package dk.sdu.se4.asteroids;

import dk.sdu.se4.common.asteroids.Asteroid;
import dk.sdu.se4.common.asteroids.IAsteroidSplitter;
import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IEntityProcessingService;

import java.util.Random;

public class AsteroidProcessor implements IEntityProcessingService {

    private IAsteroidSplitter splitter = new AsteroidSplitterImplementation();
    private final Random random = new Random();
    private long lastSpawnTime = System.currentTimeMillis();
    private static final long SPAWN_INTERVAL_MS = 3000;

    @Override
    public void process(GameData gameData, World world) {

        // Spawn a new asteroid every 3 seconds
        long now = System.currentTimeMillis();
        if (now - lastSpawnTime >= SPAWN_INTERVAL_MS) {
            world.addEntity(createAsteroid(gameData));
            lastSpawnTime = now;
        }

        for (Entity asteroidEntity : world.getEntities(Asteroid.class)) {
            double movementX = Math.cos(Math.toRadians(asteroidEntity.getRotation()));
            double movementY = Math.sin(Math.toRadians(asteroidEntity.getRotation()));

            asteroidEntity.setX(asteroidEntity.getX() + movementX * 0.5);
            asteroidEntity.setY(asteroidEntity.getY() + movementY * 0.5);

            if (asteroidEntity.getX() < 0) asteroidEntity.setX(gameData.getDisplayWidth());
            if (asteroidEntity.getX() > gameData.getDisplayWidth()) asteroidEntity.setX(0);
            if (asteroidEntity.getY() < 0) asteroidEntity.setY(gameData.getDisplayHeight());
            if (asteroidEntity.getY() > gameData.getDisplayHeight()) asteroidEntity.setY(0);
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        int size = random.nextInt(10) + 5;

        asteroid.setPolygonCoordinates(
                size, -size,
                -size, -size,
                -size, size,
                size, size
        );

        // Spawn on a random edge of the screen
        int edge = random.nextInt(4);
        switch (edge) {
            case 0 -> { asteroid.setX(random.nextInt(gameData.getDisplayWidth())); asteroid.setY(0); }
            case 1 -> { asteroid.setX(random.nextInt(gameData.getDisplayWidth())); asteroid.setY(gameData.getDisplayHeight()); }
            case 2 -> { asteroid.setX(0); asteroid.setY(random.nextInt(gameData.getDisplayHeight())); }
            default -> { asteroid.setX(gameData.getDisplayWidth()); asteroid.setY(random.nextInt(gameData.getDisplayHeight())); }
        }

        asteroid.setRadius(size);
        asteroid.setRotation(random.nextInt(360));
        return asteroid;
    }

    public void setAsteroidSplitter(IAsteroidSplitter splitter) { this.splitter = splitter; }
    public void removeAsteroidSplitter(IAsteroidSplitter splitter) { this.splitter = null; }
}