package codigo;

import java.util.Stack;

public class State {
    private Stack<Link> stack;

    public State() {
        stack = new Stack<>();
        stack.push(new EmptyLink(null));
        stack.push(new EmptyLink(null));
    }

    public Link addLink(Link link, Object cargo) {
        Link current = stack.peek();
        Link newCurrent = current.addLink(link, cargo);

        stack.push(new MultiLink(null));
        return newCurrent;
    }

    public Link removeLink(Link link) {
        stack.pop();
        Link current = stack.peek();
        Link newCurrent = current.removeLink(link);

        return newCurrent;
    }

    public State manageState(State currentState) {
        Link current = stack.peek();
        return current.manageState(currentState);
    }

}
