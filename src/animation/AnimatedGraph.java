package animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

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
	public LineChart init() {
		NumberAxis xAxis = new NumberAxis();
		xAxis.setForceZeroInRange(false);
		xAxis.setAutoRanging(true);


		xAxis.setTickLabelsVisible(false);
		xAxis.setTickMarkVisible(false);
		xAxis.setMinorTickVisible(false);

		NumberAxis yAxis = new NumberAxis("Percentage of cells", MIN_PERCENTAGE, MAX_PERCENTAGE, TICK_UNIT);
//		yAxis.setAutoRanging(true);

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
		return sc;
	}
	// add the data into series, series into sth else, add that to root
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addData(int count, HashMap<Integer,Integer> proportions) {
		for (int i: proportions.keySet()) {
			seriesMap.get(i).getData().add(new XYChart.Data(count, proportions.get(i)));
		}
	}
	
}
