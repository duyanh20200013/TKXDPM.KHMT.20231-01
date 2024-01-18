package media_wizard;

import controller.IAdminItemAdvice;
import domain.MediaRepo;
import ui.wizard.custom.BaseWizardScreen;
import ui.wizard.custom.DynamicWizardScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MediaEditSelectType extends BaseWizardScreen implements DynamicWizardScreen {
    private JPanel jPanel = new JPanel();
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private final JRadioButton changeItem = new JRadioButton("Change item information", null, true);
    private final JRadioButton changePrice = new JRadioButton("Change price", null, false);
    private final MediaEditState state;
    private final MediaRepo mediaRepo;
    private final JLabel message;
    private final IAdminItemAdvice adminItemAdvice;
    private boolean nextable = false;
    public MediaEditSelectType(MediaEditState state, MediaRepo repo, IAdminItemAdvice adminItemAdvice) {
        this.adminItemAdvice = adminItemAdvice;
        this.state = state;
        this.mediaRepo = repo;
        buttonGroup.add(changeItem);
        buttonGroup.add(changePrice);
        jPanel.add(new JLabel("Please choose information to change"));
        jPanel.add(changePrice);
        jPanel.add(changeItem);
        jPanel.add(message = new JLabel());

        changePrice.addActionListener( ev->updateState());
        changeItem.addActionListener( ev->updateState());
        updateState();
    }

    private void updateState() {
        if(changeItem.isSelected()) {
            if(adminItemAdvice.canEdit(state.getId())) {
                nextable = true;
            }
            else {
                nextable = false;
                message.setText("Update exceeded");
            }
        }
        else {
            if(adminItemAdvice.canUpdatePrice(state.getId())) {
                nextable = true;
            }
            else {
                nextable = false;
                message.setText("Change price exceeded");
            }
        }
        notifyStateChange();
    }

    @Override
    public Component getComponent() {
        return jPanel;
    }

    @Override
    public String getTitle() {
        return "Select change";
    }

    @Override
    public void initBeforeDisplay(Map<String, Object> argument) {
    }

    @Override
    public void init() {
    }

    @Override
    public void beforeNext() {
    }

    @Override
    public void beforeBack() {
    }

    @Override
    public boolean canNext() {
        return nextable;
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    public DynamicWizardScreen getNextScreen() {
        if(changeItem.isSelected()) {
            return new MediaEditWizard(state, mediaRepo, adminItemAdvice);
        }
        else if(changePrice.isSelected()) {
            return new MediaEditPriceWizard(state, mediaRepo, adminItemAdvice);
        }
        else throw new RuntimeException();
    }

    @Override
    public boolean hasNext() {
        return true;
    }
}
