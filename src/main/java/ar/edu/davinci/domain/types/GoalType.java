package ar.edu.davinci.domain.types;

public enum GoalType {
    GAIN_WEIGHT("gain_weight"),
    LOSS_WEIGHT("loss_weight");

    private String type;

    GoalType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}