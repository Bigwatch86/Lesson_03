package github;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class SearchSelenideRepo {

    @Test
    void shouldFindSelenideRepositoryInGithub() {
        // открыть страницу github.com
        open("https://github.com/");
        // ввести в поле поиска selenide и нажать Enter
        $("[data-test-selector=nav-search-input]").setValue("selenide").pressEnter();// если надо искать родителя в css: sibling(0).parent();
        // нажимаем на линк от первого результата поиска
        $$("ul.repo-list li").first().$("a").click(); //если ищем список элементов, то используем $$
        // check: в заголовке встречается selenide
        $("h1").shouldHave(text("selenide / selenide")); //можно использовать \n вместо двух пробелов

        //$("adgssd").$("fhfgbvbcb"); // ошибки не будет, пока не начинается акция
        //$("adgssd").$("fhfgbvbcb").shouldHave(""); // ошибка будет, так как производим действие сравнения
        //$("adgssd").$("fhfgbvbcb").shouldBe(visible); // ошибка будет, так как производим действие сравнения
        //!!!все проверки в селениде надо делать через should!!!

        // Первый вариант каждый тест отдельно
        // ARRANGE (optional)
        // ACT
        // ASSERT

        // Второй вариант
        // ARRANGE (optional) ДЛЯ END-TO-END тестов нормально использовать несколько подряд для экономии
                                                // времени на открытие браузера несколько раз
        // ACT
        // ASSERT
        // ACT
        // ASSERT
        // ACT
        // ASSERT

        // Третий вариант (но можно вставлять промежуточные assert, если они необходимы
        // ARRANGE (optional)
        // ACT
        // ACT
        // ACT
        // ASSERT

        //проверка урла
        webdriver().shouldHave(url("saf"));


    }

}
