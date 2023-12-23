package ControlTest;

public interface Changeable {
    Stage prev();
    Stage next() throws RejectedException;
}
