import dk.sdu.se4.common.services.IEntityProcessingService;
import dk.sdu.se4.common.services.IGamePluginService;

module Player {
    requires Common;
    requires CommonBullet;

    uses dk.sdu.se4.common.bullet.IBullet;

    provides IGamePluginService with dk.sdu.se4.player.PlayerPlugin;
    provides IEntityProcessingService with dk.sdu.se4.player.PlayerControlSystem;
}