public class Coordinate {

    int x;
    int y;

    public Coordinate(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void setX(int x) {
        this.x = x;
    }

    private void setY(int y) {
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Coordinate[] getAdjacent() {
        Coordinate[] adjacent = new Coordinate[8];
        adjacent[0] = new Coordinate(x - 1, y - 1);
        adjacent[1] = new Coordinate(x, y - 1);
        adjacent[2] = new Coordinate(x + 1, y - 1);
        adjacent[3] = new Coordinate(x - 1, y);
        adjacent[4] = new Coordinate(x + 1, y);
        adjacent[5] = new Coordinate(x - 1, y + 1);
        adjacent[6] = new Coordinate(x, y + 1);
        adjacent[7] = new Coordinate(x + 1, y + 1);
        return adjacent;
    }


    @Override
    public int hashCode() {
        return x * 31 + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Coordinate)) {
            return false;
        }
        Coordinate c = (Coordinate) obj;
        return c.getX() == x && c.getY() == y;
    }

    
}
