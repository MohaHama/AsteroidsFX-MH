package dk.sdu.se4.player;

import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

    private Entity playerEntity;

    @Override
    public void start(GameData gameData, World world) {
        playerEntity = createPlayerShip(gameData);
        world.addEntity(playerEntity);
    }

    private Entity createPlayerShip(GameData gameData) {
        Entity playerShip = new Player();

        playerShip.setPolygonCoordinates(
                -5, -5,
                10, 0,
                -5, 5
        );

        playerShip.setX(gameData.getDisplayWidth() / 2);
        playerShip.setY(gameData.getDisplayHeight() / 2);
        playerShip.setRadius(8);

        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(playerEntity);
    }
}