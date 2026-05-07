package dk.sdu.se4.collisionsystem;

import dk.sdu.se4.common.asteroids.Asteroid;
import dk.sdu.se4.common.asteroids.IAsteroidSplitter;
import dk.sdu.se4.common.bullet.Bullet;
import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IPostEntityProcessingService;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    private final RestTemplate restTemplate = new RestTemplate();

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
        if (isShip(first) && second instanceof Asteroid) {
            hitShip(first, world);
            destroy(second, world);
            return;
        }

        if (isShip(second) && first instanceof Asteroid) {
            hitShip(second, world);
            destroy(first, world);
            return;
        }

        if (first instanceof Bullet && second instanceof Asteroid) {
            destroy(first, world);
            hitAsteroid(second, world, gameData);
            return;
        }

        if (second instanceof Bullet && first instanceof Asteroid) {
            destroy(second, world);
            hitAsteroid(first, world, gameData);
            return;
        }

        if (first instanceof Bullet && isShip(second)) {
            hitShipWithBullet((Bullet) first, second, world);
            return;
        }

        if (second instanceof Bullet && isShip(first)) {
            hitShipWithBullet((Bullet) second, first, world);
        }
    }

    private void hitShipWithBullet(Bullet bullet, Entity ship, World world) {
        if (ship.getID().equals(bullet.getOwnerID())) {
            return;
        }

        destroy(bullet, world);
        hitShip(ship, world);
    }

    private void hitAsteroid(Entity asteroid, World world, GameData gameData) {
        asteroid.setHealth(asteroid.getHealth() - 1);

        if (asteroid.getHealth() <= 0) {
            splitAsteroid(asteroid, world);
            destroy(asteroid, world);
            addScore(gameData, 1);
        }
    }

    private void hitShip(Entity ship, World world) {
        ship.setHealth(ship.getHealth() - 1);

        if (ship.getHealth() <= 0) {
            destroy(ship, world);
        }
    }

    private void destroy(Entity entity, World world) {
        world.removeEntity(entity);
    }

    private boolean isShip(Entity entity) {
        return isPlayer(entity) || isEnemy(entity);
    }

    private boolean isPlayer(Entity entity) {
        return entity.getClass().getSimpleName().equals("Player");
    }

    private boolean isEnemy(Entity entity) {
        return entity.getClass().getSimpleName().equals("Enemy");
    }

    private void addScore(GameData gameData, int points) {
        try {
            Integer score = restTemplate.postForObject(
                    "http://localhost:8080/score/add/" + points,
                    null,
                    Integer.class
            );

            if (score != null) {
                gameData.setScore(score);
            }
        } catch (Exception e) {
            gameData.setScore(gameData.getScore() + points);
        }
    }

    private void splitAsteroid(Entity asteroid, World world) {
        ServiceLoader<IAsteroidSplitter> loader = ServiceLoader.load(IAsteroidSplitter.class);

        for (IAsteroidSplitter splitter : loader) {
            splitter.createSplitAsteroid(asteroid, world);
            return;
        }
    }

    public boolean collides(Entity first, Entity second) {
        float dx = (float) first.getX() - (float) second.getX();
        float dy = (float) first.getY() - (float) second.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance < first.getRadius() + second.getRadius();
    }
}
