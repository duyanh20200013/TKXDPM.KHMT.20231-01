package dmui.content;

import controller.ICartController;
import controller.ICartItemController;
import controller.IPaginatorController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static utils.Utils.countUnitStr;

public class ViewCartUI extends BaseContent {
    private final ICartController cart;
    private final List<BasePanel> itemLists;
    private final JLabel totalLabel;
    private final JPanel panelList;
    private final List<ViewCartItemUI> viewCartItemUIList = new ArrayList<>();

    public ViewCartUI(ICartController cart) {
        super("Cart manager");
        this.cart = cart;
        itemLists = new ArrayList<>();
        var pane = new BasePanel(new BorderLayout());

        var total = new BasePanel();
        totalLabel = new JLabel();
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        updateTotalLabel();
        total.setLayout(new GridBagLayout());
        total.add(totalLabel);

        var btns = new BasePanel();
        btns.setLayout(new GridBagLayout());
        var c = new GridBagConstraints();
        btns.add(payOrderButton(), c);
        c.gridy = 1;
        btns.add(clearCartButton(), c);

        var leftPanel = new BasePanel(new BorderLayout());
        leftPanel.add(btns, BorderLayout.PAGE_START);
        leftPanel.add(total, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(pane, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.LINE_END);

        panelList = new JPanel();
        panelList.setLayout(new BoxLayout(panelList, BoxLayout.Y_AXIS));
        var j = new JScrollPane(panelList);
        pane.add(j, BorderLayout.CENTER);

        IPaginatorController pager = cart.getPaginatorController();
        pane.add(new PaginatorUI(pager, List.of(3,5,7,9)), BorderLayout.PAGE_END);

        pager.addCallback((u,v,t)->{
            displayList(cart.getPage(u, v));
        });
        pager.invokeAllCallback();
    }

    private void displayList(List<ICartItemController> list) {
        int sz = list.size();
        int i=0;
        for(;i < Math.min(sz, viewCartItemUIList.size());++i)
            viewCartItemUIList.get(i).setController(list.get(i));

        for(;i<sz;++i) {
            var child = new ViewCartItemUI(list.get(i));
            viewCartItemUIList.add(child);
            panelList.add(child);
        }

        for(;i<viewCartItemUIList.size();++i)
            viewCartItemUIList.get(i).setVisible(false);

        panelList.invalidate();
    }

    private void updateTotalLabel() {
        totalLabel.setText(
            "<html>" +
            countUnitStr(cart.getItemCount(), "item") + ", " +
            countUnitStr(cart.getItemTypeCount(), "item type") + "<br>" +
            "Total without tax: " + cart.formatMoney(cart.getTotalMoney()) + " " + cart.getCurrency() + "<br>" +
            "Last update: " + cart.formatDate(cart.getSavedDate()) +
            "</html>");

    }

    private JButton payOrderButton() {
        var ret = new JButton("Pay Order");
        //TODO: add logic
        return ret;
    }

    private JButton clearCartButton() {
        var ret = new JButton("Clear Cart");
        //TODO: add logic
        return ret;
    }
}
