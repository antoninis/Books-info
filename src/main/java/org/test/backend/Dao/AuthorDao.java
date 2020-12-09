package org.test.backend.Dao;

import org.test.backend.Entity.Author;
import org.test.backend.Service.DBService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDao implements Dao<Author> {
    @Override
    public List<Author> getAll() {
        List<Author> list = new ArrayList<>();
        String sql="SELECT * FROM authors;";
        try (Connection connection= DBService.getConnection();
             Statement statement= connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getLong("author_id"));
                author.setFirstName(resultSet.getString("firstname"));
                author.setLastName(resultSet.getString("lastname"));
                author.setPatronymic(resultSet.getString("patronymic"));
                list.add(author);
            }
        } catch (SQLException e){
            list=null;
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Author getById(Long id) {
        Author author = null;
        String sql = "SELECT * FROM authors WHERE author_id = ?;";
        try (Connection connection = DBService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                author = new Author();
                author.setId(resultSet.getLong("author_id"));
                author.setFirstName(resultSet.getString("firstname"));
                author.setLastName(resultSet.getString("lastname"));
                author.setPatronymic(resultSet.getString("patronymic"));
            }
        } catch (SQLException e) {
            author = null;
            e.printStackTrace();
        }
        return author;
    }

    @Override
    public void delete(Author author) {
        String sql = "DELETE FROM authors WHERE author_id = ?;";
        try (Connection connection = DBService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1,author.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(Author author) {
        String sql = "INSERT INTO authors(firstname, lastname, patronymic) VALUES(?,?,?)";
        try (Connection connection = DBService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setString(3, author.getPatronymic());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void change(Author author) {
        String sql = "UPDATE authors set firstname = ?, lastname = ?," +
                "patronymic = ? WHERE author_id=?";
        try (Connection connection = DBService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setString(3, author.getPatronymic());
            preparedStatement.setLong(4, author.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
