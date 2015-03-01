/**
 * Manages the different aspects of stages
 * @author John Morach
 *
 */

public class StageHandler {
	public static final int NUM_STAGES = 13;
	/**
	 * Array of background image file names
	 */
	private static final String[] images = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"};
	/**
	 * Array of texts displayed between stages
	 */
	private static final String[] text = new String[] {"Fire: Humans can now cook food, ward away predators, and stay warm. Migration to colder climates is now possible.",
		"Tools: Humans can now use things other than their hands to create items. Superior building is now possible.",
		"Farming: Humans can now grow their own food and no longer have to constantly move to gather food. Surpluses of food are now possible.",
		"Writing: Humans can now keep their ideas for longer periods of time, their words are set in stone. Information transfer is now possible.",
		"Metalworking: Humans can now forge metal tools, and stronger items. Better tools are now possible.",
		"Trading and Currency: Humans can now transfer goods and services. Specialized positions in society are now possible.",
		"Math: Humans are on the path to developing advanced sciences and engineering. Theoretical understanding of the world is now possible.",
		"Engineering: Humans now can take advantage of physical forms and can create even better structures in which to reside and use. Larger machines and more advanced societies are now possible.",
		"Vaccines/Sterilization: Humans can now fight diseases and viruses. A higher survival rate is now possible.",
		"Electricity: Humans can now power new and more complex contraptions. More isolated society is now possible",
		"Internet: Humans can now talk to each other from across the world. Faster communication is now possible.",
		"Humans have reached the peak of intelligence and technology and no longer have a need of you. You have done well."};
	/**
	 * Array of sound file names
	 */
	private static final String[] sounds = new String[] {"original", "fire", "tools",  "farm", "writing", "metal", "money", "math", "engineering", "vaccines", "electricity", "internet", "future"};
	/**
	 * Array of volume levels for each sound
	 */
	private static final float[] soundLevels = new float[] {-12.0f, -11.0f, -16.0f, -7.0f, -15.0f, -15.0f, -12.0f, -12.0f, -15.0f, -15.0f, -15.0f, -15.0f, -15.0f};
	
	/**
	 * Number of stages completed
	 */
	private int numCompleted = 0;
	private int numTried = 0;
	
	/**
	 * Progresses to the next stage
	 * @return Background image of the next stage
	 */
	public String nextImage() {
		numCompleted++;
		return images[numCompleted];
	}
	
	/**
	 * 
	 * @return Background image of the current stage
	 */
	public String image() {
		return images[numCompleted];
	}
	
	public void anotherTried() {
		numTried++;
	}
	
	public int getNumTried() {
		return numTried;
	}
	
	/**
	 * 
	 * @return Number of stages completed
	 */
	public int getNumCompleted() {
		return numCompleted;
	}
	
	/**
	 * 
	 * @return Display text of the stage
	 */
	public String getNextStageText() {
		Game.button = ImageLoader.getImage(Game.buttonNames[numCompleted]);
		return text[numCompleted];
	}
	
	/**
	 * 
	 * @return File name of the stage's sound
	 */
	public String getSound() {
		if (numCompleted < sounds.length)
			return sounds[numCompleted];
		return null;
	}
	
	/**
	 * 
	 * @return Decibel gain of the stage's sound
	 */
	public float getSoundLevel() {
		if (numCompleted < soundLevels.length)
			return soundLevels[numCompleted];
		return 0.0f;
	}
}