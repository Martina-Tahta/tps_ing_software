package codigo;

public class Ring {
    private Link currentLink;

    public Ring() {
        currentLink = new EmptyLink(null);
    }

    public Ring next() {
        currentLink = currentLink.next();
        return this;
    }

    public Object current() {
        return currentLink.current();
    }

    public Ring add( Object cargo ) {
        currentLink = currentLink.addLink(cargo);
        return this;
    }

    public Ring remove() {
        currentLink = currentLink.removeLink();
        return this;
    }
}
