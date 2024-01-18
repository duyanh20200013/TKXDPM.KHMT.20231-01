package ui.screen;

import domain.MediaRepo;
import ui.RandomContentNavigator;
import media_wizard.MediaCreateWizard;
import ui.wizard.WizardTopLevel;
import ui.wizard.custom.DynamicWizardSequence;
import ui.wizard.custom.WelcomePanel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Map;

public class MainScreen extends AbstractScreen {
    private final MediaRepo mediaRepo;
    static private URL addMediaButtonImageUrl() {
        var ret = ClassLoader.getSystemClassLoader().getResource("icon/MediaAdd.png");
        if(ret == null) throw new RuntimeException("Resource not found: icon/MediaAdd.png");
        return ret;
    }
    static private URL listMediaButtonImageUrl() {
        var ret = ClassLoader.getSystemClassLoader().getResource("icon/MediaList.png");
        if(ret == null) throw new RuntimeException("Resource not found: icon/MediaList.png");
        return ret;
    }

    private final WizardTopLevel wizardTopLevel;
    public MainScreen(RandomContentNavigator navigator, MediaRepo mediaRepo, WizardTopLevel wizardTopLevel) {
        super(navigator);
        this.mediaRepo = mediaRepo;
        this.wizardTopLevel = wizardTopLevel;
        screen.setLayout(new BorderLayout());
        JPanel btns = new JPanel();
        btns.setLayout(new BoxLayout(btns, BoxLayout.Y_AXIS));
        var createBtn = new JButton(new ImageIcon(addMediaButtonImageUrl()));
        var listBtn = new JButton(new ImageIcon(listMediaButtonImageUrl()));
        btns.add(createBtn);
        btns.add(Box.createRigidArea(new Dimension(10, 10)));
        btns.add(listBtn);
        btns.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        screen.add(btns, BorderLayout.LINE_END);
        JPanel main = new JPanel();
        main.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5), BorderFactory.createEtchedBorder(Color.YELLOW, Color.black)));
        main.add(new JLabel("Dashboard etc..."));
        screen.add(main);

        createBtn.addActionListener(ev->{
            wizardTopLevel.run(new DynamicWizardSequence(new WelcomePanel(ClassLoader.getSystemResource("icon/MediaBanner.png"), "Welcome to create media wizard", "Press 'Next' button to continue",new MediaCreateWizard(mediaRepo))));
        });

        listBtn.addActionListener(ev->{
            navigator.changeTo(ListMediaScreen.class, Map.of());
        });

    }

    @Override
    public String getTitle() {
        return "Admin Dashboard";
    }

    @Override
    public void init(Map<String, Object> argument) {
    }

}
