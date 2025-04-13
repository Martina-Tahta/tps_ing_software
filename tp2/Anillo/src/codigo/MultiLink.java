package codigo;

class MultiLink extends Link {
    public MultiLink(Object v) {
        super(v);
    }

    public Link addLink(Object v) {
        MultiLink newL = new MultiLink(v);

        newL.nextLink = this;
        newL.prevLink = this.prevLink;
        this.prevLink.nextLink = newL;
        this.prevLink = newL;
        return newL;
    }

    public Link removeLink(Link link) {
        link.prevLink.nextLink = link.nextLink;
        link.nextLink.prevLink = link.prevLink;
        return link.nextLink;
    }

    public Link next() {
        return nextLink;
    }

    public Object current() {
        return value;
    }
}
