package domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import media_wizard.MediaCreateState;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
@ToString
public class Media {
    private Integer id;
    private String type, category, title, imageUrl;
    private int price, quantity, value;

    public Media() {
        id = 0;
        title = "No title";
    }

    public Media(MediaCreateState state) {
        type = state.getType();
        category = state.getCategory();
        title = state.getTitle();
        imageUrl = state.getImageUrl();
        price = Integer.parseInt(state.getPrice());
        quantity = Integer.parseInt(state.getQuantity());
        value = Integer.parseInt(state.getValue());
    }

    public Media(ResultSet rs) throws SQLException {
        id = rs.getInt(1);
        type = rs.getString(2);
        category = rs.getString(3);
        price = rs.getInt(4);
        quantity = rs.getInt(5);
        title = rs.getString(6);
        value = rs.getInt(7);
        imageUrl = rs.getString(8);
    }
}
