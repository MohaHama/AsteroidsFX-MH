module Bullet {
    requires Common;
    requires CommonBullet;

    provides dk.sdu.se4.common.bullet.BulletSPI
            with dk.sdu.se4.bullet.BulletControlSystem;

    provides dk.sdu.se4.common.services.IEntityProcessingService
            with dk.sdu.se4.bullet.BulletControlSystem;
}