import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
	private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
	private final SelenideElement amountInputNew = $("[data-test-id='amoun'] input");
	private final SelenideElement fromInput = $("[data-test-id='from'] input");
	private final SelenideElement transferHead = $("Пополнение карты");
	private final SelenideElement errorMessage = $("[data-test-id='error-message']");

	public TransferPage() {
		transferHead.shouldBe(visible);
	}

	public DashboardPage makeValidTransfer(String amounToTransfer, DataHelper.CardInfo cardInfo) {
		makeTransfer(amountToTransfer, cardInfo);
		return new DashboardPage();
	}

	public void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
		amountInputNew.setValue(amountToTransfer);
		fromInput.setValue(cardInfo.getCardNumber());
		transferButton.click();
	}

	public void findErrorMessage(String expectedText) {
		errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
	}
}
