//suresh change
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.LineUnavailableException;
public class oscillate {

public static void main(String[] args){
	oscillate e = new oscillate();
	
	try { e.generate( 440,1,100, "Sine"); } catch (LineUnavailableException lue) { }

	}

public oscillate() { 
/*
	try { generate( 440,1,100); } catch (LineUnavailableException lue) { }
	try { generate( 880,2,100); } catch (LineUnavailableException lue) { }
	try { generate( 440,3,100); } catch (LineUnavailableException lue) { }
	try { generate(1200,1,10 ); } catch (LineUnavailableException lue) { }
*/
	}
public void generate(int Hertz, int duration, int loudness ) throws LineUnavailableException {
	generate(Hertz,duration,loudness,"Sine");
	}
public void generate(int Hertz) throws LineUnavailableException {
	generate(Hertz,1,50,"Sine");
	}

public void generate(int Hertz, int duration, int loudness, String theType ) throws LineUnavailableException {
int k = 0;
    double rate = 44100.0;
	int nSamples = 44100;
    AudioFormat audioF;

	double volume;
	volume = 128 * loudness / 100.0;
    audioF = new AudioFormat((float)rate,8,1,true,false);
    byte[] buff = new byte[44190*duration]; // 
	nSamples = 44100 * duration;
	int seconds = nSamples * duration;
	double fudge = Math.PI * 2.0F;
	double time = 0.0;
	double Previous = 0.0;	
	int count=0;
	double widthOfCycle = (double)1.0/(double)(1.0 * Hertz);   
	double widthOfSampling = (double)1.0 / rate;
	double angle=0.0;
	double x = 0.0;
// widthOfCycle is the wave time in seconds
// this is set up for 1 second of sound
	for (int i=0; i<= nSamples  ; i++) {
		time = widthOfSampling * i;
		// where in the sound cycle are we?
		angle = fudge * time/widthOfCycle;

double q=0.0;
if (theType.equals("Sine"))     { q = Math.sin(angle); }
if (theType.equals("Square"))   { q = Math.sin(angle); }
if (theType.equals("Saw"))      { q = Math.sin(angle); }
if (theType.equals("Triangle")) { q = Math.sin(angle); }
		double amp = q * volume;
		int a = (int) amp;
//		if (k++ > 440) {
//			System.out.printf("%8.7f %8.4f %8.1f\n",angle,q,amp);
//			k=0;
//			}
		buff[i] = (byte)a;
		}
SourceDataLine sourceDL = null;
/*
Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
System.out.printf("There are %d mixers\n",mixerInfo.length);
for (int i=0; i<mixerInfo.length; i++) {
	System.out.printf("%d. %s\n",i,mixerInfo[i].toString());
	System.out.printf("   %s\n",mixerInfo[i].getDescription());
	System.out.printf("   %s\n",mixerInfo[i].getName());
	System.out.printf("   %s\n",mixerInfo[i].getVendor());
	System.out.printf("   %s\n",mixerInfo[i].getVersion());
	System.out.printf("   %04x\n",mixerInfo[i].hashCode());
	}
*/

sourceDL = AudioSystem.getSourceDataLine(audioF); // good
// sourceDL = AudioSystem.getSourceDataLine(); // good
// System.out.printf("sourceDataLine = %s\n",sourceDL);
sourceDL.open(audioF);
sourceDL.start();

sourceDL.write(buff,0,nSamples);
sourceDL.drain();
sourceDL.stop();
sourceDL.close();
}

}
