package tests.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

@Tag("homework")
public class RegistrationFormTests extends TestBase {

    @Test
    @DisplayName("Successful fill registration test")
    public void fieldFormTest() {
        step("Open registration form", () -> {
            open("/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
            executeJavaScript("$('footer').remove()");
            executeJavaScript("$('#fixedban').remove()");
        });
        step("Fill registration form", () -> {
            $("#firstName").setValue("Rustam");
            $("#lastName").setValue("Tyapaev");
            $("#userEmail").setValue("test@test.com");
            $("#genterWrapper").$(byText("Other")).click();
            $("#userNumber").setValue("1234567890");
            $("#dateOfBirthInput").click();
            $(".react-datepicker__year-select").selectOption("1988");
            $(".react-datepicker__month-select").selectOption("December");
            $(".react-datepicker__day--019:not(.react-datepicker__day--outside-month)").click();
            $("#subjectsInput").setValue("Maths").pressEnter();
            $("#hobbiesWrapper").$(byText("Sports")).click();
            $("#uploadPicture").uploadFromClasspath("pictures/1.png");
            $("#currentAddress").setValue("Some address");
            $("#state").click();
            $("#stateCity-wrapper").$(byText("NCR")).click();
            $("#city").click();
            $("#stateCity-wrapper").$(byText("Delhi")).click();
            $("#submit").click();
        });
        step("Verify form data", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $(".table-responsive").shouldHave(text("Rustam Tyapaev"), text("test@test.com"), text("Other"));
            $(byText("Student Name")).parent().shouldHave(text("Rustam Tyapaev"));
            $("#closeLargeModal").click();
        });
    }
}
