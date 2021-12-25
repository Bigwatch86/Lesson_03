package github;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class FirstContributorTest {

    @Test
    void firstContributorShouldBeAndreiSolntsev(){
        //Configuration.browserSize="700*200"; // если селениум может проскроллить
                                                // скролл-барами страницу, то он её скроллить автоматом
        // открыть страницу репозитория Селенида
        open ("https://github.com/selenide/selenide");
        //подвести мышку к первому элементу в области contibutors
        $(".Layout-sidebar").find(byText("Contributors")).closest("div")
                .$$("ul li").first().hover();

        //$$(".Layout-sidebar .BorderGrid-row").find(text("Contributors"));
                    //.$$("ul li").first().hover();

        // check: в появившемся окне "АНдрей Солнцев"
        $$(".Popover-message").find(visible).shouldHave(text("Andrei Solntsev"));


    }
}
