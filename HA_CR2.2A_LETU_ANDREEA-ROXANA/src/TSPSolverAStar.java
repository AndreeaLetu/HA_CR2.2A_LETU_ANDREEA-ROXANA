import java.util.*;

// Class for solving the Traveling Salesman Problem using A* Search
class TSPSolverAStar {
    private int[][] distances; // Matrix of distances between cities
    private int numCities; // Number of cities in the problem

    // Constructor to initialize the TSPSolverAStar instance with distances between cities
    public TSPSolverAStar(int[][] distances) {
        this.distances = distances;
        this.numCities = distances.length;
    }

    // Method to perform A* Search to find the best path
    public PathResult aStarSearch() {
        // Priority queue to store paths sorted by their estimated cost (cost + heuristic)
        PriorityQueue<Path> queue = new PriorityQueue<>(Comparator.comparingInt(Path::getEstimatedCost));
        queue.add(new Path(0, new ArrayList<>(Collections.singletonList(0)), 0)); // Add the initial path starting from city 0

        List<Integer> bestPath = null; // Store the best path found
        int minLongestDistance = Integer.MAX_VALUE; // Initialize the minimum longest distance
        int totalCost = 0; // Initialize the total cost of the best path

        // Continue A* Search until the queue is empty
        while (!queue.isEmpty()) {
            Path current = queue.poll(); // Dequeue the path with the lowest estimated cost
            if (current.path.size() == numCities) {
                current.path.add(0); // Add city 0 to complete the path
                int longestDistance = getLongestDistance(current.path); // Calculate the longest distance in the path
                int currentCost = getTotalCost(current.path); // Calculate the total cost of the path
                // Update the best path if the current path has a shorter longest distance
                if (longestDistance < minLongestDistance) {
                    minLongestDistance = longestDistance;
                    bestPath = current.path;
                    totalCost = currentCost;
                }
                continue; // Continue with the next path in the queue
            }

            // Generate new paths by adding unvisited cities to the current path
            for (int i = 0; i < numCities; i++) {
                if (!current.path.contains(i)) { // Check if the city is not already visited
                    List<Integer> newPath = new ArrayList<>(current.path); // Create a new path by copying the current path
                    newPath.add(i); // Add the unvisited city to the new path
                    int newCost = current.cost + distances[current.path.get(current.path.size() - 1)][i]; // Calculate the cost of the new path
                    int heuristic = calculateHeuristic(newPath); // Calculate the heuristic value for the new path
                    queue.add(new Path(newCost, newPath, heuristic)); // Add the new path to the priority queue
                }
            }
        }
        return new PathResult(bestPath, totalCost); // Return the best path found
    }

    // Method to calculate the heuristic value for a path
    private int calculateHeuristic(List<Integer> path) {
        // A simple heuristic can be the minimum edge that hasn't been included yet
        int remainingMinEdge = Integer.MAX_VALUE;
        for (int i = 0; i < numCities; i++) {
            if (!path.contains(i)) {
                for (int j = 0; j < numCities; j++) {
                    if (i != j && !path.contains(j)) {
                        remainingMinEdge = Math.min(remainingMinEdge, distances[i][j]);
                    }
                }
            }
        }
        return remainingMinEdge == Integer.MAX_VALUE ? 0 : remainingMinEdge;
    }

    // Method to calculate the longest distance in a path
    private int getLongestDistance(List<Integer> path) {
        int longest = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int dist = distances[path.get(i)][path.get(i + 1)];
            if (dist > longest) {
                longest = dist;
            }
        }
        return longest;
    }

    // Method to calculate the total cost of a path
    private int getTotalCost(List<Integer> path) {
        int total = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            total += distances[path.get(i)][path.get(i + 1)];
        }
        return total;
    }

    // Class to represent a path with its cost and heuristic value
    class Path {
        int cost; // Total cost of the path
        List<Integer> path; // Sequence of cities in the path
        int heuristic; // Heuristic value of the path

        // Constructor to initialize a path with its cost and heuristic value
        Path(int cost, List<Integer> path, int heuristic) {
            this.cost = cost;
            this.path = path;
            this.heuristic = heuristic;
        }

        // Method to get the estimated cost of the path (cost + heuristic)
        int getEstimatedCost() {
            return cost + heuristic;
        }
    }
}
