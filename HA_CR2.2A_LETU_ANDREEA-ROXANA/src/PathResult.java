import java.util.List;

// Class to store the result of a path calculation
class PathResult {
    List<Integer> path; // Sequence of cities in the path
    int cost; // Total cost of the path

    // Constructor to initialize a PathResult object with a path and its cost
    PathResult(List<Integer> path, int cost) {
        this.path = path;
        this.cost = cost;
    }

    // Method to return a string representation of the PathResult object
    @Override
    public String toString() {
        return "Path: " + path + ", Cost: " + cost; // Return the path and its cost as a string
    }
}
