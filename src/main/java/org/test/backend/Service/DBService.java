package org.test.backend.Service;

import org.test.backend.Dao.AuthorDao;
import org.test.backend.Dao.BookDao;
import org.test.backend.Dao.GenreDao;
import org.test.backend.Entity.Author;
import org.test.backend.Entity.Book;
import org.test.backend.Entity.Genre;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.commons.io.FileUtils;

public class DBService {
    private static  Connection connection=null;

    private static AuthorDao authorDao = new AuthorDao();
    public static List<Author> getAllAuthors() {
        return authorDao.getAll();
    }
    public static void deleteAuthor(Author author) {
        authorDao.delete(author);
    }
    public static void saveAuthor(Author author) {
        authorDao.save(author);
    }
    public static void changeAuthor(Author author) {
        authorDao.change(author);
    }

    private static BookDao bookDao = new BookDao();
    public static List<Book> getAllBooks() {
        return bookDao.getAll();
    }
    public static void deleteBook(Book book) {
        bookDao.delete(book);
    }
    public static void saveBook(Book book) {
        bookDao.save(book);
    }
    public static void changeBook(Book book) {
        bookDao.change(book);
    }

    private static GenreDao genreDao = new GenreDao();
    public static List<Genre> getAllGenres() {
        return genreDao.getAll();
    }
    public static void deleteGenre(Genre genre) {
        genreDao.delete(genre);
    }
    public static void saveGenre(Genre genre) {
        genreDao.save(genre);
    }
    public static void changeGenre(Genre genre) {
        genreDao.change(genre);
    }

    public static Connection getConnection() {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
            String driver = resourceBundle.getString("db.driver");
            String url = resourceBundle.getString("db.url");
            String username = resourceBundle.getString("db.user");
            String password = resourceBundle.getString("db.password");
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return connection;
    }

    public static void createDB() {
        runSqlScript("database/file1.sql");
    }

    public static void fillDB() {
        runSqlScript("database/file2.sql");
    }

    public static void runSqlScript(String scriptPath) {
        File file = new File(scriptPath);
        String createQuery = null;
        try {
            createQuery = FileUtils.readFileToString(file,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection.createStatement().execute(createQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

