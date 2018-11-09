package nl.praegus.fitnesse.slim.fixtures.util;

import nl.hsac.fitnesse.fixture.slim.SlimFixture;
import nl.hsac.fitnesse.fixture.slim.StopTestException;

import javax.swing.*;
import java.net.URL;

import static javax.swing.JOptionPane.showOptionDialog;

public class PauseTestFixture extends SlimFixture {
    private volatile boolean clicked = false;
    private boolean stopTest = false;
    private final Object lock = new Object();
    private String msg = "Test execution is paused...";


    public boolean pause() throws StopTestException {
        return pause(msg);
    }

    public boolean pause(String message) throws StopTestException {
        if(!canPause()) {
            throw new StopTestException(false, "Pause was called from a Junit run. Aborting to avoid infinite waiting..");
        }

        this.msg = message;
        SwingUtilities.invokeLater(new PauseDialog(msg));
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
            throw new StopTestException(false, "Test stopped by user.");
        }
        clicked = false;
        return true;
    }

    private boolean canPause() {
        return null == System.getProperties().getProperty("nodebug") ||
                !System.getProperties().getProperty("nodebug").equalsIgnoreCase("true");
    }

    private class PauseDialog implements Runnable {
        private String msg;
        private final String title = "FitNesse - Pause";
        private final String[] options = {"Continue", "Stop test"};
        private ImageIcon icon;

        PauseDialog(String msg) {
            this.msg = msg;
        }

        public void run() {
            JFrame jf=new JFrame();
            jf.setAlwaysOnTop(true);
            URL iconUrl = getClass().getClassLoader().getResource("fitnesse.png");
            if (null != iconUrl) {
                icon = new ImageIcon(iconUrl);
            } else {
                icon = null;
            }

            int userInput = showOptionDialog(jf, msg, title, JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);

            if (userInput == JOptionPane.NO_OPTION) {
                stopTest = true;
            }

            clicked = true;
            synchronized (lock) {
                lock.notify();
            }
        }
    }
}
