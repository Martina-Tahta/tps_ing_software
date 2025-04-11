package codigo;

public class Ring {
    private Link currentLink;
    private State currentState;

    public Ring() {
        currentLink = new EmptyLink(null);
        currentState = new State();
    }

    public Ring next() {
        currentLink = currentLink.next();
        return this;
    }

    public Object current() {
        return currentLink.current();
    }

    public Ring add(Object cargo) {
        currentLink = currentLink.addLink(cargo);
        currentState = currentState.pushLink();
        return this;
    }

    public Ring remove() {
        currentLink = currentState.popLink(currentLink);
        return this;
    }
}
