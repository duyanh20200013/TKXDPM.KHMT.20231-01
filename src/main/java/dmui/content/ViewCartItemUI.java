package dmui.content;

import controller.ICartItemController;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ViewCartItemUI extends BasePanel {
    private ICartItemController cartItemController;
    private final JLabel title = new JLabel();
    private final JLabel type = new JLabel();
    private final JLabel priceCalculate = new JLabel();
    private final JLabel outOfProduct = new JLabel("Out of product");
    private final ImageIcon icon = new ImageIcon();
    private final JButton add = new JButton("+");
    private final JButton minus = new JButton("-");
    private final JButton remove = new JButton("Remove");
    private final JTextField count = new JTextField();

    public ViewCartItemUI(ICartItemController controller) {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel imagePanel = new BasePanel();
        imagePanel.add(new JLabel(icon));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
        add(imagePanel, BorderLayout.LINE_START);

        JPanel contentPanel = new BasePanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(title);
        contentPanel.add(type);
        contentPanel.add(priceCalculate);
        add(contentPanel, BorderLayout.CENTER);
        count.setColumns(5);
        
        JPanel option = new JPanel();
        option.setLayout(new GridBagLayout());
        var c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 0;
        option.add(minus, c);
        c.gridx = 1;
        option.add(count, c);
        c.gridx = 2;
        option.add(add, c);
        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 3;
        option.add(remove, c);
        add(option, BorderLayout.LINE_END);

        minus.addActionListener(e->{
            controller.minus();
            init();
        });
        remove.addActionListener(e->{
            controller.remove();
            init();
        });
        add.addActionListener(e->{
            controller.plus();
            init();
        });
        count.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                syncCountText();
            }
        });
        count.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    syncCountText();
                }
            }
        });
        setController(controller);
    }

    private void syncCountText() {
        String i = count.getText();
        try {
            int c = Integer.parseInt(i);
            cartItemController.setItemCount(c);
        } catch (NumberFormatException exception) {
        }
        init();
    }

    public void setController(ICartItemController controller) {
        if(controller == null) throw new IllegalArgumentException("Must not be null");
        this.cartItemController = controller;
        init();
    }

    private void init() {
        Image image = null;
        try {
            image = cartItemController.getImage();
        } catch (IOException ioException) {
            defaultExceptionHandle(ioException);
        }
        if(image == null) image = missingImage;
        icon.setImage(image);
        title.setText(cartItemController.getTitle());
        type.setText(cartItemController.getType());
        priceCalculate.setText("%s * %s = %s".formatted(cartItemController.getCount(), cartItemController.formatMoney(cartItemController.getItemPrice()), cartItemController.formatMoney(cartItemController.getTotalPrice())));
        int cnt = cartItemController.getCount();
        count.setText(""+cnt);
        minus.setEnabled(cnt != 0);
        boolean ok = cartItemController.checkRemain();
        if(ok) {
            add.setEnabled(true);
            outOfProduct.setVisible(false);
        }
        else {
            add.setEnabled(false);
            outOfProduct.setVisible(true);
        }
    }
}
