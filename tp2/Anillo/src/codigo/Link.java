package codigo;

public abstract class Link {
    public Link nextLink;
    public Link prevLink;
    public Object value;

    public Link(Object v) {value = v;}

    public abstract Link addLink(Object cargo);
    public abstract Link removeLink();
    public abstract Link next();
    public abstract Object current();
}

class EmptyLink extends Link {
    public EmptyLink(Object v) {super(v);}

    public Link addLink(Object cargo) {
        OneLink currentL = new OneLink(cargo);
        currentL.nextLink = this;
        currentL.prevLink = this;
        return currentL;
    }

    public Link next() {
        throw new RuntimeException("No se puede hacer next en un anillo vacio"); //solo para que tire assertion, ver como hacer??
    }
    public Object current() {
        throw new RuntimeException("No hay current en un anillo vacio");
    }

    public Link removeLink() {
        throw new RuntimeException("No hay nada para eliminar en un anillo vacio");
    }
}

class OneLink extends Link {
    public OneLink(Object v) {super(v);}

    public Link addLink(Object v) {
        MultiLink currentL = new MultiLink(v);
        MultiLink nextL = new MultiLink(this.value);

        currentL.nextLink = nextL;
        currentL.prevLink = nextL;

        nextL.nextLink = currentL;
        nextL.prevLink = currentL;

        return currentL;
    }

    public Link next() {
        return this;
    }

    public Object current() {
        return this.value;
    }
    public Link removeLink() {
        return new EmptyLink(null);
    }
}

class MultiLink extends Link {
    public MultiLink(Object v) {super(v);}

    public Link addLink(Object v) {
        MultiLink newL = new MultiLink(v);

        newL.nextLink = this;
        newL.prevLink = this.prevLink;

        this.prevLink.nextLink = newL;
        this.prevLink = newL;

        return newL;
    }

    public Link next() {
        return this.nextLink;
    }

    public Object current() {
        return this.value;
    }

    public Link removeLink() {
        this.prevLink.nextLink = this.nextLink;
        this.nextLink.prevLink = this.prevLink;

        return this.nextLink;
    }
}