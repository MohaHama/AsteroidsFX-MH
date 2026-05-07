package dk.sdu.se4.bullet;

import dk.sdu.se4.common.bullet.Bullet;
import dk.sdu.se4.common.bullet.BulletSPI;
import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bulletEntity : world.getEntities(Bullet.class)) {
            double movementX = Math.cos(Math.toRadians(bulletEntity.getRotation()));
            double movementY = Math.sin(Math.toRadians(bulletEntity.getRotation()));

            bulletEntity.setX(bulletEntity.getX() + movementX * 3);
            bulletEntity.setY(bulletEntity.getY() + movementY * 3);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Bullet bulletEntity = new Bullet();

        bulletEntity.setPolygonCoordinates(
                1, -1,
                1, 1,
                -1, 1,
                -1, -1
        );

        double movementX = Math.cos(Math.toRadians(shooter.getRotation()));
        double movementY = Math.sin(Math.toRadians(shooter.getRotation()));

        bulletEntity.setX(shooter.getX() + movementX * 10);
        bulletEntity.setY(shooter.getY() + movementY * 10);
        bulletEntity.setRotation(shooter.getRotation());
        bulletEntity.setRadius(1);
        bulletEntity.setOwnerID(shooter.getID());

        return bulletEntity;
    }
}