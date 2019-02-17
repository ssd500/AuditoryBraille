package audio_analysis;

import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UI extends JFrame implements ActionListener {
	  private static JButton button1 = new JButton("Click me!");
	  private static JButton button2 = new JButton("Click me too!");

	  public UI() {
	    button1.addActionListener(this);
	    button2.addActionListener(this);
	    
	  }

	  public void actionPerformed(ActionEvent evt) {
	    Object src = evt.getSource();
	    if (src == button1) {
	    	AudioCapture.setFormat(44100, 8);
			AudioCapture.setUp();

			byte[] input;
			int k = 0;
			while (true) {
				input = AudioCapture.capture();

				// for(byte b: input) System.out.println(b);

				// System.out.println("---------");
				Complex[] cinput = new Complex[input.length];
				for (int i = 0; i < input.length; i++)
					cinput[i] = new Complex(input[i], 0.0);
				FFT f=new FFT();

				Complex[] test = f.fft(cinput);
				double[] actual = new double[test.length];
				for (int i = 0; i < actual.length/2; i++) {
					actual[i] = Math.sqrt(test[i].re() * test[i].re() + test[i].im() * test[i].im());
				}

				System.out.println("Result:");
				/*
				 * for (double c : actual) { System.out.println(c); }
				 */
				int pos = f.getMaxF(actual);
				System.out.println((double)pos*44100/actual.length);
				k++;

			}
	    } 
	    Object src0 = evt.getSource();
	    if (src0 == button2) {
	      //show output
	    }
	  }
	  
	  public static void main(String[] args) {
		  
		    
		  
			JFrame frame= new JFrame("FFT");	
			frame.add(new JPanel());
			frame.add(button1);
			
			frame.setSize(600, 400);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);		
		}
	}