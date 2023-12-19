package repo.impl;

import domain.IItemDomain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Getter @Setter(AccessLevel.MODULE)
public class SimpleItem implements IItemDomain {
    private int itemId;
    private String type;
    private String title;
    private long price;
    private long remain;
    @Getter(AccessLevel.NONE)
    private byte[] image;
    private boolean isDeleted;

    @Override
    public boolean hasEnough(int count) {
        return count <= remain;
    }

    @Override
    public InputStream getImage() {
        if(image == null) return InputStream.nullInputStream();
        return new ByteArrayInputStream(image);
    }
}
