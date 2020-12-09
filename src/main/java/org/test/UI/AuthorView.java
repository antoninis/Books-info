package org.test.UI;

import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.test.backend.Entity.Author;
import org.test.backend.Entity.Book;
import org.test.backend.Service.DBService;

import java.util.List;

public class AuthorView extends VerticalLayout implements View {

    public static final String NAME = "";

    private Grid<Author> authorGrid = new Grid<>(Author.class);
    private Button addButton = new Button("Добавить");
    private Button editButton = new Button("Изменить");
    private Button deleteButton = new Button("Удалить");
    private int i;

    public AuthorView() {
        setAuthorView();
        setButtons();
        upgradeGrid();
    }

    private void setAuthorView(){
        authorGrid.removeAllColumns();
        authorGrid.addColumn(Author::getFirstName).setCaption("Имя");
        authorGrid.addColumn(Author::getLastName).setCaption("Фамилия");
        authorGrid.addColumn(Author::getPatronymic).setCaption("Отчество");
        authorGrid.addColumn(Author ->{
            List<Book> books = DBService.getAllBooks();
            i=0;
            for (Book r:books){
                if (r.getAuthor().getId()==Author.getId()){
                    i++;
                }
            }
            return i;
        }).setCaption("Кол-во книг");
        authorGrid.setSizeFull();
        setMargin(true);
        setSpacing(true);
        setSizeFull();


        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton,editButton,deleteButton);
        Label a = new Label ("Перед удалением убедитесь что у Автора отсутствуют Книги");

        addComponents(authorGrid,buttonsLayout,a);
        setExpandRatio(authorGrid, 1f);
    }

    private void upgradeGrid(){
        authorGrid.setItems(DBService.getAllAuthors());
    }

    private void setButtons(){
        addButton.addClickListener(clickEvent -> getUI().addWindow(new AuthorEditorView(authorGrid, "add")));
        editButton.addClickListener(clickEvent -> {
            if (authorGrid.asSingleSelect().isEmpty()) {Notification.show("Запись не выбрана").setDelayMsec(1000);}
            else {getUI().addWindow(new AuthorEditorView(authorGrid,"edit"));}
        });
        deleteButton.addClickListener(clickEvent -> {
            if (authorGrid.asSingleSelect().isEmpty()) {Notification.show("Запись не выбрана").setDelayMsec(1000);}
            else {
                try {
                    DBService.deleteAuthor(authorGrid.asSingleSelect().getValue());
                }
                catch (Exception e) {
                    //
                    Notification.show("Невозможно удалить, у Автора есть Книги").setDelayMsec(1000);
                }
            }
            upgradeGrid();
        });
    }
}
