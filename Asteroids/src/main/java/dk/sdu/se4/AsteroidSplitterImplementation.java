package dk.sdu.se4.asteroids;

import dk.sdu.se4.common.asteroids.IAsteroidSplitter;
import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.World;

import java.util.Random;

public class AsteroidSplitterImplementation implements IAsteroidSplitter {

    Random random = new Random();

    @Override
    public void createSplitAsteroid(Entity asteroidEntity, World world) {
        float oldRadius = asteroidEntity.getRadius();

        if (oldRadius < 5) {
            return;
        }

        float newSize = oldRadius / 2;

        Entity split1 = new dk.sdu.se4.common.asteroids.Asteroid();
        split1.setPolygonCoordinates(newSize, -newSize, -newSize, -newSize, -newSize, newSize, newSize, newSize);
        split1.setX(asteroidEntity.getX() + 10);
        split1.setY(asteroidEntity.getY() + 10);
        split1.setRadius(newSize);
        split1.setRotation(random.nextInt(360));

        Entity split2 = new dk.sdu.se4.common.asteroids.Asteroid();
        split2.setPolygonCoordinates(newSize, -newSize, -newSize, -newSize, -newSize, newSize, newSize, newSize);
        split2.setX(asteroidEntity.getX() - 10);
        split2.setY(asteroidEntity.getY() - 10);
        split2.setRadius(newSize);
        split2.setRotation(random.nextInt(360));

        world.addEntity(split1);
        world.addEntity(split2);
    }
}