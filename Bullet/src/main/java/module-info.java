import dk.sdu.se4.common.bullet.BulletSPI;
import dk.sdu.se4.common.services.IEntityProcessingService;
import dk.sdu.se4.common.services.IGamePluginService;

module Bullet {
    requires Common;
    requires CommonBullet;

    provides IGamePluginService with dk.sdu.se4.bullet.BulletPlugin;
    provides BulletSPI with dk.sdu.se4.bullet.BulletControlSystem;
    provides IEntityProcessingService with dk.sdu.se4.bullet.BulletControlSystem;
}