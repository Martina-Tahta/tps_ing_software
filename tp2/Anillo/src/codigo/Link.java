package codigo;

public abstract class Link {
    public Link nextLink;
    public Link prevLink;
    public Object value;

    public Link(Object v) {
        value = v;
    }

    public abstract Link addLink(Object cargo);
    public abstract Link removeLink();
    public abstract Link next();
    public abstract Object current();
}

class EmptyLink extends Link {
    public EmptyLink(Object v) {
        super(v);
    }
    public Link addLink(Object cargo) {
        Link newOneLink = new OneLink(cargo);
        newOneLink.nextLink = this;
        newOneLink.prevLink = this;
        return new OneLink(cargo);
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
    public OneLink(Object v) {
        super(v);
    }

    public Link addLink(Object v) {
        Link newFullLink = new FullLink(this.value);
        newFullLink.nextLink = this.nextLink;
        newFullLink.prevLink = this;

        this.nextLink = newFullLink;
        this.value = v;
        return this;
    }

    public Link next() {
        Link newOneLink = new OneLink(nextLink.value);
        newOneLink.nextLink = nextLink.nextLink;
        newOneLink.prevLink = nextLink.prevLink;

        Link newFullLink = new FullLink(this.value);
        newFullLink.nextLink = newOneLink;
        newFullLink.prevLink = prevLink;
        return newOneLink;
    }

    public Object current() {
        return value;
    }
    public Link removeLink() {
        return new EmptyLink(null);
    }
}

class FullLink extends Link {
    public FullLink(Object v) {
        super(v);
    }
    public Link addLink(Object v) {return this;}
    public Link next() {return this;}
    public Object current() {return this;}
    public Link removeLink() {return this;}
    
}