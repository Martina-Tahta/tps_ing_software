package codigo;

import java.util.Stack;

public class State {
    private Stack<Link> stack;

    public State() {
        stack = new Stack<>();
        stack.push(new EmptyLink(null));
    }

    public State pushLink() {
        stack.push(new MultiLink(null));
        return this;
    }

    public Link popLink(Link link) {
        stack.pop();
        return stack.peek().removeLink(link);
    }
}
