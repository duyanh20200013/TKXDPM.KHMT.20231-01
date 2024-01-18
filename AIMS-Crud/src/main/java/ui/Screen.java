package ui;

import java.awt.*;
import java.util.Map;

public interface Screen {
    Component getComponent();
    String getTitle();
    void initBeforeDisplay(Map<String, Object> argument);
}
