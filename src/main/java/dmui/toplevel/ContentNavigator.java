package dmui.toplevel;

import dmui.content.BaseContent;
import java.util.HashMap;
import java.util.Map;

public class ContentNavigator {
    private Map<Class<? extends BaseContent>, BaseContent> mp = new HashMap<>();
    private final Screen screen;

    public ContentNavigator(Screen screen) {
        this.screen = screen;
    }
    public <T extends BaseContent>void register(T contentContent) {
        mp.put(contentContent.getClass(), contentContent);
    }

    BaseContent get(Class<? extends BaseContent> panelClass) {
        return mp.get(panelClass);
    }

    public void changeTo(Class<? extends BaseContent> panelClass) {
        screen.setContent(get(panelClass));
    }
}
