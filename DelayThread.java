/**
 * Class for waiting a specified amount of time
 * @author John Morach
 *
 */

public class DelayThread extends Thread {
	/**
	 * Delay in milliseconds
	 */
	private int delay;
	
	// Whether or not to switch panels at the end of the delay
	private boolean switchPanel;
	
	/**
	 * 
	 * @param delay Delay in milliseconds
	 * @param switchPanel Behavior after the delay
	 */
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