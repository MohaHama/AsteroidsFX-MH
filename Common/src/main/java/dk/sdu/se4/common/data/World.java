package dk.sdu.se4.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private final Map<String, Entity> entities = new ConcurrentHashMap<>();

    public String addEntity(Entity entity) {
        entities.put(entity.getID(), entity);
        return entity.getID();
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity.getID());
    }

    public Collection<Entity> getEntities() {
        return entities.values();
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> result = new ArrayList<>();

        for (Entity entity : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(entity.getClass())) {
                    result.add(entity);
                }
            }
        }

        return result;
    }

    public Entity getEntity(String id) {
        return entities.get(id);
    }
}