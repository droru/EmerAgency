package sample;

public abstract class Aview {

    private Controller controller=Controller.getInstance();

    public Controller getController() {
        return controller;
    }
}
