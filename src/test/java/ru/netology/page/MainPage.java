package ru.netology.page;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {


    public void selectDebit() {
        $$("button").findBy(text("Купить")).shouldBe(visible).click();
    }

    public void selectCredit() {
        $$("button").findBy(text("Купить в кредит")).shouldBe(visible).click();
    }

    public void fillCardNumber(String number) {
        $("input[placeholder='0000 0000 0000 0000']").shouldBe(visible).setValue(number);
    }

    public void fillMonth(String month) {
        $("input[placeholder='08']").shouldBe(visible).setValue(month);
    }

    public void fillYear(String year) {
        $("input[placeholder='22']").shouldBe(visible).setValue(year);
    }

    public void fillOwner(String owner) {
        $$("input.input__control").get(3).shouldBe(visible).setValue(owner);
    }

    public void fillCVC(String cvc) {
        $("input[placeholder='999']").shouldBe(visible).setValue(cvc);
    }

    public void submit() {
        $$("button").findBy(text("Продолжить")).shouldBe(visible).click();
    }

    public void shouldSeeSuccess() {
        $(".notification_status_ok").shouldBe(visible, java.time.Duration.ofSeconds(10));
    }

    public void shouldSeeError() {
        $(".notification_status_error").shouldBe(visible, java.time.Duration.ofSeconds(10));
    }

    public void shouldReturnErrorWithInvalidField(String expectedText) {
        $(".input__sub").shouldBe(visible).shouldHave(exactText(expectedText));
    }
}

