package dk.sdu.se4.common.bullet;

import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;

public interface IBullet {
    Entity createBullet(Entity shooter, GameData gameData);
}