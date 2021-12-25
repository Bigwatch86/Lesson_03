package selenide;

import com.codeborne.selenide.*;
import org.openqa.selenium.Keys;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class Snippets {

    void browser_command_examples() {
        // -Dselenide.baseUrl=http://github.com
        open("https://google.com");
        open("/customer/orders"); // адрес хоста передаётся оттдельно в baseUrl выше

        //при обычной аутентификации
        open("/", AuthenticationType.BASIC, "user", "password");

        Selenide.back(); //кнопка назад
        Selenide.refresh(); //кнопка обновить

        Selenide.clearBrowserCookies(); // чистка кук
        Selenide.clearBrowserLocalStorage(); //чистка кэш

        Selenide.confirm(); // OK in alert dialogs (JS alerts - стандартные браузерные диалоги)
        Selenide.dismiss(); // Cancel in alert dialogs

        Selenide.closeWindow(); //close active tab (вкладка=окно в данном случае)
        Selenide.closeWebDriver(); //close browser comletely

        Selenide.switchTo().frame("new"); //чтобы искать инфу внутри iframe, иначе не найдется
        Selenide.switchTo().defaultContent();

        Selenide.switchTo().window("The Internet");
    }

    void selectors_examples() {
        $("div").click();
        element("div").click();

        $("div", 2).click(); //the third div

        $x("//h1/div").click();
        $(byXpath("//h1/div")).click();

        $(byText("full text")).click(); // ищет на полное сопадение
        $(withText("ull tex")).click(); // частичное совпадение

        $("").parent();
        $("").sibling(1);
        $("").preceding();
        $("").closest("div");
        $("").lastChild();


        $("div").$("h1").find(byText("abc")).click(); //ищем div, внутри него ищем h1,
                    // внутри него ищем текст abc

        // very optional
        $(byAttribute("abc", "x")).click();
        $("[abc=x]").click();

        $(byId("mytext")).click();
        $("#mytext").click();

        $(byClassName("red")).click();
        $(".red").click();
    }

    void actions_examples() {
        $("").click();
        $("").doubleClick();
        $("").contextClick(); // right click

        $("").hover(); //подносить мышку

        $("").setValue("text"); // удаляет то, что было в поле до этого
        $("").append("text"); // добавляет текст в поле
        $("").clear();
        $("").setValue(""); // clear


        $("div").sendKeys("c"); // hotkey c on element
        actions().sendKeys("c").perform(); //hotkey c on whole application
        actions().sendKeys(Keys.chord(Keys.CONTROL, "f")).perform(); // Ctrl + F
        $("html").sendKeys(Keys.chord(Keys.CONTROL, "f"));

        $("").pressEnter();
        $("").pressEscape();
        $("").pressTab();


        // complex actions with keybord and mouse, example
        actions().moveToElement($("div")).clickAndHold().moveByOffset(300, 200).release().perform();

        // old html actions don't work with many modern frameworks
        $("").selectOption("dropdown_option");
        $("").selectRadio("radio_options");

    }

    void assertions_examples() {
        $("").shouldBe(visible);
        $("").shouldNotBe(visible);
        $("").shouldHave(text("abc"));
        $("").shouldNotHave(text("abc"));
        $("").should(appear);
        $("").shouldNot(appear);


        //longer timeouts
        $("").shouldBe(visible, Duration.ofSeconds(30));
        $("").waitUntil(visible, 30000);


    }

    void conditions_examples() {
        $("").shouldBe(visible);
        $("").shouldBe(hidden);

        $("").shouldHave(text("abc")); // нечуствительно к регистру
        $("").shouldHave(exactText("abc"));
        $("").shouldHave(textCaseSensitive("abc"));
        $("").shouldHave(exactTextCaseSensitive("abc"));
        $("").should(matchText("[0-9]abc$"));

        $("").shouldHave(cssClass("red"));

        // если нужно два класса
        //  $("").shouldHave(cssClass("red"), cssClass("active"));

        $("").shouldHave(cssValue("font-size", "12"));

        $("").shouldHave(value("25")); // частичное совпадение
        $("").shouldHave(exactValue("25")); // полное совпадение
        $("").shouldBe(empty);

        $("").shouldHave(attribute("disabled"));
        $("").shouldHave(attribute("name", "example"));
        $("").shouldHave(attributeMatching("name", "[0-9]abc$"));

        $("").shouldBe(checked); // for checkboxes

        // Warning! Only checks if it is in DOM, not if it is visible! You don't need it in most tests!
        $("").should(exist);

        // Warning! Checks only the "disabled" attribute! Will not work with many modern frameworks
        $("").shouldBe(disabled);
        $("").shouldBe(enabled);
    }

    void collections_examples() {

        $$("div"); // does nothing!

        // selections
        $$("div").filterBy(text("123")).shouldHave(size(1));
        $$("div").excludeWith(text("123")).shouldHave(size(1));

        $$("div").first().click();
        elements("div").first().click();
        // $("div").click();
        $$("div").last().click();
        $$("div").get(1).click(); // the second! (start with 0)
        $("div", 1).click(); // same as previous
        $$("div").findBy(text("123")).click(); //  finds first

        // assertions
        $$("").shouldHave(size(0));
        $$("").shouldBe(CollectionCondition.empty); // the same

        $$("").shouldHave(texts("Alfa", "Beta", "Gamma"));
        $$("").shouldHave(exactTexts("Alfa", "Beta", "Gamma"));

        $$("").shouldHave(textsInAnyOrder("Beta", "Gamma", "Alfa"));
        $$("").shouldHave(exactTextsCaseSensitiveInAnyOrder("Beta", "Gamma", "Alfa"));

        $$("").shouldHave(itemWithText("Gamma")); // only one text

        $$("").shouldHave(sizeGreaterThan(0));
        $$("").shouldHave(sizeGreaterThanOrEqual(1));
        $$("").shouldHave(sizeLessThan(3));
        $$("").shouldHave(sizeLessThanOrEqual(2));


    }

    void file_operation_examples() throws FileNotFoundException {

        File file1 = $("a.fileLink").download(); // only for <a href=".."> links
        File file2 = $("div").download(DownloadOptions.using(FileDownloadMode.FOLDER)); // more common options, but may have problems with Grid/Selenoid

        File file = new File("src/test/resources/readme.txt");
        $("#file-upload").uploadFile(file);
        $("#file-upload").uploadFromClasspath("readme.txt");
        // don't forget to submit!
        $("uploadButton").click();
    }

    void javascript_examples() {
        executeJavaScript("alert('selenide')");
        executeJavaScript("alert(arguments[0]+arguments[1])", "abc", 12);
        long fortytwo = executeJavaScript("return arguments[0]*arguments[1];", 6, 7);

    }
}
