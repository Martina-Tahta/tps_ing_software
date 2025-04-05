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
    public abstract Link joinWith(Link other);
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

    public Link joinWith(Link other) {
        throw new RuntimeException("No se puede hacer join con un anillo vacío");
    }

    public Link removeLink() {
        throw new RuntimeException("No hay nada para eliminar en un anillo vacio");
    }
}


class OneLink extends Link {
    public OneLink(Object v) {
        super(v);
        this.nextLink = this;
        this.prevLink = this;
    }

    public Link addLink(Object v) {
        TwoLink link = new TwoLink(this.value, v);
        return link.nextLink;
    }

    public Link next() {
        return this;
    }

    public Object current() {
        return value;
    }

    public Link joinWith(Link other) {
        throw new RuntimeException("No se puede hacer join con un solo nodo");
    }

    public Link removeLink() {
        return new EmptyLink(null);
    }
}


class TwoLink extends Link {
    public TwoLink(Object v1, Object v2) {
        super(v1);
        Link other = new OneLink(v2);

        this.nextLink = other;
        this.prevLink = other;
        other.nextLink = this;
        other.prevLink = this;
    }


    public Link addLink(Object v) {
        MultiLink newNode = new MultiLink(v);

        newNode.nextLink = this.nextLink;
        newNode.prevLink = this;

        this.nextLink.prevLink = newNode;
        this.nextLink = newNode;

        return newNode;
    }

    public Link next() {
        return nextLink;
    }

    public Link joinWith(Link other) {
        return new OneLink(this.value);
    }


    public Object current() {
        return value;
    }

    public Link removeLink() {
        this.prevLink.nextLink = this.nextLink;
        this.nextLink.prevLink = this.prevLink;
        return this.prevLink.joinWith(this.nextLink);
    }
}


class MultiLink extends Link {
    public MultiLink(Object v) {
        super(v);
    }

    public Link addLink(Object v) {
        MultiLink newNode = new MultiLink(v);

        newNode.nextLink = this.nextLink;
        newNode.prevLink = this;

        this.nextLink.prevLink = newNode;
        this.nextLink = newNode;

        return newNode;
    }

    public Link next() {
        return nextLink;
    }


    public Object current() {
        return value;
    }


    public Link joinWith(Link other) {
        return this;
    }

    public Link removeLink() {
        this.prevLink.nextLink = this.nextLink;
        this.nextLink.prevLink = this.prevLink;
        return this.prevLink.joinWith(this.nextLink);
    }
}

