import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//import com.sun.jdi.Location;

public class Main {

	private static final String COMMA_DELIMITER = ",";

	public static List<List<String>> readCSV(String fp) {

		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fp))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER);
				records.add(Arrays.asList(values));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return records;
	}

	public static void populateCampusMap(GraphA3<String> map, Map<String, Location> locationInfo, String nodeFP,
			String edgesFP) {

		List<List<String>> nodeRecords = readCSV(nodeFP);
		for (List<String> nodeRecord : nodeRecords) {
			String code = nodeRecord.get(0);
			String name = nodeRecord.get(1);
			String info = nodeRecord.get(2);
			map.addVertex(code);
			locationInfo.put(code, new Location(name, info));
		}

		List<List<String>> edgeRecords = readCSV(edgesFP);
		for (List<String> edgeRecord : edgeRecords) {
			String locationName1 = edgeRecord.get(0);
			String locationName2 = edgeRecord.get(1);
			Integer distance = Integer.valueOf(edgeRecord.get(2));
			map.addEdge(locationName1, locationName2, distance);
		}

	}

	public static void help(String locationCode) {
		System.out.println("You are currently located at: " + locationCode);
	}

	public static String move(GraphA3<String> map, String currentLocationCode) {
		System.out.println("Possible locations to visit: " + map.getAdjVertecies(currentLocationCode));
		
		Scanner sc = new Scanner(System.in);
		System.out.println("To what location would you like to visit?");
		String userNavChoice = sc.nextLine();
		if (map.getVertecies().contains(userNavChoice)) {
			currentLocationCode = userNavChoice;
			System.out.println("You are currently located at: " + currentLocationCode);
		} else {
			System.out.println("Invalid choice.");
		}
		return currentLocationCode;
	}

	public static void look(GraphA3<String> map, String currentLocationCode) {
		System.out.println(map.getAdjVertecies(currentLocationCode));
	}

	public static void info(Map<String, Location> locationInfo, String currentLocationCode) {
		System.out.println(locationInfo.get(currentLocationCode));
	}

	public static void navigate(GraphA3<String> map, String currentLocationCode) {
		System.out.println("Possible locations to visit: " + map.getVertecies());
		System.out.println("To what location would you like the path to lead?");

		Scanner sc = new Scanner(System.in);
		String userNavChoice = sc.nextLine();
		if (map.getVertecies().contains(userNavChoice)) {
			map.dijkstraShortestPath(currentLocationCode, userNavChoice);
		} else {
			System.out.println("Invalid choice.");
		}
	}

	public static void main(String[] args) {

		GraphA3<String> map = new GraphA3<>();
		Map<String, Location> locationInfo = new HashMap<>();
		populateCampusMap(map, locationInfo, "vertexlist.csv", "edgelist.csv");

		Scanner sc = new Scanner(System.in);
		System.out.printf("Locations: %s\n", map.getVertecies());
		System.out.print("Choose a starting location: ");
		String currentLocationCode = sc.nextLine();
		if (!map.getVertecies().contains(currentLocationCode)) {
			System.out.println("Invalid starting location. End.");
			return;
		}

		while (true) {
			System.out.println("Please enter one of the following commands: [l]ook, [m]ove, [i]nfo, [n]avigate, or [h]elp");
			String userChoice = sc.nextLine();
			if (userChoice.equals("h")) {
				help(currentLocationCode);
			} else if (userChoice.equals("m")) {
				currentLocationCode = move(map, currentLocationCode);
			} else if (userChoice.equals("l")) {
				look(map, currentLocationCode);
			} else if (userChoice.equals("i")) {
				info(locationInfo, currentLocationCode);
			} else if (userChoice.equals("n")) {
				navigate(map, currentLocationCode);
			} 
		}
		// break; // REMOVE WHEN IMPLEMENTING
	}

}
