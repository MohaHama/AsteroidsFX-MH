package dk.sdu.se4.common.services;

import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;

public interface IGamePluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}