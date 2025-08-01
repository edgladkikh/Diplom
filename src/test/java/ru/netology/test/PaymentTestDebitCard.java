package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPage;
import ru.netology.data.DataGenerator;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PaymentTestDebitCard {
    MainPage mainPage = open("http://localhost:8080", MainPage.class);

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        mainPage.selectDebit();
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("1. Успешная оплата APPROVED картой")
    void shouldSuccessWithApprovedCard() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldSeeSuccess();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("2. Отказ оплаты DECLINED картой")
    void shouldUnsuccessWithDeclinedCard() {
        mainPage.fillCardNumber(DataHelper.getDeclinedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldSeeError();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }

    @Test
    @DisplayName("3. Отказ оплаты несуществующей картой")
    void shouldUnsuccessWithNonexistentCard() {
        mainPage.fillCardNumber(DataGenerator.generateCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldSeeError();
    }

    @Test
    @DisplayName("4. Попытка оплаты тура с незаполненным полем номер карты")
    void shouldUnsuccessWithEmptyCardNumber() {
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверный формат");
    }

    @Test
    @DisplayName("5. Попытка оплаты тура с полем номера карты, заполненным нулями")
    void shouldUnsuccessWithNullCardNumber() {
        mainPage.fillCardNumber(DataHelper.getNullCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldSeeError();
    }

    @Test
    @DisplayName("6. Попытка оплаты тура с неполностью заполненным полем номер карты")
    void shouldUnsuccessWithIncompletelyCardNumber() {
        mainPage.fillCardNumber(DataGenerator.generateInvalidCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверный формат");
    }


    @Test
    @DisplayName("7. Попытка оплаты APPROVED картой с истекшим сроком действия")
    void shouldUnsuccessWithExpiredCard() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(-1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Истёк срок действия карты");
    }

    @Test
    @DisplayName("8. Попытка оплаты APPROVED картой с несуществующим месяцем")
    void shouldUnsuccessWithInvalidMonth() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataHelper.getInvalidMonth());
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("9. Попытка оплаты APPROVED картой с заполненным нулями полем месяц")
    void shouldUnsuccessWithNullMonth() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataHelper.getNulllMonth());
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("10. Попытка оплаты APPROVED картой с незаполненным полем месяц")
    void shouldUnsuccessWithEmptyMonth() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверный формат");
    }

    @Test
    @DisplayName("11. Попытка оплаты APPROVED картой с неполностью заполненным полем месяц")
    void shouldUnsuccessWithIncompletelyMonth() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillCardNumber(DataHelper.getIncompletelyMonth());
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверный формат");
    }

    @Test
    @DisplayName("12. Попытка оплаты APPROVED картой с заполненным нулями полем год")
    void shouldUnsuccessWithNullYear() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataHelper.getNulllYear());
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Истёк срок действия карты");
    }

    @Test
    @DisplayName("13. Попытка оплаты APPROVED картой с незаполненным полем год")
    void shouldUnsuccessWithEmptyYear() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверный формат");
    }

    @Test
    @DisplayName("14. Попытка оплаты APPROVED картой с неполностью заполненным полем год")
    void shouldUnsuccessWithIncompletelyYear() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataHelper.getIncompletelyYear());
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверный формат");
    }

    @Test
    @DisplayName("15. Попытка оплаты APPROVED картой с неполностью заполненным полем CVC/CVV")
    void shouldUnsuccessWithInvalidCVC() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataGenerator.generateInvalidCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверный формат");
    }

    @Test
    @DisplayName("16. Попытка оплаты APPROVED картой с заполненным нулями полем CVC/CVV")
    void shouldUnsuccessWithNullCVC() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.fillCVC(DataHelper.getNullCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверный формат");
    }

    @Test
    @DisplayName("17. Попытка оплаты APPROVED картой с незаполненным полем CVC/CVV")
    void shouldUnsuccessWithEmptyCVC() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataGenerator.generateOwner("en"));
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверный формат");
    }

    @Test
    @DisplayName("18. Попытка оплаты APPROVED картой с незаполненным полем владелец")
    void shouldUnsuccessWithEmptyOwner() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("19. Попытка оплаты APPROVED картой с невалидным полем владелец, символы")
    void shouldUnsuccessWithInvalidOwnerSymbols() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataHelper.getInvalidOwnerSymbols());
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверный формат");
    }

    @Test
    @DisplayName("20. Попытка оплаты APPROVED картой с невалидным полем владелец, кириллица")
    void shouldUnsuccessWithInvalidOwnerRus() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataHelper.getInvalidOwnerRus());
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверный формат");
    }

    @Test
    @DisplayName("21. Попытка оплаты APPROVED картой с невалидным полем владелец, только имя")
    void shouldUnsuccessWithInvalidOwnerOnlyName() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataHelper.getInvalidOwnerOnlyName());
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверный формат");
    }

    @Test
    @DisplayName("22. Попытка оплаты APPROVED картой с невалидным полем владелец, цифры")
    void shouldUnsuccessWithInvalidOwnerNumbers() {
        mainPage.fillCardNumber(DataHelper.getApprovedCardNumber());
        mainPage.fillMonth(DataGenerator.generateMonth(1));
        mainPage.fillYear(DataGenerator.generateYear(1));
        mainPage.fillOwner(DataHelper.getInvalidOwnerNumbers());
        mainPage.fillCVC(DataGenerator.generateCVC());
        mainPage.submit();
        mainPage.shouldReturnErrorWithInvalidField("Неверный формат");
    }
}