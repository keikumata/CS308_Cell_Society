package animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AnimatedGraph{
	private static final int MAX_PERCENTAGE = 100;
	private static final int MIN_PERCENTAGE = 0;
	private static final int TICK_UNIT = 10;
	HashMap<Integer,String> paramLabels;
	@SuppressWarnings("rawtypes")
	HashMap<Integer, XYChart.Series> seriesMap;

	public AnimatedGraph(HashMap<Integer,String> paramLabels){
		this.paramLabels = paramLabels;
	}
	
	
	@SuppressWarnings("rawtypes")
	private HashMap<Integer, XYChart.Series> createAddSeries(HashMap<Integer,String> paramLabels) {
		seriesMap = new HashMap<>();
		for (int i:paramLabels.keySet()){
			@SuppressWarnings("rawtypes")
			XYChart.Series temp = new XYChart.Series<Number, Number>();
			temp.setName(paramLabels.get(i));
			seriesMap.put(i, temp);
		}
		return seriesMap;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Pane init() {
		NumberAxis xAxis = new NumberAxis();
		xAxis.setForceZeroInRange(false);
		xAxis.setAutoRanging(true);


		xAxis.setTickLabelsVisible(false);
		xAxis.setTickMarkVisible(false);
		xAxis.setMinorTickVisible(false);

		NumberAxis yAxis = new NumberAxis("Percentage of cells", MIN_PERCENTAGE, MAX_PERCENTAGE, TICK_UNIT);

		//-- Chart
		final LineChart<Number, Number> sc = new LineChart<Number, Number>(xAxis, yAxis) {
			// Override to remove symbols on each data point
			@Override protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {}
		};
		sc.setId("liveLineeChart");
		sc.setTitle("Animated Cell Proportion (in %)");
		sc.setLegendVisible(true);

		for (int i: createAddSeries(paramLabels).keySet()) {
			sc.getData().add(seriesMap.get(i));
		}
		
		sc.setPrefSize(400, 200);
		sc.setMaxSize(400, 200);
		sc.setTranslateX(400);
		sc.setTranslateY(150);
		
		
		StackPane sp = new StackPane();
		sp.getChildren().addAll(sc,createLegendBox(sc));
//		sp.getChildren().add(sc);
		return sp;
	}
	private Node createLegendBox(LineChart sc) {
		Set<Node> items = sc.lookupAll("Label.chart-legend-item");
	    int i = 0;
	    // these colors came from caspian.css .default-color0..4.chart-pie
	    Color[] colors = { Color.web("#f9d900"), Color.web("#a9e200"), Color.web("#22bad9"), Color.web("#0181e2"), Color.web("#2f357f") };
	    GridPane pane = new GridPane();
	    for (Node item : items) {
	      Label label = (Label) item;
	      final Rectangle rectangle = new Rectangle(10, 10, colors[i]);
//	      final Glow niceEffect = new Glow();
//	      niceEffect.setInput(new Reflection());
//	      rectangle.setEffect(niceEffect);
	      label.setGraphic(rectangle);
	      i++;
	      pane.add(item, 0, i);
	    }
	    
		pane.setTranslateX(400);
		return pane;
		
	}
	// add the data into series, series into sth else, add that to root
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addData(int count, HashMap<Integer,Integer> proportions) {
		for (int i: proportions.keySet()) {
			seriesMap.get(i).getData().add(new XYChart.Data(count, proportions.get(i)));
		}
	}
	
}
