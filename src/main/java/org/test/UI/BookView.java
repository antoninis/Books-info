package org.test.UI;

import com.vaadin.data.HasValue;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.test.backend.Entity.Author;
import org.test.backend.Entity.Book;
import org.test.backend.Entity.Genre;
import org.test.backend.Service.DBService;

import java.util.Objects;

public class BookView extends VerticalLayout implements View {
    public static final String NAME = "books";


    private final TextField nameText = new TextField();
    private final TextField authorText = new TextField();
    private final TextField publisherText = new TextField();

    private Grid<Book> bookGrid = new Grid<>(Book.class);
    private Button addButton = new Button("Добавить");
    private Button editButton = new Button("Изменить");
    private Button deleteButton = new Button("Удалить");
    private int i;

    public BookView(){
        setBookView();
        upgradeGrid();
        setButtons();
    }

    public void setBookView(){

        Panel filterPanel = new Panel("Фильтр");
        HorizontalLayout filterLayout = new HorizontalLayout();
        filterLayout.setMargin(true);
        filterLayout.setSpacing(true);
        nameText.setPlaceholder("Название");
        authorText.setPlaceholder("Автор");
        publisherText.setPlaceholder("Издатель");
        filterLayout.addComponents(nameText, authorText, publisherText);
        filterPanel.setContent(filterLayout);


        bookGrid.removeAllColumns();
        bookGrid.addColumn(Book::getName).setCaption("Название");
        bookGrid.addColumn(Book -> Book.getAuthor().getFirstName()+" "
                    + Book.getAuthor().getLastName()).setCaption("Автор");
        bookGrid.addColumn(Book -> Book.getGenre().getName()).setCaption("Жанр");
        bookGrid.addColumn(Book::getPublisher).setCaption("Издатель");
        bookGrid.addColumn(Book::getYear).setCaption("Год");
        bookGrid.addColumn(Book::getCity).setCaption("Город");
        bookGrid.setSizeFull();
        setMargin(true);
        setSpacing(true);
        setSizeFull();


        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton,editButton,deleteButton);

        addComponents(filterPanel,bookGrid,buttonsLayout);
        setExpandRatio(bookGrid, 1f);
    }

    public void upgradeGrid(){
        bookGrid.setItems(DBService.getAllBooks());
    }

    private void setButtons(){
        addButton.addClickListener(clickEvent -> getUI().addWindow(new BookEditorView(bookGrid, "add")));
        editButton.addClickListener(clickEvent -> {
            if (bookGrid.asSingleSelect().isEmpty()) {Notification.show("Запись не выбрана").setDelayMsec(1000);}
            else {getUI().addWindow(new BookEditorView(bookGrid,"edit"));}
        });
        deleteButton.addClickListener(clickEvent -> {
            if (bookGrid.asSingleSelect().isEmpty()) {Notification.show("Запись не выбрана").setDelayMsec(1000);}
            else {
                try {
                    DBService.deleteBook(bookGrid.asSingleSelect().getValue());
                }
                catch (Exception e) {
                    //
                    Notification.show("Невозможно удалить").setDelayMsec(1000);
                }
            }
            upgradeGrid();
        });
        nameText.addValueChangeListener(this::onFilterChange);
        authorText.addValueChangeListener(this::onFilterChange);
        publisherText.addValueChangeListener(this::onFilterChange);
    }

    private void onFilterChange(HasValue.ValueChangeEvent<String> event) {
        ListDataProvider<Book> dataProvider = (ListDataProvider<Book>) bookGrid.getDataProvider();
        dataProvider.setFilter((item) -> {
            boolean nameFilter = item.getName()
                    .toLowerCase()
                    .contains(nameText.getValue().toLowerCase());
            boolean authorFilter = (item.getAuthor().getFirstName() + " " + item.getAuthor().getLastName())
                    .toLowerCase()
                    .contains(authorText.getValue().toLowerCase());
            boolean publisherFilter = item.getPublisher()
                    .toLowerCase()
                    .contains(publisherText.getValue().toLowerCase());
            return nameFilter && authorFilter && publisherFilter;
        });
    }
}
