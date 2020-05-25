/**
 * A simple timer to track how long it's been since we last played a tone
 * @author Justin Cagle
 * @author jcagle2001@msn.com
 * @version 1.0
 */
public class Timer {
    long startTime;
    long elapsedTime;

    /**
     * Constructor
     */
    Timer(){
        startTime = 0;
        elapsedTime = 0;
        startTimer();
    }

    /**
     * Method to start the count up to the next interval
     */
    public void startTimer(){
        startTime = System.currentTimeMillis();
    }

    /**
     * Method to check how long it's been since we started the timer
     * @return amount of time since the timer was last started.
     */
    public long getElapsedTime() {
        return elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
    }

    /**
     * Zeros out all time values
     */
    public void resetTimer(){
        startTime = 0;
        elapsedTime = 0;
    }
}
