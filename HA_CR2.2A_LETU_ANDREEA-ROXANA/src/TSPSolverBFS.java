import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Class for solving the Traveling Salesman Problem using Breadth-First Search
class TSPSolverBFS {
    private int[][] distances; // Matrix of distances between cities
    private int numCities; // Number of cities in the problem

    // Constructor to initialize the TSPSolverBFS instance with distances between cities
    public TSPSolverBFS(int[][] distances) {
        this.distances = distances;
        this.numCities = distances.length;
    }

    // Method to perform Breadth-First Search to find the best path
    public PathResult bfs() {
        Queue<List<Integer>> queue = new LinkedList<>(); // Queue for BFS traversal
        List<Integer> initialPath = new ArrayList<>(); // Initial path starting from city 0
        initialPath.add(0); // Add city 0 to the initial path
        queue.add(initialPath); // Add the initial path to the queue

        List<Integer> bestPath = null; // Store the best path found
        int minLongestDistance = Integer.MAX_VALUE; // Initialize the minimum longest distance
        int totalCost = 0; // Initialize the total cost of the best path

        // Continue BFS until the queue is empty
        while (!queue.isEmpty()) {
            List<Integer> path = queue.poll(); // Dequeue the current path
            if (path.size() == numCities) {
                path.add(0); // Add city 0 to complete the path
                int longestDistance = getLongestDistance(path); // Calculate the longest distance in the path
                int currentCost = getTotalCost(path); // Calculate the total cost of the path
                // Update the best path if the current path has a shorter longest distance
                if (longestDistance < minLongestDistance) {
                    minLongestDistance = longestDistance;
                    bestPath = path;
                    totalCost = currentCost;
                }
                continue; // Continue with the next path in the queue
            }

            // Generate new paths by adding unvisited cities to the current path
            for (int i = 0; i < numCities; i++) {
                if (!path.contains(i)) { // Check if the city is not already visited
                    List<Integer> newPath = new ArrayList<>(path); // Create a new path by copying the current path
                    newPath.add(i); // Add the unvisited city to the new path
                    queue.add(newPath); // Add the new path to the queue for further exploration
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

    // Method to calculate the total cost of a path
    private int getTotalCost(List<Integer> path) {
        int total = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            total += distances[path.get(i)][path.get(i + 1)];
        }
        return total;
    }
}
