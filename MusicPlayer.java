import java.io.File;
import java.util.Scanner;

public class MusicPlayer {
    private static AudioHandler audioHandler = new AudioHandler();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;
        String audioFilePath;

        System.out.println("Pulse Play");
        System.out.println("Available commands: play <filename>, pause, resume, stop, exit");
        System.out.println("Available audio files: song1.mp3, song2.mp3");

        while (true) {
            System.out.print("Enter command: ");
            command = scanner.nextLine().toLowerCase();

            if (command.startsWith("play ")) {
                audioFilePath = "audio/" + command.split(" ")[1]; // Get the filename from the command
                File audioFile = new File(audioFilePath);
                if (audioFile.exists()) {
                    try {
                        audioHandler.loadAudio(audioFilePath);
                        audioHandler.play();
                        System.out.println("Playing audio: " + command.split(" ")[1]);
                    } catch (Exception e) {
                        System.err.println("Error loading audio: " + e.getMessage());
                    }
                } else {
                    System.err.println("Audio file not found: " + audioFilePath);
                }
            } else {
                switch (command) {
                    case "pause":
                        audioHandler.pause();
                        System.out.println("Audio paused.");
                        break;
                    case "resume":
                        audioHandler.resume();
                        System.out.println("Audio resumed.");
                        break;
                    case "stop":
                        audioHandler.stop();
                        System.out.println("Audio stopped.");
                        break;
                    case "exit":
                        audioHandler.stop();
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid command. Please try again.");
                }
            }
        }
    }
}