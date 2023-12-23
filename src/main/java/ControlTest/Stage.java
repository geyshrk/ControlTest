package ControlTest;

public abstract class Stage{
    Status status;
    Stage next;
    Stage prev;
    public Stage(){
        status = Status.PLANNED;
    }
}
