import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ShortestPathApp {

	static List<String> locations = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the name of the map file: ");
		String filename = scanner.nextLine();

		GraphADT<String, Double> graph = loadGraphData(filename);

		while (true) {
			System.out.println("\nMenu:");
			System.out.println("1. Get the list of all locations in the map");
			System.out.println("2. Find the shortest path between two locations");
			System.out.println("3. Quit the application");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // consume newline

			switch (choice) {
				case 1:
					List<String> locations = getListOfAllLocations(graph);
					System.out.println("Locations:");
					for (String location : locations) {
						System.out.println(location);
					}
					break;
				case 2:
					System.out.print("Enter the starting location: ");
					String startLocation = scanner.nextLine();
					System.out.print("Enter the ending location: ");
					String endLocation = scanner.nextLine();
					List<String> path = findShortestPath(graph, startLocation, endLocation);
					System.out.println("Shortest path:");
					for (String loc : path) {
						System.out.println(loc);
					}
					break;
				case 3:
					System.out.println("Quitting the application.");
					return;
				default:
					System.out.println("Invalid choice. Please enter 1, 2, or 3.");
			}
		}
	}

	/**
	 * Read in a map file and save it as a Dijkstra graph
	 *
	 * @param filename map file name
	 * @return a GraphADT that represents the map
	 */
	public static GraphADT<String,Double> loadGraphData(String filename) throws IOException {
		GraphADT<String,Double> graph = new DijkstraGraph<String, Double>();

		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("\"") && line.contains("->")) {
					String[] parts = line.split("->");
					String source = parts[0].trim().replace("\"", "");
					String[] destParts = parts[1].trim().split("\\[seconds=");
					String destination = destParts[0].trim().replace("\"", "");
					double seconds = Double.parseDouble(destParts[1].replace("];", ""));

					if (!graph.containsNode(source)) {
						graph.insertNode(source);
						locations.add(source);
					}


					if (!graph.containsNode(destination)) {
						graph.insertNode(destination);
						locations.add(destination);
					}

					if (graph.containsEdge(source,destination)) {
						continue;
					}
					graph.insertEdge(source, destination, seconds);
					graph.insertEdge(destination, source, seconds);
				}
			}
		}

		return graph;
	}

	/**
	 * Return the list of all locations in a map
	 *
	 * @param map GraphADT map
	 * @return the list of all possible locations on the map
	 */
	public static List<String> getListOfAllLocations(GraphADT<String,Double> map) {
		return locations;
	}

	/**
	 * Return the list of all locations in a shortest path of a map for given start and end locations
	 *
	 * @param map GraphADT map
	 * @param startLocation starting location
	 * @param endLocation ending location
	 * @return the list of locations on the shortest path from start to end
	 */
	public static List<String> findShortestPath(GraphADT<String,Double> map, String startLocation, String endLocation) {
		return ((DijkstraGraph<String, Double>)map).shortestPathData(startLocation,
				endLocation);
	}
}

