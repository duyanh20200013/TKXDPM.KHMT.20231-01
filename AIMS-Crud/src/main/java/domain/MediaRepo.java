package domain;

import java.sql.SQLException;
import java.util.List;

public interface MediaRepo {
    void save(Media media) throws Exception;
    List<Media> getAllMedia() throws Exception;

    void deleteById(int id) throws SQLException;

    void update(Media media) throws Exception;
}
