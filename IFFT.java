//package edu.princeton.cs.algorithms;
//
//import edu.princeton.cs.introcs.StdOut;
package audio_analysis;


import javax.swing.Timer;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.color.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
//import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.ConcurrentLinkedQueue;




/*************************************************************************
 *  Compilation:  javac FFT.java
 *  Execution:    java FFT N
 *  Dependencies: Complex.java
 *
 *  Compute the FFT and inverse FFT of a length N complex sequence.
 *  Bare bones implementation that runs in O(N log N) time. Our goal
 *  is to optimize the clarity of the code, rather than performance.
 *
 *  Limitations
 *  -----------
 *   -  assumes N is a power of 2
 *
 *   -  not the most memory efficient algorithm (because it uses
 *      an object type for representing complex numbers and because
 *      it re-allocates memory for the subarray, instead of doing
 *      in-place or reusing a single temporary array)
 *  
 *************************************************************************/

public class IFFT extends JPanel /* implements ActionListener*/{

	
	
	
    // compute the FFT of x[], assuming its length is a power of 2
    public static Complex[] fft(Complex[] x) {
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
        int N = x.length;

        // base case
        if (N == 1) return new Complex[] { x[0] };

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) { throw new IllegalArgumentException("N is not a power of 2"); }

        // fft of even terms
        Complex[] even = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < N/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N/2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]       = q[k].plus(wk.times(r[k]));
            y[k + N/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }


    // compute the inverse FFT of x[], assuming its length is a power of 2
    public static Complex[] ifft(Complex[] x) {
        int N = x.length;
        Complex[] y = new Complex[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = x[i].conjugate();
        }

        // compute forward FFT
        y = fft(y);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            y[i] = y[i].times(new Complex((y[i].re()*1.0/N),(y[i].im()*1.0/N)));
        }

        return y;

    }

    // compute the circular convolution of x and y
    public static Complex[] cconvolve(Complex[] x, Complex[] y) {

        // should probably pad x and y with 0s so that they have same length
        // and are powers of 2
        if (x.length != y.length) { throw new IllegalArgumentException("Dimensions don't agree"); }

        int N = x.length;

        // compute FFT of each sequence
        Complex[] a = fft(x);
        Complex[] b = fft(y);

        // point-wise multiply
        Complex[] c = new Complex[N];
        for (int i = 0; i < N; i++) {
            c[i] = a[i].times(b[i]);
        }

        // compute inverse FFT
        return ifft(c);
    }


    // compute the linear convolution of x and y
    public static Complex[] convolve(Complex[] x, Complex[] y) {
        Complex ZERO = new Complex(0, 0);

        Complex[] a = new Complex[2*x.length];
        for (int i = 0;        i <   x.length; i++) a[i] = x[i];
        for (int i = x.length; i < 2*x.length; i++) a[i] = ZERO;

        Complex[] b = new Complex[2*y.length];
        for (int i = 0;        i <   y.length; i++) b[i] = y[i];
        for (int i = y.length; i < 2*y.length; i++) b[i] = ZERO;

        return cconvolve(a, b);
    }

    // display an array of Complex numbers to standard output
    public static void show(Complex[] x, String title) {
        System.out.println(title);
        System.out.println("-------------------");
        for (int i = 0; i < x.length; i++) {
            System.out.println(x[i].re());
        }
        System.out.println();
    }
    
    
    
    
//    int counter=0;
//    static Timer timer = new Timer(5000, new ActionListener() {
//    	@Override 
//    	public void actionPerformed(ActionEvent e) {
//    		}
//    	}
//    	);
//    
//   private static int cnt=0;
//    public void actionPerformed(ActionEvent event) {
//    	 
//        cnt += 1;
//     
//        System.out.println("Counter = "+cnt);
//     
//    }
    
    
    
    
    
    
    
    
   
    
    public static void main(String[] args) throws InterruptedException, LineUnavailableException, IOException{
//      int N = Integer.parseInt(args[0]);
    	
    	
    	
    	
    	
        
        BufferedImage image = ImageIO.read(new File("C:\\\\Users\\\\ssd500\\\\Pictures\\\\Saved Pictures\\\\ball.jpg"));
        JLabel label = new JLabel(new ImageIcon(image));
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(label);
        f.pack();
        f.setLocation(200,200);
        f.setVisible(true);

    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
//    	
//    	new JFrame().setVisible(false);
//    	 
//    	ActionListener actListner = new ActionListener() {
//    		 
//    		@Override
//    		 
//    		public void actionPerformed(ActionEvent event) {
//    		 
//    		    cnt += 1;
//    		 
//    		    System.out.println("Counter = "+cnt);
//    		 
//    		}
//    		 
//    		  };
//    		  
//    		  Timer timer = new Timer(750, actListner);
//    		  
//    		  timer.start();
    	
    	
    	
    	JFrame myFrame = new JFrame("Frame Title");
    	JPanel mainPanel = new JPanel();
    	myFrame.add(mainPanel);
    	//myFrame.add(timer);
    	mainPanel.setBackground(new Color(255, 255, 255));
        myFrame.setPreferredSize(new Dimension(400,300));
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        
        mainPanel.setLayout(new BorderLayout());
        myFrame.getContentPane().add(mainPanel);
        
       // mainPanel.add(new JButton("Button Text"), BorderLayout.CENTER);

//        ActionListener actionListener = new ActionListener() {
//            public void actionPerformed(ActionEvent actionEvent) {
//              System.out.println("Hello World Timer");
//            }
//          };
          
          
          

          

        myFrame.pack();
        myFrame.setLocationByPlatform(true);
        myFrame.setVisible(true);

    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
        int N = (int)Math.pow(2,18);
        Complex[] x = new Complex[N];
        for(int i=0;i<x.length;i++) {
        	x[i]=new Complex(100,0);
        }
        
        
        
        
        
        
        BufferedImage img = null;
		File F = null;
		
		// read image
		try {
			F = new File("C:\\Users\\ssd500\\Pictures\\Saved Pictures\\ball.jpg");
			img = ImageIO.read(F);
			
			//img0=ImageIO.read(f0);
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
		
		
		int temp[] = new int[(int)Math.pow(2, 18)];
		long t1 = System.nanoTime(), t2;
			for (int i = 0; i < img.getWidth(); i++) {
				for (int j = 0; j < img.getHeight(); j++) {
					//timer.start();
					int p = img.getRGB(i, j);
					temp[340 * i + j]=p;
					//t2=System.nanoTime();
//					for(int g=0;g<100000;g++)
//						System.out.println("         ");
					//if((t2-t1)>37760) {
					//if(cnt%25==0) {
					// for(int c=0;c<img0.getWidth();c++) {
					// for(int d=0;d<img0.getHeight();d++) {
					// img0.setRGB(c, d, p);
					// }
					// }

					// get alpha
					//System.out.println(cnt);
					int a = (p >> 24) & 0xff;

					// get red
					int r = (p >> 16) & 0xff;

					// get green
					int g = (p >> 8) & 0xff;

					// get blue
					int b = p & 0xff;
					//mainPanel.setBackground(new Color(r, g, b));

					// System.out.println(p);

					/* chnage to vr */ x[340 * i + j] = new Complex((r + g + b) / 3, 0);
					
				}
			}
			//}

			// original data

			// x=ir.getImageAsComplex();

			// show(x, "x");

			// FFT of original data
			// Complex[] y = fft(x);
			// show(y, "y = fft(x)");

			// take inverse FFT
			Complex[] z = ifft(x);
			// show(z, "z = ifft(x)");

			// circular convolution of x with itself
			// Complex[] c = cconvolve(x, x);
			// show(c, "c = cconvolve(x, x)");

			// linear convolution of x with itself
			// Complex[] d = convolve(x, x);
			// show(d, "d = convolve(x, x)");

			final int SAMPLING_RATE = 44100; // Audio sampling rate
			final int SAMPLE_SIZE = 2; // Audio sample size in bytes
			final int range = 6000;

			for (int in = 0; in < (int) Math.pow(2, 18); in+=30) {
				
				int a = (temp[in] >> 24) & 0xff;

				// get red
				int r = (temp[in] >> 16) & 0xff;

				// get green
				int g = (temp[in] >> 8) & 0xff;

				// get blue
				int b = temp[in] & 0xff;
				mainPanel.setBackground(new Color(r, g, b));
				
				for(int h=0;h<5000;h++)
					System.out.println("         ");

				
				SourceDataLine line;
				if (z[in].re() < 100)
					z[in] = new Complex(100, 0);
				else if (z[in].re() > range)
					z[in] = new Complex(range, 0);
				double fFreq = 250 + z[in].re() / 10; // Frequency of the sine wave

				// Position through the sine wave as a percentage (i.e. 0 to 1 is 0 to 2*PI)
				double fCyclePosition = 0;

				// Open up audio output, using 44100hz sampling rate, 16 bit samples, mono,
				// and big endian byte ordering
				AudioFormat format = new AudioFormat(SAMPLING_RATE, 16, 1, true, true);
				DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

				if (!AudioSystem.isLineSupported(info)) {
					System.out.println("Line matching " + info + " is not supported.");
					throw new LineUnavailableException();
				}

				line = (SourceDataLine) AudioSystem.getLine(info);
				line.open(format);
				line.start();

				// Make our buffer size match audio system's buffer
				ByteBuffer cBuf = ByteBuffer.allocate(line.getBufferSize());

				int ctSamplesTotal = SAMPLING_RATE / 32; // Output for roughly 5 seconds

				// On each pass main loop fills the available free space in the audio buffer
				// Main loop creates audio samples for sine wave, runs until we tell the thread
				// to exit
				// Each sample is spaced 1/SAMPLING_RATE apart in time
				while (ctSamplesTotal > 0) {
					double fCycleInc = fFreq / SAMPLING_RATE; // Fraction of cycle between samples

					cBuf.clear(); // Discard the samples from the last pass

					// Figure out how many samples we can add
					int ctSamplesThisPass = line.available() / SAMPLE_SIZE;
					for (int p = 0; p < ctSamplesThisPass; p++) {
						cBuf.putShort((short) (Short.MAX_VALUE * Math.sin(2 * Math.PI * fCyclePosition)));

						fCyclePosition += fCycleInc;
						if (fCyclePosition > 1)
							fCyclePosition -= 1;
					}

					// Write sine samples to the line buffer. If the audio buffer is full, this will
					// block until there is room (we never write more samples than buffer will hold)
					line.write(cBuf.array(), 0, cBuf.position());
					ctSamplesTotal -= ctSamplesThisPass; // Update total number of samples written

					// Wait until the buffer is at least half empty before we add more
					while (line.getBufferSize() / 2 < line.available())
						Thread.sleep(1);
				}

				// Done playing the whole waveform, now wait until the queued samples finish
				// playing, then clean up and exit
				line.drain();
				line.close();
			}
			
		}
    }
        
        
        
        
        
        
        
        
        
        
        
        
    
    
//    try {
//        // Open an audio input stream.           
////         File soundFile = new File("C:/Users/niklas/workspace/assets/Sound/sound.wav"); //you could also get the sound file with an URL
////         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
//        // Get a sound clip resource.
//        Clip clip = AudioSystem.getClip();
//        byte b[] = new byte[(int)Math.pow(2, 18)];
//        for(int i=0;i<(int)Math.pow(2, 18);i++) {
//				for (int k = 0; k < (int) Math.pow(2, 18); k++) {
//					b[i] = (byte)(100+k);
//				}
//			}
//        // Open audio clip and load samples from the audio input stream.
//        int j=600;
//        clip.open(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 8000.0F, 16, 2, 4, 8000.0F, true), b, 0,
//					b.length);
//		 while (j > 0) {
//				
//				clip.start();
//				//b= new byte[100000];
//		         for(int i=0;i<(int)Math.pow(2, 18);i++) {
//		        //
//		        	 b[i]=(byte)x[i].re();
//		         }
//		         j--;
//		         
//		         //clip.close();
//				
//		 }
        

//     }
//      catch (LineUnavailableException e) {
//         e.printStackTrace();
//      }
//   }


    
























class Graph_IFFT extends Application {

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

	
	series1.getData().remove(0, series1.getData().size());
	
	//data = AudioCapture.capture();
	double[] arr = FFT.fftReal(data);
	
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





