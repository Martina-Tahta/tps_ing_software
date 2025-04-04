package codigo;



public class Ring {
    private Link currentLink;

    public Ring() {
        currentLink = new EmptyLink(null);
    }

    public Ring next() {
        currentLink = currentLink.next();
        return this;
    }

    public Object current() {
        return currentLink.current();
    }


    public Ring add( Object cargo ) {
        currentLink = currentLink.addLink(cargo);
        return this;

    }

    public Ring remove() {
        currentLink = currentLink.removeLink();
        return this;
    }
}


// Pasan los tests pero con if:
//public class Ring {
//    private Ring currentNode;
//    private Object value;
//
//    public Ring next() {
//        if (this.currentNode == null){
//            return this.currentNode.currentNode;
//        }
//        return currentNode;
//    }
//
//    public Object current() {
//        return currentNode.value;
//    }
//
//
//    public Ring add( Object cargo ) {
//        Ring newRing = new Ring();
//        newRing.value = cargo;
//
//        if (this.currentNode == null) {
//            newRing.currentNode = newRing;
//            return newRing;
//        } else {
//            newRing.currentNode = this.currentNode;
//            this.currentNode = newRing;
//            return this;
//        }
//
//    }
//
//    public Ring remove() {
//        if (this.currentNode == this.currentNode.currentNode){
//            return new Ring();
//        }
//        this.currentNode = this.currentNode.currentNode;
//        return this;
//    }
//}
