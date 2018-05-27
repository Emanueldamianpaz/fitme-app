package ar.edu.davinci.infraestructure;


import spark.RouteGroup;

public abstract class Router {

    public abstract RouteGroup routes();

    public abstract String path();


}
