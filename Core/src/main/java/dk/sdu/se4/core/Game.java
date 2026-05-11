package dk.sdu.se4.core;

import dk.sdu.se4.common.data.Entity;
import dk.sdu.se4.common.data.GameData;
import dk.sdu.se4.common.data.GameKeys;
import dk.sdu.se4.common.data.World;
import dk.sdu.se4.common.services.IEntityProcessingService;
import dk.sdu.se4.common.services.IGamePluginService;
import dk.sdu.se4.common.services.IPostEntityProcessingService;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Game {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<String, Polygon> polygons = new ConcurrentHashMap<>();

    private final Pane gameWindow = new Pane();

    private final List<IGamePluginService> plugins;
    private final List<IEntityProcessingService> processors;
    private final List<IPostEntityProcessingService> postProcessors;

    private Text scoreText;

    Game(
            List<IGamePluginService> plugins,
            List<IEntityProcessingService> processors,
            List<IPostEntityProcessingService> postProcessors
    ) {
        this.plugins = plugins;
        this.processors = processors;
        this.postProcessors = postProcessors;
    }

    void start(Stage window) {

        scoreText = new Text(10, 20, "Total Asteroids Destroyed: 0");

        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(scoreText);

        Scene scene = new Scene(gameWindow);

        scene.setOnKeyPressed(event -> {

            if (event.getCode().toString().equals("LEFT")) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }

            if (event.getCode().toString().equals("RIGHT")) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }

            if (event.getCode().toString().equals("UP")) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }

            if (event.getCode().toString().equals("SPACE")) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
        });

        scene.setOnKeyReleased(event -> {

            if (event.getCode().toString().equals("LEFT")) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }

            if (event.getCode().toString().equals("RIGHT")) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }

            if (event.getCode().toString().equals("UP")) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }

            if (event.getCode().toString().equals("SPACE")) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
        });

        for (IGamePluginService plugin : plugins) {
            plugin.start(gameData, world);
        }

        window.setScene(scene);
        window.setTitle("Asteroids");
        window.show();
    }

    void render() {

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();
            }
        }.start();
    }

    private void update() {

        for (IEntityProcessingService processor : processors) {
            processor.process(gameData, world);
        }

        for (IPostEntityProcessingService processor : postProcessors) {
            processor.process(gameData, world);
        }
    }

    private void draw() {

        scoreText.setText("Total Asteroids Destroyed: " + gameData.getScore());

        for (Entity entity : world.getEntities()) {

            Polygon polygon = polygons.get(entity.getID());

            if (polygon == null) {

                polygon = new Polygon(entity.getPolygonCoordinates());

                if (entity.getClass().getSimpleName().equals("Player")) {
                    polygon.setFill(Color.BLACK);
                }
                else if (entity.getClass().getSimpleName().equals("Enemy")) {
                    polygon.setFill(Color.BLUE);
                }
                else if (entity.getClass().getSimpleName().equals("Asteroid")) {
                    polygon.setFill(Color.GRAY);
                }
                else {
                    polygon.setFill(Color.RED);
                }

                polygons.put(entity.getID(), polygon);
                gameWindow.getChildren().add(polygon);
            }

            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());

            if (entity.getFlashUntil() > System.currentTimeMillis()) {

                if ((System.currentTimeMillis() / 100) % 2 == 0) {
                    polygon.setOpacity(0.1);
                }
                else {
                    polygon.setOpacity(1);
                }
            }
            else {
                polygon.setOpacity(1);
            }
        }

        polygons.keySet().removeIf(id -> {

            Entity entity = world.getEntity(id);

            if (entity == null) {
                gameWindow.getChildren().remove(polygons.get(id));
                return true;
            }

            return false;
        });
    }
}