package dk.sdu.se4.enemy;

import dk.sdu.se4.common.bullet.BulletSPI;
import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {

    private final Random random = new Random();
    private long lastDirectionChange = 0;

    @Override
    public void process(GameData gameData, World world) {
        if (world.getEntities(Enemy.class).isEmpty()) {
            world.addEntity(EnemyPlugin.createEnemy(gameData));
        }

        for (Entity enemy : world.getEntities(Enemy.class)) {
            long now = System.currentTimeMillis();

            if (now - lastDirectionChange > 1000) {
                enemy.setRotation(random.nextInt(360));
                lastDirectionChange = now;
            }

            double movementX = Math.cos(Math.toRadians(enemy.getRotation()));
            double movementY = Math.sin(Math.toRadians(enemy.getRotation()));

            enemy.setX(enemy.getX() + movementX);
            enemy.setY(enemy.getY() + movementY);

            if (random.nextInt(250) == 0) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        bulletSPI -> world.addEntity(bulletSPI.createBullet(enemy, gameData))
                );
            }

            if (enemy.getX() < 0) enemy.setX(gameData.getDisplayWidth());
            if (enemy.getX() > gameData.getDisplayWidth()) enemy.setX(0);
            if (enemy.getY() < 0) enemy.setY(gameData.getDisplayHeight());
            if (enemy.getY() > gameData.getDisplayHeight()) enemy.setY(0);
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }
}