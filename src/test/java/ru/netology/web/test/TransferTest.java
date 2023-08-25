package ru.netology.web.test;

import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.VerificationPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;


public class TransferTest {
	DashboardPage dashboardPage;

	@BeforeEach
	void setup() {
		var loginPage = open("http://localhost:9999", LoginPage.class);
		var authInfo = getAuthInfo();
		var verificationPage = loginPage.validLogin(authInfo);
		var verificationCode = getVerificationCode();
		dashboardPage = verificationPage.validVerify(verificationCode);
	}

	@Test
	void shouldTransferFromFirstToSecond() {
		var firstCardInfo = getFirstCardInfo();
		var secondCardInfo = getSecondCardInfo();
		var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
		var SecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
		var amount = generateValidAmount(firstCardBalance);
		var expectedBalanceFirstCard = firstCardBalance - amount;
		var expectedBalanceSecondCard = SecondCardBalance + amount;
		var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
		dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
		var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
		var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
		assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
		assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
	}

	@Test
	void shouldGetErrorMessageIfAmountMoreBalance() {
		var firstCardInfo = getFirstCardInfo();
		var secondCardInfo = getSecondCardInfo();
		var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
		var SecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
		var amount = generateInvalidAmount(SecondCardBalance);
		var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
		transferPage.makeTransfer(String.valueOf(amount), secondCardInfo);
		transferPage.findErrorMessage("Выполнена попытка перевода суммы, превышающей остаток на карте списания");
		var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
		var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);
		assertEquals(firstCardBalance, actualBalanceFirstCard);
		assertEquals(secondCardInfo, actualBalanceSecondCard);
	}
}
