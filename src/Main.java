/**
 * @author Justin Cagle
 */
public class Main {

    public static void main(String[] args){
        AudioEngine audio = new AudioEngine();
        TrayGUI gui = new TrayGUI();
        Timer timer = new Timer();

        // Play the clip on startup so AudioEngine is properly primed
        audio.playClip();

        // Main Loop
        while(!gui.getIsTimeToExit()){
            // Check for elapsed time interval
            if(timer.getElapsedTime() >= TrayGUI.interval)
            {
                audio.playClip();
                timer.resetTimer();
                timer.startTimer();
            }

            // Cleanup the audio file resources since it's not currently in use
            if(audio.isDone()) {
                audio.cleanup();
            }

            // Update the status in the tray icon
            gui.setStatus();
        }
    }
}
