import dk.sdu.se4.common.services.IEntityProcessingService;
import dk.sdu.se4.common.services.IGamePluginService;

module Enemy {
    requires Common;
    requires CommonBullet;

    uses dk.sdu.se4.common.bullet.BulletSPI;

    provides IGamePluginService with dk.sdu.se4.enemy.EnemyPlugin;
    provides IEntityProcessingService with dk.sdu.se4.enemy.EnemyControlSystem;
}