package codigo;

public class Ring {
    private Ring currentNode;
    private Object value;

    public Ring next() {
        if (this.currentNode == null){
            return this.currentNode.currentNode;
        }
        return currentNode;
    }

    public Object current() {
        return currentNode.value;
    }


    public Ring add( Object cargo ) {
        Ring newRing = new Ring();
        newRing.value = cargo;

        if (this.currentNode == null) {
            newRing.currentNode = newRing;
            return newRing;
        } else {
            newRing.currentNode = this.currentNode;
            this.currentNode = newRing;
            return this;
        }

    }

    public Ring remove() {
        if (this.currentNode == this.currentNode.currentNode){
            return new Ring();
        }
        this.currentNode = this.currentNode.currentNode;
        return this;
    }
}
