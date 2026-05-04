package dk.sdu.se4.common.asteroids;

import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.World;

public interface IAsteroidSplitter {
    void createSplitAsteroid(Entity asteroidEntity, World world);
}