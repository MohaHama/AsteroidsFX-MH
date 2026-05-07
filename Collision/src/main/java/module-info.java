import dk.sdu.se4.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonAsteroids;
    requires CommonBullet;
    requires spring.web;

    uses dk.sdu.se4.common.asteroids.IAsteroidSplitter;

    provides IPostEntityProcessingService with dk.sdu.se4.collisionsystem.CollisionDetector;
}
