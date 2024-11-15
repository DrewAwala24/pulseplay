import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.IOException;

public class AudioHandler {
    private Player player;
    private String currentFile;
    private long currentFrame; // To keep track of the current position
    private boolean isPaused; // To track if the audio is paused
    private Thread playThread;// To manage playback threads

    public void loadAudio(String filePath) throws IOException {
        if (player != null) {
            player.close();
        }
        currentFile = filePath;
        currentFrame = 0; // Reset the current frame
        isPaused = false; // Reset the paused state
    }

    public void play() {
        new Thread(() -> {
            try {
                FileInputStream fileInputStream = new FileInputStream(currentFile);
                player = new Player(fileInputStream);
                isPaused = false; // Reset paused state
                player.play(currentFrame, player.getFrameCount()); // Play from the current frame
            } catch (JavaLayerException | IOException e) {
                System.err.println("Error playing audio: " + e.getMessage());
            } finally {
                // Reset the player and current frame when done
                player = null;
                currentFrame = 0;
            }
        }).start();
    }

    public void pause() {
        if (player != null) {
            currentFrame = player.getPosition(); // Get the current position
            player.close(); // Stop the player
            isPaused = true; // Set paused state
        }
    }

    public void resume() {
        if (isPaused) {
            play(); // Call play to start from the last position
        } else {
            System.out.println("Audio is not paused.");
        }
    }

    public void stop() {
        if (player != null) {
            player.close();
            player = null;
            currentFrame = 0; // Reset the current frame
            isPaused = false; // Reset paused state
        }
    }
}
