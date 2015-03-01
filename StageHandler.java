public class StageHandler {
	public static final int NUM_STAGES = 14;
	private static final String[] images = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
	private static final String[] text = new String[] {"Fire: Humans can now cook food, ward away predators, and stay warm. Migration to colder climates is now possible.",
		"Tools: Humans can now use things other than their hands to create items. Superior building is now possible.",
		"Farming: Humans can now grow their own food and no longer have to constantly move to gather food. Surpluses of food are now possible.",
		"Writing: Humans can now keep their ideas for longer periods of time, their words are set in stone. Information transfer is now possible.",
		"Metalworking: Humans can now forge metal tools, and stronger items. Better tools are now possible.",
		"Trading and Currency: Humans can now transfer goods and services. Specialized positions in society are now possible.",
		"Math: Humans are on the path to developing advanced sciences and engineering. Theoretical understanding of the world is now possible.",
		"Engineering: Humans now can take advantage of physical forms and can create even better structures in which to reside and use. Larger machines and more advanced societies are now possible.",
		"Electricity: Humans can now power new and more complex contraptions. More isolated society is now possible",
		"Internet: Humans can now talk to each other from across the world. Faster communication is now possible.",
		"Humans have reached the peak of intelligence and technology and no longer have a need of you. You have done well."};
	private static final String[] sounds = new String[] {"original", "fire", "farm", "writing", "metal", "money", "engineering", "electricity", "future"};
	private static final float[] soundLevels = new float[] {-12.0f, -11.0f, -7.0f, -7.f, -15.0f, -13.0f, -12.0f, -12.0f, -15.0f};
	
	private int numCompleted = 0;
	
	public String nextImage() {
		numCompleted++;
		return images[numCompleted];
	}
	
	public String image() {
		return images[numCompleted];
	}
	
	public int getNumCompleted() {
		return numCompleted;
	}
	
	public String getNextStageText() {
		Game.button = ImageLoader.getImage(Game.buttonNames[numCompleted]);
		return text[numCompleted];
	}
	
	public String getSound() {
		return sounds[numCompleted];
	}
	
	public float getSoundLevel() {
		return soundLevels[numCompleted];
	}
}