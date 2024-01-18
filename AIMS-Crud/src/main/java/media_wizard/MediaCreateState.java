package media_wizard;

import domain.Media;
import lombok.Data;

@Data
public class MediaCreateState {
   private String type, category, title, value, imageUrl;
   private String price, quantity;
   public MediaCreateState() {}
   public MediaCreateState(Media media) {
      type = media.getType();
      category = media.getCategory();
      title = media.getTitle();
      value = String.valueOf(media.getValue());
      imageUrl = media.getImageUrl();
      price = String.valueOf(media.getPrice());
      quantity = String.valueOf(media.getQuantity());
   }
}
