package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class RandomContentNavigator {
    private class EntryStruct<T> {
        private final Function<RandomContentNavigator, T> supplier;
        private T obj;

        private EntryStruct(Function<RandomContentNavigator, T> supplier) {
            this.supplier = supplier;
        }

        public T getObj() {
            if(obj == null)
                obj = supplier.apply(RandomContentNavigator.this);
            return obj;
        }
    }
    private final Map<Class<? extends Screen>, EntryStruct<? extends Screen>> mp = new HashMap<>();
    private final ScreenHolder screenHolder;

    public RandomContentNavigator(ScreenHolder screenHolder) {
        if(screenHolder == null) throw new IllegalArgumentException("not null");
        this.screenHolder = screenHolder;
    }

    public void register(Function<RandomContentNavigator, ? extends Screen> contentContent, Class<? extends Screen> clazz) {
        if(contentContent == null) throw new IllegalArgumentException("not null");
        mp.put(clazz, new EntryStruct<>(contentContent));
    }

    public <U extends Screen> U get(Class<U> panelClass) {
        return (U) mp.get(panelClass).getObj();
    }

    public void changeTo(Class<? extends Screen> panelClass, Map<String, Object> argument) {
        if(panelClass == null) throw new IllegalArgumentException("not null");
        var c = get(panelClass);
        if(c == null)
            throw new IllegalStateException("Khong co man hinh '%s'".formatted(panelClass.getSimpleName()));
        c.initBeforeDisplay(argument);
        screenHolder.setContent(c.getComponent(), c.getTitle());
    }
}
