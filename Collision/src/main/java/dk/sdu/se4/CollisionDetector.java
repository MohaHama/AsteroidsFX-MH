package dk.sdu.se4.collisionsystem;

import dk.sdu.se4.common.asteroids.Asteroid;
import dk.sdu.se4.common.asteroids.IAsteroidSplitter;
import dk.sdu.se4.common.bullet.Bullet;
import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IPostEntityProcessingService;
import dk.sdu.se4.common.util.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetector implements IPostEntityProcessingService {

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
            splitAsteroid(second, world);
            world.removeEntity(first);
            world.removeEntity(second);
            gameData.setScore(gameData.getScore() + 1);
            return;
        }

        if (second instanceof Bullet && first instanceof Asteroid) {
            splitAsteroid(first, world);
            world.removeEntity(second);
            world.removeEntity(first);
            gameData.setScore(gameData.getScore() + 1);
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
            damage(second, world);
            return;
        }

        if (second instanceof Asteroid && isShip(first)) {
            damage(first, world);
        }
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

    private void splitAsteroid(Entity asteroid, World world) {
        List<IAsteroidSplitter> splitters = ServiceLocator.INSTANCE.locateAll(IAsteroidSplitter.class);

        if (!splitters.isEmpty()) {
            splitters.get(0).createSplitAsteroid(asteroid, world);
        }
    }

    public boolean collides(Entity first, Entity second) {
        float dx = (float) first.getX() - (float) second.getX();
        float dy = (float) first.getY() - (float) second.getY();

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance < first.getRadius() + second.getRadius();
    }
}