package domain.repo_impl;

import domain.Media;
import domain.MediaRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class JdbcMediaRepo implements MediaRepo {
    private final Supplier<Connection> connectionSupplier;

    public JdbcMediaRepo(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    private void checkConnection() {
        try {
            if(connection == null)
                connection = connectionSupplier.get();
            if(!connection.isValid(1)) {
                connection.close();
                connection = connectionSupplier.get();
            }
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
    private Connection connection;

    @Override
    public void save(Media media) throws SQLException {
        checkConnection();
        int id;
        try(var stmt = connection.prepareStatement("INSERT INTO Media(type, category, price, quantity, title, value, imageUrl) VALUES (?, ?, ?, ?, ?, ?, ?);")) {
            stmt.setString(1, media.getType());
            stmt.setString(2, media.getCategory());
            stmt.setInt(3, media.getPrice());
            stmt.setInt(4, media.getQuantity());
            stmt.setString(5, media.getTitle());
            stmt.setInt(6, media.getValue());
            stmt.setString(7, media.getImageUrl());
            stmt.executeUpdate();
        }

        try(var stmt = connection.createStatement()) {
            var rs = stmt.executeQuery("SELECT last_insert_rowid()");
            id = rs.getInt(1);
        }
        media.setId(id);
    }

    @Override
    public List<Media> getAllMedia() throws Exception {
        checkConnection();
        var lst = new ArrayList<Media>();
        try(var stmt = connection.createStatement()) {
            var rs = stmt.executeQuery("SELECT id, type, category, price, quantity, title, value, imageUrl FROM Media");
            while(rs.next()) {
                lst.add(new Media(rs));
            }
        }
        return lst;
    }

    @Override
    public void deleteById(int id) throws SQLException {
        checkConnection();
        try(var stmt = connection.prepareStatement("DELETE FROM Media WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void update(Media media) throws SQLException {
        checkConnection();
        try(var stmt = connection.prepareStatement("UPDATE Media SET type=?, category=?, price=?, quantity=?, title=?, value=?, imageUrl=? WHERE id = ?")) {
            stmt.setString(1, media.getType());
            stmt.setString(2, media.getCategory());
            stmt.setInt(3, media.getPrice());
            stmt.setInt(4, media.getQuantity());
            stmt.setString(5, media.getTitle());
            stmt.setInt(6, media.getValue());
            stmt.setString(7, media.getImageUrl());
            stmt.setInt(8, media.getId());
            stmt.executeUpdate();
        }

    }
}
