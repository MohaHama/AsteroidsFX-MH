package dk.sdu.se4.collisionsystem;

import dk.sdu.se4.common.asteroids.Asteroid;
import dk.sdu.se4.common.bullet.Bullet;
import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollisionDetector implements IPostEntityProcessingService {

    private final Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = new ArrayList<>(world.getEntities());

        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                Entity first = entities.get(i);
                Entity second = entities.get(j);

                if (world.getEntity(first.getID()) == null || world.getEntity(second.getID()) == null) {
                    continue;
                }

                if (collides(first, second)) {
                    handleCollision(first, second, world, gameData);
                }
            }
        }
    }

    private void handleCollision(Entity first, Entity second, World world, GameData gameData) {
        if (first instanceof Bullet && second instanceof Asteroid) {
            hitAsteroid(second, world, gameData);
            world.removeEntity(first);
            return;
        }

        if (second instanceof Bullet && first instanceof Asteroid) {
            hitAsteroid(first, world, gameData);
            world.removeEntity(second);
            return;
        }

        if (first instanceof Bullet && isShip(second)) {
            if (!sameOwner((Bullet) first, second)) {
                world.removeEntity(first);
                damage(second, world);
            }
            return;
        }

        if (second instanceof Bullet && isShip(first)) {
            if (!sameOwner((Bullet) second, first)) {
                world.removeEntity(second);
                damage(first, world);
            }
            return;
        }

        if (first instanceof Asteroid && isShip(second)) {
            world.removeEntity(second);
            return;
        }

        if (second instanceof Asteroid && isShip(first)) {
            world.removeEntity(first);
        }
    }

    private void hitAsteroid(Entity asteroid, World world, GameData gameData) {
        splitAsteroid(asteroid, world);
        world.removeEntity(asteroid);
        gameData.setScore(gameData.getScore() + 1);
    }

    private void splitAsteroid(Entity asteroid, World world) {
        if (asteroid.getRadius() <= 8) {
            return;
        }

        float size = asteroid.getRadius() / 2;

        world.addEntity(createAsteroid(asteroid, size, -15));
        world.addEntity(createAsteroid(asteroid, size, 15));
    }

    private Entity createAsteroid(Entity oldAsteroid, float size, int move) {
        Entity asteroid = new Asteroid();

        asteroid.setHealth(1);
        asteroid.setRadius(size);
        asteroid.setX(oldAsteroid.getX() + move);
        asteroid.setY(oldAsteroid.getY() - move);
        asteroid.setRotation(random.nextInt(360));

        asteroid.setPolygonCoordinates(
                size, -size,
                -size, -size,
                -size, size,
                size, size
        );

        return asteroid;
    }

    private boolean sameOwner(Bullet bullet, Entity entity) {
        return bullet.getOwnerID() != null && bullet.getOwnerID().equals(entity.getID());
    }

    private void damage(Entity entity, World world) {
        long now = System.currentTimeMillis();

        if (entity.getImmuneUntil() > now) {
            return;
        }

        entity.setHealth(entity.getHealth() - 1);
        entity.setImmuneUntil(now + 1200);
        entity.setFlashUntil(now + 1200);

        if (entity.getHealth() <= 0) {
            world.removeEntity(entity);
        }
    }

    private boolean isShip(Entity entity) {
        String name = entity.getClass().getSimpleName();
        return name.equals("Player") || name.equals("Enemy");
    }

    public boolean collides(Entity first, Entity second) {
        float dx = (float) first.getX() - (float) second.getX();
        float dy = (float) first.getY() - (float) second.getY();

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance < first.getRadius() + second.getRadius();
    }
}