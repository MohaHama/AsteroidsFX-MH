module Core {
    requires Common;
    requires CommonBullet;
    requires javafx.graphics;
    requires spring.context;
    requires spring.core;
    requires spring.beans;

    exports dk.sdu.se4.core;
    opens dk.sdu.se4.core to javafx.graphics, spring.core;

    uses dk.sdu.se4.common.services.IGamePluginService;
    uses dk.sdu.se4.common.services.IEntityProcessingService;
    uses dk.sdu.se4.common.services.IPostEntityProcessingService;
    uses dk.sdu.se4.common.bullet.BulletSPI;
}