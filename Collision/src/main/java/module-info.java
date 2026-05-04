import dk.sdu.se4.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;

    provides IPostEntityProcessingService with dk.sdu.se4.collisionsystem.CollisionDetector;
}