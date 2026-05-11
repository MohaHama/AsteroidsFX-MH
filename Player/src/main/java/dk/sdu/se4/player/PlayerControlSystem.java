package dk.sdu.se4.player;

import dk.sdu.se4.common.bullet.BulletSPI;
import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.GameKeys;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity playerEntity : world.getEntities(Player.class)) {
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                playerEntity.setRotation(playerEntity.getRotation() - 5);
            }

            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                playerEntity.setRotation(playerEntity.getRotation() + 5);
            }

            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double movementX = Math.cos(Math.toRadians(playerEntity.getRotation()));
                double movementY = Math.sin(Math.toRadians(playerEntity.getRotation()));

                playerEntity.setX(playerEntity.getX() + movementX);
                playerEntity.setY(playerEntity.getY() + movementY);
            }

            if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        bulletSPI -> world.addEntity(bulletSPI.createBullet(playerEntity, gameData))
                );
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }
}