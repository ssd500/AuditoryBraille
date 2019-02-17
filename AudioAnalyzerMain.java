package audio_analysis;

/**
 * Audio Analyzer
 * 
 * Description: 
 * Real-time audio analysis with FFT and chord detection
 * 
 * 
 * Java Sound API guide:
 * https://docs.oracle.com/javase/tutorial/sound/sampled-overview.html
 * 
 * */



import javax.sound.sampled.*;	//handles capture and play back of audio


public class AudioAnalyzerMain {
    
    public static void main(String[] args) {
	
	AudioCapture.setFormat(44100, 16);
	AudioCapture.setUp();
	
	
	int k=0;
	while(k!=600) {
	    byte[] received = AudioCapture.capture();
	    
	    for(byte b: received) System.out.println(b);
	    
	    System.out.println("---------");
	    k++;
	}
//	WaveFileWriter writer = new WaveFileWriter();
//	writer.write(in, AudioFileFormat.Type.WAVE, outStream);
    }

}
