package media_wizard;

import domain.Media;
import lombok.Getter;

public class MediaEditState extends MediaCreateState {
    @Getter
    private int id;
    public MediaEditState(Media media) {
        super(media);
        id = media.getId();
    }
}

