package dmui.content;

import lombok.Getter;

import java.awt.*;

public class BaseContent extends BasePanel {
    @Getter
    protected final String title;

    public BaseContent(String title) {
        super();
        this.title = title;
    }

    public BaseContent(String title, LayoutManager layoutManager) {
        super(layoutManager);
        this.title = title;
    }
}
