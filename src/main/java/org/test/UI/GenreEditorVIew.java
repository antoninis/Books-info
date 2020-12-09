package org.test.UI;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;
import org.test.backend.Entity.Author;
import org.test.backend.Entity.Genre;
import org.test.backend.Service.DBService;

import java.util.List;

public class GenreEditorVIew extends Window {
    private TextField name = new TextField("Название");
    private Button submitButton = new Button("Принять");
    private Button closeButton = new Button("Закрыть");
    private Binder<Genre> binder = new Binder<>(Genre.class);

    public GenreEditorVIew(Grid<Genre> grid, String action) {

        setWidth("500px");
        setHeight("200px");
        setModal(true);
        setResizable(false);
        center();
        Sub();
        if (action=="add") {
            setCaption("Добавление жанра");
            SetAddButtons(grid);
        }
        if (action=="edit"){
            setCaption("Изменение жанра");
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
                .bind(Genre::getName, Genre::setName);
        mainLayout.addComponents(name);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.addComponents(submitButton,closeButton);
        mainLayout.addComponent(buttons);

        setClosable(false);
        setContent(mainLayout);
    }

    public void SetAddButtons(Grid<Genre> grid){
        submitButton.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                Genre doc = new Genre();
                doc.setName(name.getValue());
                DBService.saveGenre(doc);

                List<Genre> genres = DBService.getAllGenres();
                grid.setItems(genres);
                close();
            } else {
                Notification.show("Заполните форму");
            }
        });

        closeButton.addClickListener(clickEvent -> close());
    }

    public void SetEditButtons(Grid<Genre> grid){
        Genre genre = grid.asSingleSelect().getValue();
        binder.setBean(genre);
        submitButton.addClickListener(clickEvent -> {
            if (binder.isValid()) {
                DBService.changeGenre(genre);
                List<Genre> genres = DBService.getAllGenres();
                grid.setItems(genres);
                close();
            } else {
                Notification.show("Заполните форму");
            }
        });

        closeButton.addClickListener(clickEvent -> close());
    }
}
