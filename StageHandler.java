public class StageHandler {
	public static final int NUM_STAGES = 14;
	private static final String[] images = new String[] {"1", "2", "3", "4", "5"};
	private static final String[] text = new String[] {"Fire: Humans can now cook food, ward away predators, and stay warm. Migration to colder climates is now possible.",
		"Farming: Humans can now grow their own food and no longer have to constantly move to gather food. Surpluses of food are now possible.",
		"Writing: Humans can now keep their ideas for longer periods of time, their words are set in stone. Information transfer is now possible.",
		"Metalworking: Humans can now forge metal tools, and stronger items. Better tools are now possible.",
		"Trading and Currency: Humans can now transfer goods and services. Specialized positions in society are now possible.",
		"Math: Humans are on the path to developing advanced sciences and engineering. Theoretical understanding of the world is now possible.",
		"Engineering: Humans now can take advantage of physical forms and can create even better structures in which to reside and use. Larger machines and more advanced societies are now possible.",
		"Electricity: Humans can now power new and more complex contraptions. More isolated society is now possible",
		"Internet: Humans can now talk to each other from across the world. Faster communication is now possible.",
		"Humans have reached the peak of intelligence and technology and no longer have a need of you. You have done well."};
	
	private int numCompleted = 0;
	
	public String nextImage() {
		numCompleted++;
		return images[numCompleted];
	}
	
	public int getNumCompleted() {
		return numCompleted;
	}
	
	public String getNextStageText() {
		return text[numCompleted];
	}
}