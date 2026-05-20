package dk.sdu.se4.collisionsystem;

import dk.sdu.se4.common.data.Entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollisionDetectorTest {

    @Test
    public void testCollisionDetected() {
        CollisionDetector detector = new CollisionDetector();

        //first entity
        Entity first = new Entity();
        first.setX(0);
        first.setY(0);
        first.setRadius(10);

        //second entity close enough to collide
        Entity second = new Entity();
        second.setX(5);
        second.setY(0);
        second.setRadius(10);

        assertTrue(detector.collides(first, second), "Entities should collide when they overlap.");
    }

    @Test
    public void testNoCollision() {
        CollisionDetector detector = new CollisionDetector();

        // first entity
        Entity first = new Entity();
        first.setX(0);
        first.setY(0);
        first.setRadius(5);

        // second entity far away
        Entity second = new Entity();
        second.setX(100);
        second.setY(100);
        second.setRadius(5);

        assertFalse(detector.collides(first, second), "Entities should not collide when they are far apart.");
    }

    @Test
    public void testTouchingEdgesIsNotCollision() {
        CollisionDetector detector = new CollisionDetector();

        // first entity
        Entity first = new Entity();
        first.setX(0);
        first.setY(0);
        first.setRadius(10);

        // second entity only touches the edge of first entity
        Entity second = new Entity();
        second.setX(20);
        second.setY(0);
        second.setRadius(10);

        assertFalse(detector.collides(first, second), "Entities touching edges should not count as collision.");
    }
}