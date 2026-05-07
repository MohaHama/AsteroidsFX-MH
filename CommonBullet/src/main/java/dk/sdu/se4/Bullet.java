package dk.sdu.se4.common.bullet;

import dk.sdu.se4.common.data.Entity;

public class Bullet extends Entity {

    private String ownerID;

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
}
