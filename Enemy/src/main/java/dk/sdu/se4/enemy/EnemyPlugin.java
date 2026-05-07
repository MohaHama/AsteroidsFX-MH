package dk.sdu.se4.enemy;

import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }

    static Entity createEnemy(GameData gameData) {
        Entity enemy = new Enemy();

        enemy.setPolygonCoordinates(
                12, 0,
                6, 10,
                -6, 10,
                -12, 0,
                -6, -10,
                6, -10
        );

        enemy.setX(100);
        enemy.setY(100);
        enemy.setRadius(10);
        enemy.setRotation(45);
        enemy.setHealth(3);

        return enemy;
    }
}
