public class Guard {
    
    String guardVector;
    Coordinate guardPosition;

    public Guard(String guardVector, Coordinate guardPosition) {
        this.guardVector = guardVector;
        this.guardPosition = guardPosition;
    }


    public String getGuardDirection() {
        return guardVector;
    }


    public void setGuardDirection(String guardVector) {
        this.guardVector = guardVector;
    }


    public Coordinate getGuardPosition() {
        return guardPosition;
    }

    public void setGuardPosition(Coordinate guardPosition) {
        this.guardPosition = guardPosition;
    }

    public Coordinate moveGuard() {
        Coordinate currentPosition = getGuardPosition();
        Coordinate newPosition;
        switch (guardVector) {
            case "^":
                newPosition = new Coordinate(currentPosition.getX(), currentPosition.getY() - 1);
                break;
            case "v":
            newPosition = new Coordinate(currentPosition.getX(), currentPosition.getY() + 1);;
                break;
            case ">":
            newPosition = new Coordinate(currentPosition.getX() + 1, currentPosition.getY());
                break;
            case "<":
            newPosition = new Coordinate(currentPosition.getX() -1, currentPosition.getY());
                break;
            default:
                throw new IllegalStateException("Invalid guard vector: " + guardVector);
        }
        return newPosition;
    }

    public void rotateVector() {
        switch (guardVector) {
            case "^":
                setGuardDirection(">");
                break;
            case ">":
            setGuardDirection("v");
                break;
            case "v":
            setGuardDirection("<");
                break;
            case "<":
            setGuardDirection("^");
                break;
        }
    }

}
