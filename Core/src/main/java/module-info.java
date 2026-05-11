module Core {
    requires Common;
    requires javafx.controls;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.web;

    uses dk.sdu.se4.common.services.IGamePluginService;
    uses dk.sdu.se4.common.services.IEntityProcessingService;
    uses dk.sdu.se4.common.services.IPostEntityProcessingService;

    exports dk.sdu.se4.core;

    opens dk.sdu.se4.core to javafx.graphics, spring.core, spring.beans, spring.context;
}