module Enemy {
    requires Common;
    requires CommonBullet;

    uses dk.sdu.se4.common.bullet.BulletSPI;

    provides dk.sdu.se4.common.services.IGamePluginService
            with dk.sdu.se4.enemy.EnemyPlugin;

    provides dk.sdu.se4.common.services.IEntityProcessingService
            with dk.sdu.se4.enemy.EnemyControlSystem;
}