package ui.screen;

import domain.Media;
import ui.MessageDisplayer;
import ui.RandomContentNavigator;
import ui.Screen;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class MediaViewerScreen implements Screen {
    private Media media;
    private final MessageDisplayer messageDisplayer;
    private final RandomContentNavigator contentNavigator;
    private final JPanel root = new JPanel(new BorderLayout());
    private final JPanel jPanel = new JPanel(new GridBagLayout());
    private final JLabel id = new JLabel(), type = new JLabel(), category = new JLabel(), title = new JLabel(), image = new JLabel(), price = new JLabel(), quantity = new JLabel(), value = new JLabel();

    public MediaViewerScreen(RandomContentNavigator contentNavigator, MessageDisplayer messageDisplayer) {
        var back = new JButton("Back");
        var p = new JPanel(new BorderLayout());
        p.add(back, BorderLayout.LINE_START);
        root.add(jPanel);
        root.add(p, BorderLayout.PAGE_START);
        back.addActionListener(ev->{
            contentNavigator.changeTo(ListMediaScreen.class, Map.of());
        });
        this.messageDisplayer = messageDisplayer;
        this.contentNavigator = contentNavigator;
        GridBagConstraints c = new GridBagConstraints();
        c.gridheight = 8;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 10, 10, 10);
        jPanel.add(image, c);

        c.gridheight = 1;
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 1;
        jPanel.add(new JLabel("Id"), c);
        ++c.gridx;
        jPanel.add(id, c);
        ++c.gridy;

        c.gridx = 1;
        jPanel.add(new JLabel("Title"), c);
        ++c.gridx;
        jPanel.add(title,c );
        ++c.gridy;

        c.gridx = 1;
        jPanel.add(new JLabel("Type"), c);
        ++c.gridx;
        jPanel.add(type,c);
        ++c.gridy;

        c.gridx = 1;
        jPanel.add(new JLabel("Category"), c);
        ++c.gridx;
        jPanel.add(category,c);
        ++c.gridy;

        c.gridx = 1;
        jPanel.add(new JLabel("price"), c);
        ++c.gridx;
        jPanel.add(price,c);
        ++c.gridx;
        jPanel.add(new JLabel("VND"),c);
        ++c.gridy;

         c.gridx = 1;
        jPanel.add(new JLabel("quantity"), c);
        ++c.gridx;
        jPanel.add(quantity, c);
        ++c.gridx;
        jPanel.add(new JLabel("item"), c);
        ++c.gridy;

        c.gridx = 1;
        jPanel.add(new JLabel("value"), c);
        ++c.gridx;
        jPanel.add(value, c);
        ++c.gridy;
    }

    @Override
    public Component getComponent() {
        return root;
    }

    @Override
    public String getTitle() {
        return "Media view";
    }

    @Override
    public void initBeforeDisplay(Map<String, Object> argument) {
        var obj = argument.get("media");
        if(obj instanceof Media) {
            media = (Media) obj;
            init();
        }
        else {
            messageDisplayer.displayCriticalError("Cant view media");
            contentNavigator.changeTo(ListMediaScreen.class, Map.of());
        }
    }

    private void init() {
        id.setText(media.getId().toString());
        type.setText(media.getType());
        category.setText(media.getCategory());
        title.setText(media.getTitle());
        try {
            image.setIcon(new ImageIcon(media.getImageUrl()));
        } catch (Exception e) {
            image.setText("No image");
        }
        price.setText(Integer.toString(media.getPrice()));
        quantity.setText(Integer.toString(media.getQuantity()));
        value.setText(Integer.toString(media.getValue()));
    }
}
