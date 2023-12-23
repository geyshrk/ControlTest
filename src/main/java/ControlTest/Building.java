package ControlTest;
public class Building implements Buildable, Changeable{
    Stage current;
    @Override
    public void start() {
        add(new Project());
        add(new Foundation());
        add(new Walls());
        add(new Roof());
        add(new Finishing());
        this.process();
    }
    public void process(){
        //Сам процесс стройки, при котором с шансом около 5% может появиться брак на одном из этапов
        //Если забраковали проект, то сразу завершаем стройку.
        while (current.next != null || current.status != Status.COMPLETED){
            if (Math.random() < 0.05f) {
                current.status = Status.REJECTED;
                if (current.getClass() == Project.class) {
                    end();
                    return;
                }
            }
            try {
                current = next();
            } catch (RejectedException e){
                System.out.println(e.getMessage());
                current = prev();
            }
            System.out.println(this);
        }
        end();
    }
    @Override
    public void end() {
        if (current.status == Status.REJECTED){
            System.out.println("Неудачная стройка!");
        } else {
            System.out.println("Стройка завершена успешно");
        }
    }

    @Override
    public Stage prev() {
        switch (current.status){
            case PLANNED: if (current.getClass() != Project.class) current = current.prev; return current;
            case UNDERWAY: current.status = Status.PLANNED; return current;
            case COMPLETED: current.status = Status.UNDERWAY; return current;
            case REJECTED: current.status = Status.PLANNED; current = prev(); return current;
        }
        return current;
    }

    @Override
    public Stage next() throws RejectedException {
        switch (current.status){
            case PLANNED: current.status = Status.UNDERWAY; return current;
            case UNDERWAY: current.status = Status.COMPLETED; return current;
            case COMPLETED: if (current.getClass() != Finishing.class) current = current.next; return current;
            case REJECTED: throw new RejectedException("Забраковано!");
        }
        return current;
    }
    public String toString(){
        return current + " " + current.status;
    }
    private void add(Stage stage){
        if (current == null){
            current = stage;
            current.next = current;
            current.prev = current;
        } else {
            current.prev.next = stage;
            stage.prev = current.prev;
            current.prev = stage;
        }
    }
}
