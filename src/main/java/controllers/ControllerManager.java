package controllers;

import javafx.scene.Node;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Роман on 28.01.2017.
 */
public class ControllerManager {
    private static ControllerManager ourInstance = new ControllerManager();
    private static HashMap<String,Ctrl> controllers = new HashMap<String,Ctrl>();
    private static HashMap<String,Scene> scenes = new HashMap<String, Scene>();
    public static void addController(String key, Ctrl controller){
        System.out.println(controllers.get(key));
        if(controllers.get(key)==null){
            controllers.put(key,controller);
        }
    }
    public static void addScene(String key, Scene scene){
        System.out.println(scenes.get(key));
        if(scenes.get(key)==null){
            scenes.put(key,scene);
        }
    }
    public static HashMap<String, Scene> getScenes(){
        return scenes;
    }
    public static HashMap<String,Ctrl> getControllers() {
        return controllers;
    }

    public static ControllerManager getInstance() {
        return ourInstance;
    }

    private ControllerManager() {

    }
}
