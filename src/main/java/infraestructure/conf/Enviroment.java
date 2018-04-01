package infraestructure.conf;

public enum Enviroment {
    APP_CONTEXT("fitme");

    private String property;

    Enviroment(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
