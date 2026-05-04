package dk.sdu.se4.collisionsystem;

import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity first : world.getEntities()) {
            for (Entity second : world.getEntities()) {
                if (first.getID().equals(second.getID())) continue;

                // Don't collide same types
                if (first.getClass().equals(second.getClass())) continue;

                if (collides(first, second)) {
                    world.removeEntity(first);
                    world.removeEntity(second);
                }
            }
        }
    }

    public boolean collides(Entity first, Entity second) {
        float dx = (float) first.getX() - (float) second.getX();
        float dy = (float) first.getY() - (float) second.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < first.getRadius() + second.getRadius();
    }
}