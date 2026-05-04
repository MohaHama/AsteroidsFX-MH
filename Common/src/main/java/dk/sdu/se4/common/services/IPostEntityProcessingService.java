package dk.sdu.se4.common.services;

import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;

public interface IPostEntityProcessingService {
    void process(GameData gameData, World world);
}