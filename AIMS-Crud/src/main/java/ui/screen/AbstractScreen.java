package ui.screen;

import ui.RandomContentNavigator;
import ui.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public abstract class AbstractScreen implements Screen {
    protected final JPanel screen = new JPanel();
    protected final RandomContentNavigator navigator;

    protected AbstractScreen(RandomContentNavigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public Component getComponent() {
        return screen;
    }

    public abstract void init(Map<String, Object> argument);

    @Override
    public void initBeforeDisplay(Map<String, Object> argument) {
        screen.invalidate();
        screen.setVisible(true);
        init(argument);
    }
}
