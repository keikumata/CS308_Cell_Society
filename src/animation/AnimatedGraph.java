package animation;

import java.util.HashMap;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class AnimatedGraph{
	private static final int MAX_PERCENTAGE = 100;
	private static final int MIN_PERCENTAGE = 0;
	private static final int TICK_UNIT = 10;
	private static final int GRID_SIZE = 600;
	private static final int GRAPH_Y_LOCATION = 150;
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
			XYChart.Series temp = new XYChart.Series<Number, Number>();
			temp.setName(paramLabels.get(i));
			seriesMap.put(i, temp);
		}
		return seriesMap;
	}
	@SuppressWarnings({ "unchecked" })
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
		
		sc.setTranslateX(GRID_SIZE);
		sc.setTranslateY(GRAPH_Y_LOCATION);
		
		
		StackPane sp = new StackPane();
		sp.getChildren().add(sc);
		return sp;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addData(int count, HashMap<Integer,Integer> proportions) {
		for (int i: proportions.keySet()) {
			seriesMap.get(i).getData().add(new XYChart.Data(count, proportions.get(i)));
		}
	}
	
}
