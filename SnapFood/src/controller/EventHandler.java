package controller;

public class EventHandler {
    public static void eventHandle(int input, EventHandlerInterface... methodReferences) {
        methodReferences[input].run();
    }
}
