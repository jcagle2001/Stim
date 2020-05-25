/*
 * Audio engine used to play a sound file
 *
 * modified from online source: https://www.geeksforgeeks.org/play-audio-file-using-java/
 */
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Self-contained audio engine to play an audio clip in the background
 * @author Justin Cagle
 * @author jcagle2001@msn.com
 * @version 1.0
 * @see <a href="https://www.geeksforgeeks.org/play-audio-file-using-java/">https://www.geeksforgeeks.org/play-audio-file-using-java/</a>
 */
public class AudioEngine {
    private static Clip clip;

    // current status of clip
    private static String status = "Not Initialized";

    AudioInputStream inputStream;
    protected static String filePath = "StimShot.wav";

    /**
     * Constructor
     */
    public AudioEngine(){
        status = "Initialized...";
    }

    /**
     * Loads and plays the audio clip
     */
    public void playClip(){
        try{
            // create input stream object
            inputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

            // create clip reference
            clip = AudioSystem.getClip();
        } catch(LineUnavailableException | IOException | UnsupportedAudioFileException | NullPointerException e) {
            new ErrorMessage(e.toString(), "Error Creating AudioEngine");
        }

        try{
            clip.open(inputStream);
            clip.start();
            status = "Playing...";
        } catch (IOException | LineUnavailableException io){
            new ErrorMessage(io.getMessage(), "Error Playing Clip");
        }
    }

    /**
     * Checks for completion of the current play cycle
     * @return Boolean
     */
    public Boolean isDone(){
        return getClipPosition() == getClipLength();
    }

    /**
     * Gets the current frame (location within the clip) of the audio file
     * @return current frame
     */
    private static long getClipPosition(){
        return clip.getMicrosecondPosition();
    }

    /**
     * Gets the overall length of the loaded sound file, in frames
     * @return total frames
     */
    private static long getClipLength(){
        return clip.getMicrosecondLength();
    }

    /**
     * Stops and closes the audio file
     */
    public void cleanup(){
        clip.stop();
        clip.close();
        status = "Waiting...";
    }

    /**
     * Gets the current status of the audio clip
     * @return status
     */
    public static String getStatus(){
        if(getClipPosition() == getClipLength())
            status = "DONE!";
        return status;
    }
}
