module Collision {
    requires Common;
    requires CommonBullet;
    requires CommonAsteroids;
    requires spring.web;

    uses dk.sdu.se4.common.asteroids.IAsteroidSplitter;

    provides dk.sdu.se4.common.services.IPostEntityProcessingService
            with dk.sdu.se4.collisionsystem.CollisionDetector;
}