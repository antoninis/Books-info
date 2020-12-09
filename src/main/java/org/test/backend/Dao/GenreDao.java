package org.test.backend.Dao;

import org.test.backend.Entity.Genre;
import org.test.backend.Service.DBService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDao implements Dao<Genre> {
    @Override
    public List<Genre> getAll() {
        List<Genre> list = new ArrayList<>();
        String sql="SELECT * FROM genres;";
        try (Connection connection= DBService.getConnection();
             Statement statement= connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getLong("genre_id"));
                genre.setName(resultSet.getString("name"));
                list.add(genre);
            }
        } catch (SQLException e){
            list=null;
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Genre getById(Long id) {
        Genre genre = null;
        String sql = "SELECT * FROM genres WHERE genre_id = ?;";
        try (Connection connection = DBService.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1,id);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                genre = new Genre();
                genre.setId(resultSet.getLong("genre_id"));
                genre.setName(resultSet.getString("name"));

            }
        } catch (SQLException e) {
            genre = null;
            e.printStackTrace();
        }
        return genre;
    }

    @Override
    public void delete(Genre genre) {
        String sql = "DELETE FROM genres WHERE genre_id = ?;";
        try (Connection connection = DBService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1,genre.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(Genre genre) {
        String sql = "INSERT INTO genres(name) VALUES(?)";
        try (Connection connection = DBService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, genre.getName());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void change(Genre genre) {
        String sql = "UPDATE genres set name = ? WHERE genre_id=?";
        try (Connection connection = DBService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, genre.getName());
            preparedStatement.setLong(2, genre.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
