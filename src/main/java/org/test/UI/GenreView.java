package org.test.UI;

import com.vaadin.navigator.View;
import com.vaadin.ui.*;
import org.test.backend.Entity.Book;
import org.test.backend.Entity.Genre;
import org.test.backend.Service.DBService;
import java.util.List;

public class GenreView extends VerticalLayout implements View {
    public static final String NAME = "genres";

    private Grid<Genre> genreGrid = new Grid<>(Genre.class);
    private Button addButton = new Button("Добавить");
    private Button editButton = new Button("Изменить");
    private Button deleteButton = new Button("Удалить");
    private int i;

    public GenreView(){
        setGenreView();
        setButtons();
        upgradeGrid();
    }

    public void setGenreView(){
        genreGrid.removeAllColumns();
        genreGrid.addColumn(Genre::getName).setCaption("Название");
        genreGrid.addColumn(Genre ->{
            List<Book> books = DBService.getAllBooks();
            i=0;
            for (Book r:books){
                if (r.getAuthor().getId()==Genre.getId()){
                    i++;
                }
            }
            return i;
        }).setCaption("Кол-во книг");
        genreGrid.setSizeFull();
        setMargin(true);
        setSpacing(true);
        setSizeFull();


        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.addComponents(addButton,editButton,deleteButton);
        Label a = new Label ("Перед удалением убедитесь что у Жанра отсутствуют Книги");

        addComponents(genreGrid,buttonsLayout,a);
        setExpandRatio(genreGrid, 1f);
    }

    public void upgradeGrid(){
        genreGrid.setItems(DBService.getAllGenres());
    }

    private void setButtons(){
        addButton.addClickListener(clickEvent -> getUI().addWindow(new GenreEditorVIew(genreGrid, "add")));
        editButton.addClickListener(clickEvent -> {
            if (genreGrid.asSingleSelect().isEmpty()) {Notification.show("Запись не выбрана").setDelayMsec(1000);}
            else {getUI().addWindow(new GenreEditorVIew(genreGrid,"edit"));}
        });
        deleteButton.addClickListener(clickEvent -> {
            if (genreGrid.asSingleSelect().isEmpty()) {Notification.show("Запись не выбрана").setDelayMsec(1000);}
            else {
                try {
                    DBService.deleteGenre(genreGrid.asSingleSelect().getValue());
                }
                catch (Exception e) {
                    //
                    Notification.show("Невозможно удалить, так как присутствуют Книги этого жанра").setDelayMsec(1000);
                }
            }
            upgradeGrid();
        });
    }
}
