package org.test.backend.Dao;

import org.test.backend.Entity.Author;
import org.test.backend.Entity.Book;
import org.test.backend.Entity.Genre;
import org.test.backend.Service.DBService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao implements Dao<Book> {
    @Override
    public List<Book> getAll() {
        List<Book> list = new ArrayList<>();
        String sql="SELECT * FROM books;";
        try (Connection connection= DBService.getConnection();
             Statement statement= connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("book_id"));
                book.setName(resultSet.getString("name"));
                Author author = new AuthorDao().getById(resultSet.getLong("author_id"));
                book.setAuthor(author);
                Genre genre = new GenreDao().getById(resultSet.getLong("genre_id"));
                book.setGenre(genre);
                book.setPublisher(resultSet.getString("publisher"));
                book.setYear(resultSet.getString("year"));
                book.setCity(resultSet.getString("city"));
                list.add(book);
            }
        } catch (SQLException e){
            list=null;
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Book getById(Long id) {
        Book book = null;
        String sql = "SELECT * FROM books WHERE book_id = ?;";
        try (Connection connection = DBService.getConnection();
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1,id);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                book = new Book();
                book.setId(resultSet.getLong("book_id"));
                book.setName(resultSet.getString("name"));
                Author author = new AuthorDao().getById(resultSet.getLong("author_id"));
                book.setAuthor(author);
                Genre genre = new GenreDao().getById(resultSet.getLong("genre_id"));
                book.setGenre(genre);
                book.setPublisher(resultSet.getString("publisher"));
                book.setYear(resultSet.getString("year"));
                book.setCity(resultSet.getString("city"));
            }
        } catch (SQLException e) {
            book = null;
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public void delete(Book book) {
        String sql = "DELETE FROM books WHERE book_id = ?;";
        try (Connection connection = DBService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1,book.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void save(Book book) {
        String sql = "INSERT INTO books(name, author_id, genre_id, publisher, year, city) VALUES(?,?,?,?,?,?)";
        try (Connection connection = DBService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setLong(2, book.getAuthor().getId());
            preparedStatement.setLong(3, book.getGenre().getId());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.setString(5, book.getYear());
            preparedStatement.setString(6, book.getCity());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void change(Book book) {
        String sql = "UPDATE books set name = ?, author_id = ?, genre_id = ?," +
                "publisher = ?, year = ?, city = ?  WHERE book_id=?";
        try (Connection connection = DBService.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setLong(2, book.getAuthor().getId());
            preparedStatement.setLong(3, book.getGenre().getId());
            preparedStatement.setString(4, book.getPublisher());
            preparedStatement.setString(5, book.getYear());
            preparedStatement.setString(6, book.getCity());
            preparedStatement.setLong(7, book.getId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
