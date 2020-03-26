package nl.praegus.fitnesse.slim.tables.util;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;

public class PauseUtil {
    private volatile boolean clicked = false;
    private boolean stopTest = false;
    private boolean timedOut = false;
    private final Object lock = new Object();
    private int debugTimeout = 600000;
    /**
     * Pause the test with a custom message and prompt the user if they wish to continue
     * @param message The message to be displayed
     * @return true if continue is selected, otherwise return false.
     */
    public boolean pause(String message) {
        if(System.getProperty("fitnesse.debugscript.timeout") != null){
            debugTimeout = Integer.parseInt(System.getProperty("fitnesse.debugscript.timeout"));
        }

        SwingUtilities.invokeLater(new PauseDialog(message));
        while (!clicked) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException ie) {
                    break;
                }
            }
        }
        if (stopTest) {
            String abortMsg;
            if(timedOut) {
                abortMsg = "Test stopped; Debug dialog timed out.";
            } else {
                abortMsg = "Test stopped by user.";
            }
            System.out.println(abortMsg);
            return false;
        }
        clicked = false;
        return true;
    }


    private class PauseDialog implements Runnable {
        private String msg;
        private final String[] options = {"Continue", "Stop test"};

        PauseDialog(String msg) {
            this.msg = msg;
        }

        public void run() {
            JFrame jf=new JFrame();
            jf.setLocation(0,0);
            jf.setAlwaysOnTop(true);

            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("fitnesse_dbg.png")));

            String title = "FitNesse - Paused";
            final JOptionPane userOpts = new JOptionPane(msg, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION, icon, options, options[0]);
            final JDialog userDialog = userOpts.createDialog(title);
            userDialog.setAlwaysOnTop(true);

            userDialog.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentShown(ComponentEvent e) {
                    super.componentShown(e);
                    final Timer t = new Timer(debugTimeout, e1 -> userDialog.setVisible(false));
                    t.start();
                }
            });
            userDialog.setVisible(true);

            Object selectedValue = userOpts.getValue();

            if(selectedValue.equals(options[1])) {
                stopTest = true;
            } else if (selectedValue.equals("uninitializedValue")) {
                stopTest = true;
                timedOut = true;
            }

            clicked = true;

            synchronized (lock) {
                lock.notify();
            }
        }
    }
}
