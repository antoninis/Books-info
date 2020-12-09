package org.test.UI;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;
import org.test.backend.Entity.Author;
import org.test.backend.Service.DBService;

import java.util.List;

public class AuthorEditorView extends Window {

    private TextField firstName = new TextField("Имя");
    private TextField lastName = new TextField("Фамилия");
    private TextField patronymic = new TextField("Отчество");
    private Button submitButton = new Button("Принять");
    private Button closeButton = new Button("Закрыть");
    private Binder<Author> binder = new Binder<>(Author.class);

    public AuthorEditorView(Grid<Author> grid, String action) {

        setWidth("500px");
        setHeight("350px");
        setModal(true);
        setResizable(false);
        center();
        Sub();
        if (action=="add") {
            setCaption("Добавление автора");
            SetAddButtons(grid);
        }
        if (action=="edit"){
            setCaption("Изменение авторов");
            SetEditButtons(grid);
        }
    }

    private void Sub(){
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        firstName.setPlaceholder("Введите имя");
        lastName.setPlaceholder("Введите фамилию");
        patronymic.setPlaceholder("Введите отчество");
        firstName.setWidth("90%");
        lastName.setWidth("90%");
        patronymic.setWidth("90%");
        binder.forField(firstName)
                .bind(Author::getFirstName, Author::setFirstName);
        binder.forField(lastName)
                .bind(Author::getLastName, Author::setLastName);
        binder.forField(patronymic)
                .bind(Author::getPatronymic,Author::setPatronymic);
        mainLayout.addComponents(firstName,lastName,patronymic);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponents(submitButton,closeButton);
        mainLayout.addComponent(buttons);

        setClosable(false);
        setContent(mainLayout);
    }

    public void SetAddButtons(Grid<Author> grid){
        submitButton.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                Author doc = new Author();
                doc.setFirstName(firstName.getValue());
                doc.setLastName(lastName.getValue());
                doc.setPatronymic(patronymic.getValue());
                DBService.saveAuthor(doc);

                List<Author> authors = DBService.getAllAuthors();
                grid.setItems(authors);
                close();
            } else {
                Notification.show("Заполните форму");
            }
        });

        closeButton.addClickListener(clickEvent -> close());
    }

    public void SetEditButtons(Grid<Author> grid){
        Author author = grid.asSingleSelect().getValue();
        binder.setBean(author);
        submitButton.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                DBService.changeAuthor(author);
                List<Author> authors = DBService.getAllAuthors();
                grid.setItems(authors);
                close();
            } else {
                Notification.show("Заполните форму");
            }
        });

        closeButton.addClickListener(clickEvent -> close());
    }

}
