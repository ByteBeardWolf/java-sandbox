package maze;

import java.util.Comparator;

public class VertexComparator implements Comparator<Vertex> {
    @Override
    public int compare(Vertex v1, Vertex v2) {
        return v1.isVisited() == v2.isVisited() ? (Integer.compare(v1.getCost(), v2.getCost())) : (v1.isVisited() == true ? 1 : -1);
    }
}
