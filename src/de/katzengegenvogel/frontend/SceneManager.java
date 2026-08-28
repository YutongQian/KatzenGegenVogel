package de.katzengegenvogel.frontend;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
/**
 * <pre>
 *
 * Verwaltet die verschiedenen Szenen der Anwendung.
 * Der SceneManager speichert die verfügbaren Szenen und ermöglicht
 * das Wechseln zwischen den einzelnen Szenen. Dadurch können die
 * verschiedenen Bereiche des Spiels, wie Registrierung, Menü,
 * Spiel, Game-Over und Gewinnszene, zentral verwaltet werden.</pre>
 *

 */
public class SceneManager {

    private static Stage primaryStage;

    private static final Map<String,SceneInter> sceneObjekt = new HashMap<>();

    private static final Map<String,Scene> scenes=new HashMap<>();

    private static SceneInter aktuelleScene;

    /**
     * Initialisiert den SceneManager und erstellt die benötigten Szenen.
     *
     * <p>Die erstellten Szenen werden gespeichert, sodass sie später
     * über ihren Namen abgerufen und angezeigt werden können.</p>
     */
    public static void init(Stage stage){
        primaryStage=stage;
    }
    /**
     * Registriert eine neue Szene im SceneManager.
     * Das Szenenobjekt und die dazugehörige JavaFX-Szene werden
     * unter dem angegebenen Namen gespeichert. Dadurch können sie
     * später über den Szenennamen abgerufen und verwendet werden.
     *
     * @param sceneName Name, unter dem die Szene gespeichert wird
     * @param sceneObj Objekt der Szene
     * @param scene JavaFX-Szene, die angezeigt werden soll
     */
    public static void registiertScene(String sceneName,SceneInter sceneObj, Scene scene){
        sceneObjekt.put(sceneName,sceneObj);
        scenes.put(sceneName,scene);
    }
    /**
     * Gibt das Szenenobjekt mit dem angegebenen Namen zurück.
     *
     * @param name Name des gesuchten Szenenobjekts
     * @return das Szenenobjekt oder {@code null}, wenn es nicht gefunden wurde
     */
    public static Object getSceneObkect(String name){
        return sceneObjekt.get(name);

    }
    /**
     * Wechselt zu einer gespeicherten Szene.
     *
     * <p>Die Szene wird anhand ihres Namens gesucht. Wenn eine passende
     * Szene gefunden wird, wird sie auf der Hauptbühne angezeigt.
     * Falls keine passende Szene existiert, wird stattdessen das Menü geöffnet.</p>
     * @param name Name der gewünschten Szene
     */
    public static void changeScene(String name) {

        if(aktuelleScene != null){
            aktuelleScene.destroy();

        }
        Scene zielScene=scenes.get(name);
        SceneInter zielSceneObjekt= sceneObjekt.get(name);


        if(zielScene != null && zielSceneObjekt != null){
            aktuelleScene=zielSceneObjekt;

            primaryStage.setScene(zielScene);

            primaryStage.show();
        }
        else{
            System.out.println("Scene wird nicht gefunden"+name);
            changeScene("Menu");
        }
    }
}
