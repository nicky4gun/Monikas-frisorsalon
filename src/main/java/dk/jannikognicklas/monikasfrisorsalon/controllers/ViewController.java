package dk.jannikognicklas.monikasfrisorsalon.controllers;

public interface ViewController<T> {
    void setViewSwitcher(ViewSwitcher viewSwitcher);
    void setService(T service);
}
