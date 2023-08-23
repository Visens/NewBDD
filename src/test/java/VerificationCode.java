import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationCode {
	private final SelenideElement codeField = $("[data-tes-id=code] input");
	private final SelenideElement verifyButton = $("[data-test-id=action-verify]");

	public VerificationPage() {
		codeField.shouldBe(visible);
	}

	public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
		codeField.setValue(verificationCode.getCode());
		verifyButton.click();
		return new DashboardPage();
	}
}
