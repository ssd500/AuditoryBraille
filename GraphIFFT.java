package audio_analysis;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.imageio.ImageIO;



public class GraphIFFT extends Application {

    private static final int MAX_DATA_POINTS = 10000;
    
    private XYChart.Series<Number, Number> series1 = new XYChart.Series<>();

    private ConcurrentLinkedQueue<Number> dataQ1 = new ConcurrentLinkedQueue<>();

    private NumberAxis xAxis;

    private byte[] data;
    
    
    

    private void init(Stage primaryStage) {

	xAxis = new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10);
	xAxis.setTickLabelsVisible(false);
	xAxis.setTickMarkVisible(false);
	xAxis.setMinorTickVisible(false);

	NumberAxis yAxis = new NumberAxis();


	final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis) {
	    // Override to remove symbols on each data point
	    @Override
	    protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
	    }
	};

	lineChart.setAnimated(false);
	lineChart.setTitle("Line chart");
	lineChart.setHorizontalGridLinesVisible(true);

	// Set Name for Series
	series1.setName("Series 1");

	// Add Chart Series
	lineChart.getData().add(series1);

	primaryStage.setScene(new Scene(lineChart));
    }

    @Override
    public void start(Stage stage) {
	stage.setTitle("Animated Line Chart Sample");
	init(stage);
	stage.show();

//	AudioCapture.setFormat(44100, 16);
//	AudioCapture.setUp();
	

	prepareTimeline();
    }

    
    // -- Timeline gets called in the JavaFX Main thread
    private void prepareTimeline() {
	// Every frame to take any data from queue and add to chart
	new AnimationTimer() {
	    @Override
	    public void handle(long now) {
		addDataToSeries();
	    }
	}.start();
    }

    
    
    private void addDataToSeries() {
    	
    	int N = (int)Math.pow(2,18);
        Complex[] x = new Complex[N];
        for(int i=0;i<x.length;i++) {
        	x[i]=new Complex(100,0);
        }
        
        
        BufferedImage img = null;
		File f = null;

		// read image
		try {
			f = new File("C:\\Users\\ssd500\\Pictures\\Saved Pictures\\pic#.jpg");
			img = ImageIO.read(f);
		} catch (IOException e) {
			System.out.println(e);
		}

		// get image width and height
		int width = img.getWidth();
		int height = img.getHeight();

		/**
		 * Since, Sample.jpg is a single pixel image so, we will not be using the width
		 * and height variable in this project.
		 */

		//Complex arr[] = new Complex[64];
		
		// get pixel value
		
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				int p = img.getRGB(i, j);

				// get alpha
				int a = (p >> 24) & 0xff;

				// get red
				int r = (p >> 16) & 0xff;

				// get green
				int g = (p >> 8) & 0xff;

				// get blue
				int b = p & 0xff;

				//System.out.println(p);


/*chnage to vr	*/			x[340*i + j] = new Complex((r + g + b)/3, 0);
			}
		}
        
        
        

	
	series1.getData().remove(0, series1.getData().size());
	
	//data = AudioCapture.capture();
	double[] arr= new double[(int)Math.pow(2,8)];
	Complex[] ar = IFFT.ifft(x);
	for(int n=0;n<(int)Math.pow(2,8);n++) {
		arr[n]=ar[n].re();
	}
	
	for(double d: arr)
	    dataQ1.add(d);
	
	

	for (int i = 0; i < dataQ1.size(); i++) { // -- add 20 numbers to the plot+
	    
	    if (dataQ1.isEmpty()) break;
	    
	    series1.getData().add(new XYChart.Data<>(i*100, dataQ1.peek()));
	    dataQ1.remove();

	}
	

	dataQ1.clear();

	// update
//	xAxis.setLowerBound(0);
//	xAxis.setUpperBound(MAX_DATA_POINTS);
    }

    public static void main(String[] args) {
	launch(args);
    }
}