package dk.sdu.se4.collisionsystem;

import dk.sdu.se4.common.data.Entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollisionDetectorTest {

    @Test
    public void testCollisionDetected() {
        CollisionDetector detector = new CollisionDetector();

        Entity first = new Entity();
        first.setX(0);
        first.setY(0);
        first.setRadius(10);

        Entity second = new Entity();
        second.setX(5);
        second.setY(0);
        second.setRadius(10);

        assertTrue(detector.collides(first, second));
    }

    @Test
    public void testNoCollision() {
        CollisionDetector detector = new CollisionDetector();

        Entity first = new Entity();
        first.setX(0);
        first.setY(0);
        first.setRadius(5);

        Entity second = new Entity();
        second.setX(100);
        second.setY(100);
        second.setRadius(5);

        assertFalse(detector.collides(first, second));
    }

    @Test
    public void testTouchingEdgesIsNotCollision() {
        CollisionDetector detector = new CollisionDetector();

        Entity first = new Entity();
        first.setX(0);
        first.setY(0);
        first.setRadius(10);

        Entity second = new Entity();
        second.setX(20);
        second.setY(0);
        second.setRadius(10);

        assertFalse(detector.collides(first, second));
    }
}
