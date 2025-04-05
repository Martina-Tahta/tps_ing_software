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
        return newOneLink;
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

//    public Link addLink(Object v) {
//        Link newMultiLink = new MultiLink(this.value);
//        newMultiLink.nextLink = this.nextLink;
//        newMultiLink.prevLink = this;
//
//        this.nextLink = newMultiLink;
//        this.value = v;
//        return this;
//    }
    public Link addLink(Object v) {
        MultiLink currentL = new MultiLink(this.value);
        MultiLink newL = new MultiLink(v);

        currentL.nextLink = newL;
        currentL.prevLink = newL;
        newL.nextLink = currentL;
        newL.prevLink = currentL;

            return newL;
    }

//    public Link next() {
//        Link newOneLink = new OneLink(nextLink.value);
//        newOneLink.nextLink = nextLink.nextLink;
//        newOneLink.prevLink = nextLink.prevLink;
//
//        Link newMultiLink = new MultiLink(this.value);
//        newMultiLink.nextLink = newOneLink;
//        newMultiLink.prevLink = prevLink;
//        return newOneLink;
//    }

//    public Object current() {
//        return value;
//    }

    public Link next() {
        OneLink next = new OneLink(this.nextLink.value);
        next.nextLink = this.nextLink.nextLink;
        next.prevLink = this.nextLink.prevLink;
        return next;
    }

    public Object current() {
        return new MultiLink(this.value);
    }
    public Link removeLink() {
        return new EmptyLink(null);
    }
}

class MultiLink extends Link {
    public MultiLink(Object v) {
        super(v);
    }
    public Link addLink(Object v) {return this;}
    public Link next() {return this;}
    public Object current() {return this;}
    public Link removeLink() {
        Link newOneLink = new OneLink(nextLink.value);
        Link newMultiLink = new MultiLink(this.value);

        prevLink.nextLink = nextLink;
        nextLink.prevLink = prevLink;

        newMultiLink.value = nextLink.value;
        newMultiLink.nextLink = nextLink.nextLink;
        newMultiLink.prevLink = nextLink.prevLink;

        newOneLink.value = nextLink.nextLink.value;
        newOneLink.nextLink = nextLink.nextLink.nextLink;
        newOneLink.prevLink = nextLink.nextLink.prevLink;

        return nextLink;
    }

}