package ControlTest;

public enum Status {
    PLANNED, UNDERWAY, COMPLETED, REJECTED;

    @Override
    public String toString() {
        switch (this){
            case PLANNED: return "Запланирован";
            case UNDERWAY: return "Осуществляется";
            case COMPLETED: return "Выполнен";
            case REJECTED: return "Забракован";
        }
        return "Чё.";
    }
}
