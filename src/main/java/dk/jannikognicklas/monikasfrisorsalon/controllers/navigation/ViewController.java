package dk.jannikognicklas.monikasfrisorsalon.controllers.navigation;

public interface ViewController<T> {
    void setViewSwitcher(ViewSwitcher viewSwitcher);
    void setService(T service);
}
