package service;

import com.google.inject.AbstractModule;
import service.routine.RoutineService;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(RoutineService.class);

    }
}
