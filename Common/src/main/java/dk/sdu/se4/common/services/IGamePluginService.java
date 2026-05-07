package dk.sdu.se4.common.services;

import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;

public interface IGamePluginService {

    /**
     * Pre-condition: gameData and world are not null.
     * Post-condition: the component has added the entities it needs to the world.
     *
     * @param gameData the game data used by the component
     * @param world the world where entities are stored
     */
    void start(GameData gameData, World world);

    /**
     * Pre-condition: gameData and world are not null.
     * Post-condition: the component has removed the entities it added from the world.
     *
     * @param gameData the game data used by the component
     * @param world the world where entities are stored
     */
    void stop(GameData gameData, World world);
}
