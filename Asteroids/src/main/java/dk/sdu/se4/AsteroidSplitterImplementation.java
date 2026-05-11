package dk.sdu.se4.asteroids;

import dk.sdu.se4.common.asteroids.Asteroid;
import dk.sdu.se4.common.asteroids.IAsteroidSplitter;
import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.World;

import java.util.Random;

public class AsteroidSplitterImplementation implements IAsteroidSplitter {

    private final Random random = new Random();

    @Override
    public void createSplitAsteroid(Entity oldAsteroid, World world) {
        if (oldAsteroid.getRadius() <= 8) {
            return;
        }

        float size = oldAsteroid.getRadius() / 2;

        Entity first = makeAsteroid(oldAsteroid, size, 12);
        Entity second = makeAsteroid(oldAsteroid, size, -12);

        world.addEntity(first);
        world.addEntity(second);
    }

    private Entity makeAsteroid(Entity oldAsteroid, float size, int move) {
        Entity asteroid = new Asteroid();

        asteroid.setHealth(1);
        asteroid.setRadius(size);
        asteroid.setX(oldAsteroid.getX() + move);
        asteroid.setY(oldAsteroid.getY() + move);
        asteroid.setRotation(random.nextInt(360));

        asteroid.setPolygonCoordinates(
                size, -size,
                -size, -size,
                -size, size,
                size, size
        );

        return asteroid;
    }
}