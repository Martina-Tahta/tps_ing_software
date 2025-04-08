package codigo;

import java.util.Stack;

public class State {
    private Stack<Link> pila;

    public State() {
        pila = new Stack<>();
        pila.push(new EmptyLink(null));
    }

    public Link addLink(Link link, Object cargo) {
        Link current = pila.peek();
        Link newCurrent = current.addLink(link, cargo);

        pila.push(new MultiLink(null));
        return newCurrent;
    }

    public Link removeLink(Link link) {
        pila.pop();
        Link current = pila.peek();
        Link newCurrent = current.removeLink(link);

        return newCurrent;
    }

}
