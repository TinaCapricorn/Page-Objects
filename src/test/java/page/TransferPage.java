package page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromCardField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public TransferPage() {
        amountField.shouldBe(visible);
    }

    public TransferPage setSum(int sum) {
        amountField.setValue(String.valueOf(sum));
        return this;
    }

    public TransferPage setFromCardNumber(String cardNumber) {
        fromCardField.setValue(cardNumber);
        return this;
    }

    public void clickTransfer() {
        transferButton.click();
    }
}
