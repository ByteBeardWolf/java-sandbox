package maze;

public class Vertex {
    private final int x;
    private final int y;
    private Boolean visited;
    private int cost;
    private Vertex parent;
    private Boolean entrance;
    private Boolean exit;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        this.visited = false;
        this.cost = Integer.MAX_VALUE; // Initialize cost to a large value
        this.parent = null;
        this.entrance = false;
        this.exit = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Boolean isVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public Boolean isEntrance() {
        return entrance;
    }

    public void setEntrance(Boolean entrance) {
        this.entrance = entrance;
    }

    public Boolean isExit() {
        return exit;
    }

    public void setExit(Boolean exit) {
        this.exit = exit;
    }

}
