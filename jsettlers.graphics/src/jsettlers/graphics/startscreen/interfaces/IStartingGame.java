package jsettlers.graphics.startscreen.interfaces;

/**
 * This is a game that is currently started.
 * 
 * @author michael
 */
public interface IStartingGame {
	void setListener(IStartingGameListener startingGameListener);

	/**
	 * Aborts the start. The listener does not need to be called afterwards.
	 */
	void abort();

	boolean isStartupFinished();
}
