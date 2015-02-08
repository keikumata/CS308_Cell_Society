package animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class AnimatedGraph {
	private static final int MAX_DATA_POINTS = 50;
	private NumberAxis xAxis;
	private XYChart.Series series1;
	private XYChart.Series series2;
	private XYChart.Series series3;
	HashMap<Integer,String> paramLabels;
	HashMap<Integer, XYChart.Series> seriesMap;
	public AnimatedGraph(HashMap<Integer,String> paramLabels){
		this.paramLabels = paramLabels;
	}
	private HashMap<Integer, XYChart.Series> createAddSeries(HashMap<Integer,String> paramLabels) {
		seriesMap = new HashMap<>();
		for (int i:paramLabels.keySet()){
			XYChart.Series temp = new XYChart.Series<Number, Number>();
			temp.setName(paramLabels.get(i));
			seriesMap.put(i, new XYChart.Series<Number, Number>());
		}
		return seriesMap;
	}
	public LineChart init() {
		xAxis = new NumberAxis(0,MAX_DATA_POINTS,MAX_DATA_POINTS/10);
		xAxis.setForceZeroInRange(false);
		xAxis.setAutoRanging(true);


		xAxis.setTickLabelsVisible(false);
		xAxis.setTickMarkVisible(false);
		xAxis.setMinorTickVisible(false);

		NumberAxis yAxis = new NumberAxis();
		yAxis.setAutoRanging(true);

		//-- Chart
		final LineChart<Number, Number> sc = new LineChart<Number, Number>(xAxis, yAxis) {
			// Override to remove symbols on each data point
			@Override protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {}
		};
		sc.setAnimated(false);
		sc.setId("liveLineeChart");
		sc.setTitle("Animated Cell Proportion (in %)");


		for (int i: createAddSeries(paramLabels).keySet()) {
			sc.getData().add(seriesMap.get(i));
		}
		sc.setPrefSize(400, 200);
		sc.setMaxSize(400, 200);
		sc.setTranslateX(400);
		sc.setTranslateY(200);
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
