import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Utility class for sounds
 * @author John Morach
 *
 */

public class SoundUtil {
	/**
	 * Currently playing clips
	 */
	private static ArrayList<Clip> playing = new ArrayList<Clip>();
	
	/**
	 * Plays a sound once
	 * @param name File name of the sound
	 * @param db Decibel gain for volume control
	 */
	public static void playSound(String name, float db) {
		URL soundFile = Game.class.getClass().getResource("/sounds/" + name + ".wav");
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(in);
			
			// Decibel gain control
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(db);
			clip.start();
			if (playing.size() == 2) {
				// Replaces currently playing sound (not background music)
				playing.get(1).stop();
				playing.remove(1);
			}
			
			// Plays once
			playing.add(clip);
		} catch (Exception e) {}
	}
	
	/**
	 * Plays a sound indefinitely
	 * @param name File name
	 * @param db Decibel gain
	 */
	public static void loopSound(String name, float db) {
		URL soundFile = Game.class.getClass().getResource("/sounds/" + name + ".wav");
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(in);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(db);
			
			// Loops continuously
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			if (playing.size() == 2) {
				playing.get(1).stop();
				playing.remove(1);
			}
			playing.add(clip);
		} catch (Exception e) {}
	}
}