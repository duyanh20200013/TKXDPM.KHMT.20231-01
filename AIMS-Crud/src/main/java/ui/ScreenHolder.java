package ui;

import lombok.Getter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class ScreenHolder extends JPanel {
    private static final class FooterBar extends JLabel {
        public FooterBar() {
            super("Nguyen Khanh An - 20200003");
        }
    }
    private static final class HeaderBar extends BackgroundPanel {
        @Getter
        private final JLabel title;
        private static URL getBackgroundUrl() throws FileNotFoundException {
            var ret = ClassLoader.getSystemClassLoader().getResource("background/aims.png");
            if(ret == null) throw new FileNotFoundException("Resource not found: background/aims.png");
            return ret;
        }
        public HeaderBar() throws IOException {
            super(ImageIO.read(getBackgroundUrl()));
            setBorder(new StrokeBorder(new BasicStroke(0)));
            setLayout(new BorderLayout());

            JLabel menu = new JLabel();
            menu.setBackground(new Color(0,0,0,0));
            menu.setForeground(new Color(214,211,47));
            menu.setFont(new Font("Times New Roman", Font.BOLD, 40));
            menu.setVerticalAlignment(SwingConstants.TOP);
            menu.setPreferredSize(new Dimension(Short.MAX_VALUE, 80));
            this.title = menu;

            add(menu, BorderLayout.LINE_START);

            JLabel end = new JLabel("AIMS");
            end.setBackground(new Color(0,0,0,0));
            end.setForeground(Color.black);
            end.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            end.setVerticalAlignment(SwingConstants.BOTTOM);
            add(end, BorderLayout.LINE_END);
        }
    }
    private final JPanel currentContent;
    private final HeaderBar headerBar = new HeaderBar();
    public ScreenHolder() throws IOException {
        super(new BorderLayout());
        add(headerBar, BorderLayout.PAGE_START);
        add(new FooterBar(), BorderLayout.PAGE_END);
        currentContent = new JPanel(new BorderLayout());
        add(currentContent, BorderLayout.CENTER);
    }

    public void setContent(Component comp, String title) {
        if(comp == null) throw new IllegalArgumentException("Not null");
        if(title == null) throw new IllegalArgumentException("Not null");
        currentContent.removeAll();
        currentContent.add(comp);
        headerBar.getTitle().setText(title);
        invalidate();
        var top = getTopLevelAncestor();
        if(top instanceof Window) {
            top.validate();
            ((Window) top).pack();
            top.repaint();
        }
    }
}
