import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ShortestPathAppGUI extends Application {
  //TODO: declare a variable to save the Dijkstra graph

  public void start(Stage stage) {
    Pane root = new Pane();

    //TODO: Read in the provided map file and save it as a Dijkstra graph

    createAllControls(root);

    Scene scene = new Scene(root, 800, 600);
    stage.setScene(scene);
    stage.setTitle("Shortest Path App");
    stage.show();
  }

  public void createAllControls(Pane parent) {
    createShortestPathControls(parent);
    createPathListDisplay(parent);
    createAboutAndQuitControls(parent);
  }

  public void createShortestPathControls(Pane parent) {
    Label src = new Label("Path Start Selector: ");
    src.setLayoutX(32);
    src.setLayoutY(16);
    parent.getChildren().add(src);
    //TODO: Ask the user select the starting location via the graphic interface
    //		Add control components as necessary

    Label dst = new Label("Path End Selector: ");
    dst.setLayoutX(32);
    dst.setLayoutY(48);
    parent.getChildren().add(dst);
    //TODO: Ask the user select the ending location via the graphic interface
    //		Add control components as necessary

    Button find = new Button("Find Shortest Path");
    find.setLayoutX(32);
    find.setLayoutY(80);
    parent.getChildren().add(find);

    //TODO: Calculate the shortest path based on the starting and ending locations selected

    //TODO: Display the shortest path list, and the walking time along the path. Add control components as necessary

	
  }


  public void createAboutAndQuitControls(Pane parent) {
    Button about = new Button("About");
    about.setLayoutX(32);
    about.setLayoutY(560);
    parent.getChildren().add(about);
    //TODO: display the following information when this button is clicked: "UW Madison Map navigation App. Created Summer 2024."

    Button quit = new Button("Quit");
    quit.setLayoutX(96);
    quit.setLayoutY(560);
    parent.getChildren().add(quit);
    //TODO: quit the app after this button is clicked. 
  }



  //TODO

  /*
   * Note: The following codes should be copied from your project 2-1 codes in the ShortestPathApp.java file.
   * However, you need to make some changes to the findShortestPath method to return the shortest time as well
  */


    /**
     * Read in a map file and save it as a Dijkstra graph 
     *
     * @param map file name
     * @return a Dijkstra graph that reprsents the map
     */
	public GraphADT loadGraphData(String filename) throws IOException {

  	}

    /**
     * Return the list of all locations in a map 
     *
     * @param map 
     * @return the list of all possible locations on the map
     */
	public List<String> getListOfAllLocations(GraphADT map) {

  	}

    /**
     * Return the list of all locations in a shortest path of a map for given start and end locations,
     *  but the first element should be the shortest travel time, and the second element is the starting location
     *
     * @param map 
     * @param starting location
     * @param ending location
     * @return the list of locations on the shortest path from start to end 
     */
	public List<String> findShortestPath(GraphADT map, String startLocation, String endLocation) {

  	}
}
