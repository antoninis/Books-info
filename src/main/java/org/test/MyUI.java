package org.test;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.test.UI.AuthorView;
import org.test.UI.BookView;
import org.test.UI.GenreView;

@Theme(ValoTheme.THEME_NAME)
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();
        mainLayout.setMargin(false);
        mainLayout.setSpacing(false);

        HorizontalLayout menuLayout = new HorizontalLayout();
        menuLayout.setMargin(true);
        menuLayout.setSpacing(true);

        Button authorButton = new Button("Авторы", clickEvent -> getUI().getNavigator().navigateTo(AuthorView.NAME));
        authorButton.setHeight("100%");
        Button genreButton = new Button("Жанры", clickEvent -> getUI().getNavigator().navigateTo(GenreView.NAME));
        genreButton.setHeight("100%");
        Button bookButton = new Button("Книги", clickEvent -> getUI().getNavigator().navigateTo(BookView.NAME));
        bookButton.setHeight("100%");
        menuLayout.addComponents(authorButton, genreButton, bookButton);


        VerticalLayout viewsLayout = new VerticalLayout();
        viewsLayout.setSizeFull();
        viewsLayout.setMargin(false);
        viewsLayout.setSpacing(true);

        mainLayout.addComponents(menuLayout, viewsLayout);
        mainLayout.setExpandRatio(viewsLayout, 1f);

        ViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(viewsLayout);
        Navigator navigator = new Navigator(this, viewDisplay);
        navigator.addView(AuthorView.NAME, new AuthorView());
        navigator.addView(GenreView.NAME, new GenreView());
        navigator.addView(BookView.NAME, new BookView());

        setContent(mainLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
