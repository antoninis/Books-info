package org.test.UI;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;
import org.test.backend.Entity.Author;
import org.test.backend.Entity.Book;
import org.test.backend.Entity.Genre;
import org.test.backend.Service.DBService;

import java.util.ArrayList;
import java.util.List;

public class BookEditorView extends Window {

    private TextField name = new TextField("Название");
    private ComboBox<Author> authorComboBox = new ComboBox<>("Автор");
    private ComboBox<Genre> genreComboBox = new ComboBox<>("Жанр");
    private ComboBox<String> publisherComboBox = new ComboBox<>("Издатель");
    private TextField year = new TextField("Год");
    private TextField city = new TextField("Город");

    private Button submitButton = new Button("Принять");
    private Button closeButton = new Button("Закрыть");
    private Binder<Book> binder = new Binder<>(Book.class);
    List<Author> authors = DBService.getAllAuthors();
    List<Genre> genres = DBService.getAllGenres();
    List<String> publishers = new ArrayList<>();

    public BookEditorView(Grid<Book> grid, String action) {

        setWidth("500px");
        setHeight("600px");
        setModal(true);
        setResizable(false);
        center();
        Sub();
        if (action=="add") {
            setCaption("Добавление книги");
            SetAddButtons(grid);
        }
        if (action=="edit"){
            setCaption("Изменение данных о книге");
            SetEditButtons(grid);
        }
    }

    private void Sub(){
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        name.setPlaceholder("Введите название");
        name.setWidth("90%");
        binder.forField(name)
                .bind(Book::getName, Book::setName);

        authorComboBox.setPlaceholder("Выберите Автора");
        authorComboBox.setWidth("90%");
        authorComboBox.setEmptySelectionAllowed(false);
        authorComboBox.setRequiredIndicatorVisible(true);
        binder.forField(authorComboBox)
                .bind(Book::getAuthor, Book::setAuthor);
        authorComboBox.setItems(authors);
        authorComboBox.setItemCaptionGenerator(author -> author.getFirstName() + " " + author.getLastName());

        genreComboBox.setPlaceholder("Выберите Жанр");
        genreComboBox.setWidth("90%");
        genreComboBox.setEmptySelectionAllowed(false);
        genreComboBox.setRequiredIndicatorVisible(true);
        binder.forField(genreComboBox)
                .bind(Book::getGenre, Book::setGenre);
        genreComboBox.setItems(genres);
        genreComboBox.setItemCaptionGenerator(genre -> genre.getName());

        publisherComboBox.setPlaceholder("Выберите издателя");
        publisherComboBox.setWidth("90%");
        publisherComboBox.setRequiredIndicatorVisible(true);
        publisherComboBox.setEmptySelectionAllowed(false);
        binder.forField(publisherComboBox)
                .bind(Book::getPublisher, Book::setPublisher);
        publishers.add("Москва");
        publishers.add("Санкт-Петербург");
        publishers.add("O'Reilly");
        publisherComboBox.setItems(publishers);

        year.setPlaceholder("Введите издателя");
        year.setWidth("90%");
        year.setRequiredIndicatorVisible(true);
        binder.forField(year)
                .bind(Book::getYear, Book::setYear);

        city.setPlaceholder("Введите издателя");
        city.setWidth("90%");
        binder.forField(city)
                .bind(Book::getCity, Book::setCity);


        mainLayout.addComponents(name, authorComboBox, genreComboBox, publisherComboBox, year, city);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponents(submitButton,closeButton);
        mainLayout.addComponent(buttons);

        setClosable(false);
        setContent(mainLayout);
    }

    public void SetAddButtons(Grid<Book> grid){
        submitButton.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                Book doc = new Book();
                doc.setName(name.getValue());
                doc.setAuthor(authorComboBox.getValue());
                doc.setGenre(genreComboBox.getValue());
                doc.setPublisher(publisherComboBox.getValue());
                doc.setYear(year.getValue());
                doc.setCity(city.getValue());
                DBService.saveBook(doc);

                List<Book> books = DBService.getAllBooks();
                grid.setItems(books);
                close();
            } else {
                Notification.show("Заполните форму");
            }
        });

        closeButton.addClickListener(clickEvent -> close());
    }

    public void SetEditButtons(Grid<Book> grid){
        Book book = grid.asSingleSelect().getValue();
        binder.setBean(book);
        submitButton.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                DBService.changeBook(book);
                List<Book> books = DBService.getAllBooks();
                grid.setItems(books);
                close();
            } else {
                Notification.show("Заполните форму");
            }
        });

        closeButton.addClickListener(clickEvent -> close());
    }

}
