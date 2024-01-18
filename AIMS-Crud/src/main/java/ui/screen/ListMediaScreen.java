package ui.screen;

import controller.IAdminItemAdvice;
import domain.Media;
import domain.MediaRepo;
import lombok.Data;
import media_wizard.MediaEditSelectType;
import media_wizard.MediaEditState;
import ui.MessageDisplayer;
import ui.RandomContentNavigator;
import ui.Screen;
import ui.wizard.WizardTopLevel;
import ui.wizard.custom.DynamicWizardSequence;
import ui.wizard.custom.WelcomePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;

public class ListMediaScreen implements Screen {
    private final MediaRepo mediaRepo;
    private final Vector<ExtendedMedia> mediaList = new Vector<>();
    private final JList<ExtendedMedia> listPanel = new JList<>();
    private final JPanel root = new JPanel(new BorderLayout());
    private final JPanel btns = new JPanel();
    private final JButton del, view, edit;
    private final IAdminItemAdvice adminAdvice;

    @Data
    public static class ExtendedMedia {
        private final Media media;
        private Image renderedImage;

        public Image getRenderedImage() {
            if(renderedImage == null) {

                try {
                    var img = ImageIO.read(new File(media.getImageUrl()));
                    renderedImage = img.getScaledInstance(120, 200, Image.SCALE_FAST);
                } catch (Exception exception) {
                    exception.printStackTrace(System.err);
                    try {
                        renderedImage = ImageIO.read(ClassLoader.getSystemResource("icon/missing.jpg")).getScaledInstance(120, 200, Image.SCALE_FAST);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return renderedImage;

        }

        public ExtendedMedia(Media media) {
            this.media = media;
        }

    }
    public ListMediaScreen(RandomContentNavigator contentNavigator, MediaRepo mediaRepo, WizardTopLevel wizardTopLevel, MessageDisplayer messageDisplayer, IAdminItemAdvice adminAdvice) {
        this.adminAdvice = adminAdvice;
        var back = new JButton("Back");
        var p = new JPanel(new BorderLayout());
        p.add(back, BorderLayout.LINE_START);
        root.add(p, BorderLayout.PAGE_START);
        back.addActionListener(ac->{
            contentNavigator.changeTo(MainScreen.class, Map.of());
        });

        this.mediaRepo = mediaRepo;
        listPanel.setListData(mediaList);
        listPanel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        root.add(btns, BorderLayout.LINE_END);
        btns.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(0), BorderFactory.createEmptyBorder(10, 10, 10, 10))));
        btns.setLayout(new BoxLayout(btns, BoxLayout.PAGE_AXIS));
        btns.add(del = new JButton("Delete"));
        btns.add(Box.createRigidArea(new Dimension(10, 10)));
        btns.add(view = new JButton("View"));
        btns.add(Box.createRigidArea(new Dimension(10, 10)));
        btns.add(edit = new JButton("Change"));
        del.addActionListener(ev->{
            var selection = listPanel.getSelectionModel();
            int count = selection.getSelectedItemsCount();
            if(adminAdvice.canDelete(count)) {
                if (messageDisplayer.confirmDialog("Delete %s media ?".formatted(selection.getSelectedItemsCount()))) {
                    try {
                        for (int i : selection.getSelectedIndices()) {
                            mediaRepo.deleteById(mediaList.get(i).getMedia().getId());
                        }
                    } catch (Exception exception) {
                        messageDisplayer.displayCriticalError(exception.getMessage());
                    }
                    init();
                }
            }
            else messageDisplayer.displayInformation("Edit exceeded for today", "Edit exceeded 30 times today");
        });
        view.addActionListener(ev->{
            contentNavigator.changeTo(MediaViewerScreen.class, Map.of("media", listPanel.getSelectedValue().getMedia()));
        });
        edit.addActionListener(ev->{
            var media = listPanel.getSelectedValue().media;
            wizardTopLevel.run(new DynamicWizardSequence(new WelcomePanel(ClassLoader.getSystemResource("icon/MediaBanner.png"), "Welcome to create media wizard", "Press 'Next' button to continue",new MediaEditSelectType(new MediaEditState(media), mediaRepo, this.adminAdvice))));
        });

        listPanel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                var selection = listPanel.getSelectionModel();
                del.setEnabled(selection.getSelectedItemsCount() >= 1 && selection.getSelectedItemsCount() < 10);
                view.setEnabled(selection.getSelectedItemsCount() == 1);
                edit.setEnabled(selection.getSelectedItemsCount() == 1);
            }
        });
        listPanel.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                var m = (ExtendedMedia) value;
                var media = m.getMedia();
                JPanel jPanel = new JPanel();
                jPanel.setBackground(isSelected ? Color.lightGray : Color.white);
                jPanel.setLayout(new BorderLayout());
                jPanel.add(new JLabel(new ImageIcon(m.getRenderedImage())), BorderLayout.LINE_START);
                jPanel.setPreferredSize(new Dimension(100, 100));

                var content = new JPanel();
                content.setBackground(isSelected ? Color.lightGray : Color.white);
                content.setBorder(BorderFactory.createEmptyBorder(0, 10,  0, 0));
                content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
                content.add(new JLabel(media.getTitle()));
                content.add(new JLabel(media.getType()));
                content.add(new JLabel(media.getCategory()));
                jPanel.add(content);
                return jPanel;
            }
        });
        JScrollPane jScrollPane = new JScrollPane(listPanel);
        root.add(jScrollPane);
    }

    @Override
    public Component getComponent() {
        return root;
    }

    @Override
    public String getTitle() {
        return "Media list";
    }

    private void init() {
        mediaList.clear();
        try {
            mediaList.addAll(mediaRepo.getAllMedia().stream().map(ExtendedMedia::new).toList());
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
        listPanel.clearSelection();
        del.setEnabled(false);
        view.setEnabled(false);
        edit.setEnabled(false);
    }
    @Override
    public void initBeforeDisplay(Map<String, Object> argument) {
        init();
    }
}
