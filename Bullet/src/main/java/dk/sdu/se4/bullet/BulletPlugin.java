package dk.sdu.se4.bullet;

import dk.sdu.se4.common.bullet.Bullet;
import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity bulletEntity : world.getEntities(Bullet.class)) {
            world.removeEntity(bulletEntity);
        }
    }
}