/*
 * Creates the Tray Icon UI for interacting with the program
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TrayGUI {
    private JPopupMenu menu;
    private TrayIcon icon;
    private SystemTray sysTray;
    private final String trayIconPath = "Stim.png";
    private Boolean isTimeToExit = false;
    private final List<JCheckBoxMenuItem> intervalList = new ArrayList<>();
    private final List<JCheckBoxMenuItem> toneList = new ArrayList<>();
    JMenuItem clipStatus = new JMenuItem("Status: ");
    static int interval = 30;

    /**
     * Constructor
     */
    TrayGUI(){
        if(!SystemTray.isSupported()){
            new ErrorMessage("SystemTray is not supported!", "Error creating Tray");
            return;
        }
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        menu = new JPopupMenu("Stim: Keep My Speakers Alive!");
        icon = new TrayIcon(createImage(), "Stim: Keep My Speakers Alive!", null);
        icon.setImageAutoSize(true);
        icon.setToolTip("Stim: Keep My Speakers Alive!");
        sysTray = SystemTray.getSystemTray();
        createComponents();
    }

    /**
     * Creates the icon image for the tray
     * @return formatted icon image
     */
    private Image createImage(){
        return new ImageIcon(trayIconPath, "Stim").getImage();
    }

    /**
     * Heavy lifting for creating the UI. Creates and sets the components in the tray menu. Also adds event listeners
     * to respond to menu selections.
     */
    private void createComponents(){
        // Create menu components
        JMenu setInterval = new JMenu("Repeat Interval");
        JCheckBoxMenuItem interval30sec = new JCheckBoxMenuItem("30 seconds", true);
        JCheckBoxMenuItem interval45sec = new JCheckBoxMenuItem("45 seconds", false);
        JCheckBoxMenuItem interval1min = new JCheckBoxMenuItem("1 minute", false);
        JCheckBoxMenuItem interval2min = new JCheckBoxMenuItem("2 minutes", false);
        JCheckBoxMenuItem interval3min = new JCheckBoxMenuItem("3 minutes", false);
        JCheckBoxMenuItem interval4min = new JCheckBoxMenuItem("4 minutes", false);
        JCheckBoxMenuItem interval5min = new JCheckBoxMenuItem("5 minutes", false);
        JCheckBoxMenuItem interval10min = new JCheckBoxMenuItem("10 minutes", false);
        JMenu setTone = new JMenu("Tone File to Use");
        JCheckBoxMenuItem testTone = new JCheckBoxMenuItem("Use Test Tone (audible)", false);
        JCheckBoxMenuItem silentTone = new JCheckBoxMenuItem("Use Normal Tone (inaudible)", true);
        JMenuItem exit = new JMenuItem("Exit");

        // Add components to menu
        menu.add(setInterval);
        setInterval.add(interval30sec);
        setInterval.add(interval45sec);
        setInterval.add(interval1min);
        setInterval.add(interval2min);
        setInterval.add(interval3min);
        setInterval.add(interval4min);
        setInterval.add(interval5min);
        setInterval.add(interval10min);
        menu.add(setTone);
        setTone.add(testTone);
        setTone.add(silentTone);
        menu.addSeparator();
        menu.add(clipStatus);
        menu.addSeparator();
        menu.add(exit);

        intervalList.add(interval30sec);
        intervalList.add(interval45sec);
        intervalList.add(interval1min);
        intervalList.add(interval2min);
        intervalList.add(interval2min);
        intervalList.add(interval3min);
        intervalList.add(interval4min);
        intervalList.add(interval5min);
        intervalList.add(interval10min);

        toneList.add(testTone);
        toneList.add(silentTone);

        initMenu();

        exit.addActionListener(e -> {
            isTimeToExit = true;
            sysTray.remove(icon);
            System.exit(0);
        });

        interval30sec.addActionListener(e -> alterAllOtherStatesInterval(interval30sec));

        interval45sec.addActionListener(e -> alterAllOtherStatesInterval(interval45sec));

        interval1min.addActionListener(e -> alterAllOtherStatesInterval(interval1min));

        interval2min.addActionListener(e -> alterAllOtherStatesInterval(interval2min));

        interval3min.addActionListener(e -> alterAllOtherStatesInterval(interval3min));

        interval4min.addActionListener(e -> alterAllOtherStatesInterval(interval4min));

        interval5min.addActionListener(e -> alterAllOtherStatesInterval(interval5min));

        interval10min.addActionListener(e -> alterAllOtherStatesInterval(interval10min));

        testTone.addActionListener(e -> alterAllOtherStatesTone(testTone));

        silentTone.addActionListener(e -> alterAllOtherStatesTone(silentTone));

        icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger()){
                    menu.setLocation(e.getX(), e.getY() - menu.getHeight());
                    menu.setInvoker(menu);
                    menu.setVisible(true);
                }
                if(e.getButton() == MouseEvent.BUTTON1){
                    menu.setVisible(false);
                }
            }
        });
    }

    /**
     * Initializes the menu
     */
    private void initMenu(){


        try{
            sysTray.add(icon);
        } catch(AWTException awt){
            new ErrorMessage(awt.getMessage(), "Error setting Icon to Tray");
        }
    }

    public Boolean getIsTimeToExit(){
        return isTimeToExit;
    }

    /**
     * Deselects all Interval menu selections that are not the currently selected one
     * @param selectedItem Item to remain selected
     */
    private void alterAllOtherStatesInterval(JCheckBoxMenuItem selectedItem){
        for(JCheckBoxMenuItem item : intervalList){
            item.setSelected(item.getText().equals(selectedItem.getText()));
        }
        setIntervalOrFile(selectedItem);
    }

    /**
     * Deselects all Tone menu selections that are not the currently selected one
     * @param selectedItem Item to remain selected
     */
    private void alterAllOtherStatesTone(JCheckBoxMenuItem selectedItem){
        for(JCheckBoxMenuItem item : toneList){
            item.setSelected(item.getText().equals(selectedItem.getText()));
        }
        setIntervalOrFile(selectedItem);
    }

    /**
     * Sets parameters based on user selection
     * @param selection selection to set.
     */
    private void setIntervalOrFile(JCheckBoxMenuItem selection){
        switch(selection.getText()){
            case "30 seconds"                  : interval = 30;                                 break;
            case "45 seconds"                  : interval = 45;                                 break;
            case "1 minute"                    : interval = 60;                                 break;
            case "2 minutes"                   : interval = 120;                                break;
            case "3 minutes"                   : interval = 180;                                break;
            case "4 minutes"                   : interval = 240;                                break;
            case "5 minutes"                   : interval = 300;                                break;
            case "10 minutes"                  : interval = 600;                                break;
            case "Use Test Tone (audible)"     : AudioEngine.filePath = "StimShotTestTone.wav"; break;
            case "Use Normal Tone (inaudible)" : AudioEngine.filePath = "StimShot.wav";         break;
            default : interval = 10; AudioEngine.filePath = "StimShot.wav";
        }
    }

    /**
     * Updates the audio file status in the UI.
     */
    public void setStatus(){
        clipStatus.setText("Status: " + AudioEngine.getStatus());
    }
}
