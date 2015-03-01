public class DelayThread extends Thread {
	private int delay;
	
	public DelayThread(int delay) {
		this.delay = delay;
	}
	
	@Override
	public void run() {
		try {
			sleep(delay);
		} catch (Exception e) {}
		Game.switchPanels();
	}
}