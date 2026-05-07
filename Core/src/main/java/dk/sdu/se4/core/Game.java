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
import javafx.scene.input.KeyCode;
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

    Game(List<IGamePluginService> plugins,
         List<IEntityProcessingService> processors,
         List<IPostEntityProcessingService> postProcessors) {
        this.plugins = plugins;
        this.processors = processors;
        this.postProcessors = postProcessors;
    }

    public void start(Stage window) {
        scoreText = new Text(10, 20, "Total Asteroids Destroyed: 0");

        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(scoreText);

        Scene scene = new Scene(gameWindow);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) gameData.getKeys().setKey(GameKeys.LEFT, true);
            if (e.getCode() == KeyCode.RIGHT) gameData.getKeys().setKey(GameKeys.RIGHT, true);
            if (e.getCode() == KeyCode.UP) gameData.getKeys().setKey(GameKeys.UP, true);
            if (e.getCode() == KeyCode.SPACE) gameData.getKeys().setKey(GameKeys.SPACE, true);
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) gameData.getKeys().setKey(GameKeys.LEFT, false);
            if (e.getCode() == KeyCode.RIGHT) gameData.getKeys().setKey(GameKeys.RIGHT, false);
            if (e.getCode() == KeyCode.UP) gameData.getKeys().setKey(GameKeys.UP, false);
            if (e.getCode() == KeyCode.SPACE) gameData.getKeys().setKey(GameKeys.SPACE, false);
        });

        for (IGamePluginService plugin : plugins) {
            plugin.start(gameData, world);
        }

        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
    }

    public void render() {
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

        for (IPostEntityProcessingService post : postProcessors) {
            post.process(gameData, world);
        }
    }

    private void draw() {
        scoreText.setText("Total Asteroids Destroyed: " + gameData.getScore());

        for (String id : polygons.keySet()) {
            if (world.getEntity(id) == null) {
                Polygon polygon = polygons.get(id);
                gameWindow.getChildren().remove(polygon);
                polygons.remove(id);
            }
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity.getID());

            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                setColor(entity, polygon);
                polygons.put(entity.getID(), polygon);
                gameWindow.getChildren().add(polygon);
            }

            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }
    }

    private void setColor(Entity entity, Polygon polygon) {
        String name = entity.getClass().getSimpleName();

        if (name.equals("Asteroid")) {
            polygon.setFill(Color.DARKOLIVEGREEN);
        }

        if (name.equals("Enemy")) {
            polygon.setFill(Color.RED);
        }
    }
}