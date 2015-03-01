import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundUtil {
	private static ArrayList<Clip> playing = new ArrayList<Clip>();
	
	public static void playSound(String name, float db) {
		File soundFile = new File("bin/sounds/" + name + ".wav");
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(in);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(db);
			clip.start();
			if (playing.size() == 2) {
				playing.get(1).stop();
				playing.remove(1);
			}
			playing.add(clip);
		} catch (Exception e) {}
	}
	
	public static void loopSound(String name, float db) {
		File soundFile = new File("bin/sounds/" + name + ".wav");
		System.out.println(soundFile.exists());
		try {
			AudioInputStream in = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(in);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(db);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			if (playing.size() == 2) {
				playing.get(1).stop();
				playing.remove(1);
			}
			playing.add(clip);
		} catch (Exception e) {}
	}
}