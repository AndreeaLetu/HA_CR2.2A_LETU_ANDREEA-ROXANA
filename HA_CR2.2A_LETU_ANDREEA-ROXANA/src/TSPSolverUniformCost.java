import java.util.*;

// Class for solving the Traveling Salesman Problem using Uniform Cost Search
class TSPSolverUniformCost {
    private int[][] distances; // Matrix of distances between cities
    private int numCities; // Number of cities in the problem

    // Constructor to initialize the TSPSolverUniformCost instance with distances between cities
    public TSPSolverUniformCost(int[][] distances) {
        this.distances = distances;
        this.numCities = distances.length;
    }

    // Method to perform Uniform Cost Search to find the best path
    public PathResult uniformCostSearch() {
        // Priority queue to store paths sorted by their cost
        PriorityQueue<Path> queue = new PriorityQueue<>(Comparator.comparingInt(Path::getCost));
        queue.add(new Path(0, new ArrayList<>(Collections.singletonList(0)))); // Add the initial path starting from city 0

        List<Integer> bestPath = null; // Store the best path found
        int minLongestDistance = Integer.MAX_VALUE; // Initialize the minimum longest distance
        int totalCost = 0; // Initialize the total cost of the best path

        // Continue Uniform Cost Search until the queue is empty
        while (!queue.isEmpty()) {
            Path current = queue.poll(); // Dequeue the path with the lowest cost
            if (current.path.size() == numCities) {
                current.path.add(0); // Add city 0 to complete the path
                int longestDistance = getLongestDistance(current.path); // Calculate the longest distance in the path
                // Update the best path if the current path has a shorter longest distance
                if (longestDistance < minLongestDistance) {
                    minLongestDistance = longestDistance;
                    bestPath = current.path;
                    totalCost = current.cost;
                }
                continue; // Continue with the next path in the queue
            }

            // Generate new paths by adding unvisited cities to the current path
            for (int i = 0; i < numCities; i++) {
                if (!current.path.contains(i)) { // Check if the city is not already visited
                    List<Integer> newPath = new ArrayList<>(current.path); // Create a new path by copying the current path
                    newPath.add(i); // Add the unvisited city to the new path
                    int newCost = current.cost + distances[current.path.get(current.path.size() - 1)][i]; // Calculate the cost of the new path
                    queue.add(new Path(newCost, newPath)); // Add the new path to the priority queue
                }
            }
        }
        return new PathResult(bestPath, totalCost); // Return the best path found
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

    // Class to represent a path with its cost
    class Path {
        int cost; // Total cost of the path
        List<Integer> path; // Sequence of cities in the path

        // Constructor to initialize a path with its cost
        Path(int cost, List<Integer> path) {
            this.cost = cost;
            this.path = path;
        }

        // Method to get the cost of the path
        int getCost() {
            return cost;
        }
    }
}
