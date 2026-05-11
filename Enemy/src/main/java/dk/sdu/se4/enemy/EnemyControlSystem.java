package dk.sdu.se4.enemy;

import dk.sdu.se4.common.bullet.Bullet;
import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IEntityProcessingService;

import java.util.List;
import java.util.Random;

public class EnemyControlSystem implements IEntityProcessingService {

    private final Random random = new Random();
    private long lastDirectionChange = 0;
    private long lastShot = 0;

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> enemies = world.getEntities(Enemy.class);

        if (enemies.isEmpty()) {
            world.addEntity(EnemyPlugin.createEnemy(gameData));
            return;
        }

        Entity enemy = enemies.get(0);
        Entity player = getPlayer(world);

        long now = System.currentTimeMillis();

        if (player != null) {
            double dx = player.getX() - enemy.getX();
            double dy = player.getY() - enemy.getY();
            enemy.setRotation(Math.toDegrees(Math.atan2(dy, dx)));
        } else if (now - lastDirectionChange > 1200) {
            enemy.setRotation(random.nextInt(360));
            lastDirectionChange = now;
        }

        double x = Math.cos(Math.toRadians(enemy.getRotation()));
        double y = Math.sin(Math.toRadians(enemy.getRotation()));

        enemy.setX(enemy.getX() + x * 0.35);
        enemy.setY(enemy.getY() + y * 0.35);

        if (now - lastShot > 4000) {
            world.addEntity(createBullet(enemy));
            lastShot = now;
        }

        if (enemy.getX() < 0) {
            enemy.setX(gameData.getDisplayWidth());
        }

        if (enemy.getX() > gameData.getDisplayWidth()) {
            enemy.setX(0);
        }

        if (enemy.getY() < 0) {
            enemy.setY(gameData.getDisplayHeight());
        }

        if (enemy.getY() > gameData.getDisplayHeight()) {
            enemy.setY(0);
        }
    }

    private Entity getPlayer(World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getClass().getSimpleName().equals("Player")) {
                return entity;
            }
        }

        return null;
    }

    private Entity createBullet(Entity enemy) {
        Bullet bullet = new Bullet();

        bullet.setPolygonCoordinates(
                2, -2,
                2, 2,
                -2, 2,
                -2, -2
        );

        double x = Math.cos(Math.toRadians(enemy.getRotation()));
        double y = Math.sin(Math.toRadians(enemy.getRotation()));

        bullet.setX(enemy.getX() + x * 18);
        bullet.setY(enemy.getY() + y * 18);
        bullet.setRotation(enemy.getRotation());
        bullet.setRadius(2);
        bullet.setOwnerID(enemy.getID());

        return bullet;
    }
}