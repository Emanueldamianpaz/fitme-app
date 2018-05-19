package ui;

import spark.*;

public class UiRouter extends Router {

    @Override
    public RouteGroup routes() {
        return () -> {
        };
    }

    @Override
    public String path() {
        return "/";
    }
}
