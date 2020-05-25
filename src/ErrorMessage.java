import javax.swing.*;

public class ErrorMessage {
    private String message;
    private String title;

    /**
     * Creates a popup message for error reporting
     * @param message Message to display
     * @param title popup title
     */
    ErrorMessage(String message, String title){
        JFrame frame = new JFrame();
        JOptionPane error = new JOptionPane();
        JOptionPane.showConfirmDialog(frame, message,
                title, JOptionPane.DEFAULT_OPTION);
    }
}
