public class DelayThread extends Thread {
	private int delay;
	private boolean switchPanel;
	
	public DelayThread(int delay, boolean switchPanel) {
		this.delay = delay;
		this.switchPanel = switchPanel;
	}
	
	@Override
	public void run() {
		try {
			sleep(delay);
		} catch (Exception e) {}
		if (switchPanel)
			Game.switchPanels();
	}
}