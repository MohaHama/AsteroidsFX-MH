package dk.sdu.se4.common.services;

import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;

public interface IPostEntityProcessingService {

    /**
     * Pre-condition: gameData and world are not null, and entity processors have already run.
     * Post-condition: the component has processed rules that depend on updated entity positions.
     *
     * @param gameData the game data used by the component
     * @param world the world where entities are stored
     */
    void process(GameData gameData, World world);
}
