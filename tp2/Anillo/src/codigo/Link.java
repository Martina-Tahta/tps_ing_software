package codigo;

public abstract class Link {
    public Link nextLink;
    public Link prevLink;
    public Object value;

    public Link(Object v) {value = v;}

    public abstract Link addLink(Link link, Object cargo);
    public abstract Link removeLink(Link link);
    public abstract Link next();
    public abstract Object current();
}

class EmptyLink extends Link {
    public EmptyLink(Object v) {super(v);}

    public Link addLink(Link link, Object cargo) {
        MultiLink currentL = new MultiLink(cargo);
        currentL.nextLink = currentL;
        currentL.prevLink = currentL;
        return currentL;
    }

    public Link next() {
        throw new RuntimeException("No se puede hacer next en un anillo vacio"); //solo para que tire assertion, ver como hacer??
    }
    public Object current() {
        throw new RuntimeException("No hay current en un anillo vacio");
    }
    public Link removeLink(Link link) {
        return new EmptyLink(null);
    }
}

class MultiLink extends Link {
    public MultiLink(Object v) {super(v);}

    public Link addLink(Link link, Object v) {
        MultiLink newL = new MultiLink(v);

        newL.nextLink = link;
        newL.prevLink = link.prevLink;

        link.prevLink.nextLink = newL;
        link.prevLink = newL;

        return newL;
    }

    public Link next() {
        return this.nextLink;
    }

    public Object current() {
        return this.value;
    }

    public Link removeLink(Link link) {
        link.prevLink.nextLink = link.nextLink;
        link.nextLink.prevLink = link.prevLink;

        return link.nextLink;
    }
}

