package dk.sdu.se4.player;

import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.GameKeys;
import dk.sdu.se4.common.data.World;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerMovementTest {

    @Test
    public void testPlayerMovesForward() {
        GameData gameData = new GameData();
        World world = new World();
        PlayerControlSystem controlSystem = new PlayerControlSystem();

        Entity player = new Player();
        player.setX(400);
        player.setY(400);
        player.setRotation(0);
        player.setRadius(8);
        player.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        world.addEntity(player);

        gameData.getKeys().setKey(GameKeys.UP, true);
        controlSystem.process(gameData, world);

        assertTrue(player.getX() > 400);
    }

    @Test
    public void testPlayerRotatesLeft() {
        GameData gameData = new GameData();
        World world = new World();
        PlayerControlSystem controlSystem = new PlayerControlSystem();

        Entity player = new Player();
        player.setX(400);
        player.setY(400);
        player.setRotation(90);
        player.setRadius(8);
        player.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        world.addEntity(player);

        gameData.getKeys().setKey(GameKeys.LEFT, true);
        controlSystem.process(gameData, world);

        assertTrue(player.getRotation() < 90);
    }

    @Test
    public void testPlayerRotatesRight() {
        GameData gameData = new GameData();
        World world = new World();
        PlayerControlSystem controlSystem = new PlayerControlSystem();

        Entity player = new Player();
        player.setX(400);
        player.setY(400);
        player.setRotation(90);
        player.setRadius(8);
        player.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        world.addEntity(player);

        gameData.getKeys().setKey(GameKeys.RIGHT, true);
        controlSystem.process(gameData, world);

        assertTrue(player.getRotation() > 90);
    }
}