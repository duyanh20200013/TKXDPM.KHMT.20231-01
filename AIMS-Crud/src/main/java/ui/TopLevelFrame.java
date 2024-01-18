package ui;

import lombok.Data;
import lombok.Getter;
import ui.wizard.WizardTopLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.Semaphore;

public class TopLevelFrame extends JFrame {
    private final ScreenHolder screenHolder = new ScreenHolder();
    @Getter
    private final RandomContentNavigator randomContentNavigator = new RandomContentNavigator(screenHolder);
    @Getter
    private final MessageDisplayer messageDisplayer = new MessageDisplayer(this);
    @Getter
    private final WizardTopLevel wizardTopLevel = new WizardTopLevel(this);

    public TopLevelFrame() throws IOException {
        this(ClassLoader.getSystemClassLoader().getResourceAsStream("defaultConfig.properties"));
    }
    public TopLevelFrame(InputStream configStream) throws IOException {
        FrameConfig frameConfig = new FrameConfig(configStream);
        setLayout(new BorderLayout());
        add(screenHolder);
        setTitle(frameConfig.title);
        setPreferredSize(new Dimension(frameConfig.getWidthPx(), frameConfig.getHeightPx()));
        setLocationRelativeTo(null);
    }

    public void run() {
        pack();
        setVisible(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Semaphore sem = new Semaphore(0);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if(messageDisplayer.confirmDialog("Close program ?")) {
                    SwingUtilities.invokeLater(() -> {
                        e.getWindow().setVisible(false);
                        e.getWindow().dispose();
                        sem.release();
                    });
                }
            }
        });
        try {
            sem.acquire();
        } catch (InterruptedException exception) {
            dispose();
        }
    }

    @Data
    private static class FrameConfig {
        private String title;
        private String width;
        private String height;

        public FrameConfig(InputStream inputStream) throws IOException {
            Properties config = new Properties();
            config.load(inputStream);
            title = config.getProperty("title", "AIMS");
            width = config.getProperty("width", "1000");
            height = config.getProperty("height", "1000");
        }

        public int getWidthPx() {
            return Integer.parseInt(width);
        }

        public int getHeightPx() {
            return Integer.parseInt(height);
        }
    }
}
