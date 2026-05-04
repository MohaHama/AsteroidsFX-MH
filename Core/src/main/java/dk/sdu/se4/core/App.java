package dk.sdu.se4.core;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App extends Application {

    public static void main(String[] args) {
        launch(App.class);
    }

    @Override
    public void start(Stage window) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(ModuleConfig.class);

        Game game = ctx.getBean(Game.class);
        game.start(window);
        game.render();
    }
}