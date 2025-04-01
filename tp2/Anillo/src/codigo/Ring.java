package codigo;

public class Ring {
    private ArrayList<Object> ringList = new ArrayList<>();
    private int currentPos = 0;
    private int count = 0;

    public Ring next() {
        currentPos = (currentPos+1) % count;
        return this;
    }

    public Object current() {
        return ringList.get(currentPos);
    }

    public Ring add( Object cargo ) {
        ringList.add(currentPos, cargo);
        count++;
        return this;
    }

    public Ring remove() {
        if (count == 0) return this;
        ringList.remove(currentPos);
        count--;
        currentPos = (currentPos) % count;
        return this;
    }
}

