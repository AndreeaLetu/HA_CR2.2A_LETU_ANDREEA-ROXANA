
public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();


        int[][] distances = {
                // Define the distances between cities
                {0, 10, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };

        // Create instances of TSPSolver classes
        TSPSolverBFS solverBFS = new TSPSolverBFS(distances);
        TSPSolverUniformCost solverUniformCost = new TSPSolverUniformCost(distances);
        TSPSolverAStar solverAStar = new TSPSolverAStar(distances);

        // Solve the TSP problem using different algorithms
        // and print the best paths
        System.out.println("BFS Best Path: " + solverBFS.bfs());
        System.out.println("Uniform Cost Search Best Path: " + solverUniformCost.uniformCostSearch());
        System.out.println("A* Search Best Path: " + solverAStar.aStarSearch());
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");
    }
}
