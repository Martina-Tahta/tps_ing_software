package codigo;

public abstract class Link {
    public Link nextLink;
    public Link prevLink;
    public Object value;

    public Link(Object v) {value = v;}
    public abstract Link addLink(Object cargo);
    public abstract Link removeLink(Link link);
    public abstract Link next();
    public abstract Object current();
}

