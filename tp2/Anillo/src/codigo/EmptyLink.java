package codigo;

class EmptyLink extends Link {
    public EmptyLink(Object v) {
        super(v);
    }

    public Link addLink(Object cargo) {
        MultiLink currentL = new MultiLink(cargo);
        currentL.nextLink = currentL;
        currentL.prevLink = currentL;
        return currentL;
    }

    public Link removeLink(Link link) {
        return new EmptyLink(null);
    }

    public Link next() {
        throw new RuntimeException("No se puede hacer next en un anillo vacio"); //solo para que tire assertion, ver como hacer??
    }

    public Object current() {
        throw new RuntimeException("No hay current en un anillo vacio");
    }
}
