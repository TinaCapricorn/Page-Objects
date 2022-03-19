package test;

import data.DataHelper;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.TransferPage;
import java.util.Random;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    @Test
    void checkCardBalance() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        DashboardPage page = new DashboardPage();
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        int firstCardBalance = page.getCardBalance(firstCard.getCardNumber());
        int secondCardBalance = page.getCardBalance(secondCard.getCardNumber());
        assertEquals(firstCardBalance, firstCard.getBalance());
        assertEquals(secondCardBalance, secondCard.getBalance());
    }

    @Test
    void transferMoneyBetweenCards() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        DashboardPage page = new DashboardPage();
        String firstCardNumber = DataHelper.getFirstCard().getCardNumber();
        String secondCardNumber = DataHelper.getSecondCard().getCardNumber();
        int firstCardBalance = page.getCardBalance(firstCardNumber);
        int secondCardBalance = page.getCardBalance(secondCardNumber);
        int startSum = firstCardBalance + secondCardBalance;
        page.clickAddMoney(firstCardNumber);
        var transferPage = new TransferPage();
        int transferSum = new Random().nextInt((secondCardBalance / 2 - secondCardBalance / 10) + 1) + secondCardBalance / 10;
        transferPage.setSum(transferSum).setFromCardNumber(secondCardNumber).clickTransfer();
        int firstCardBalanceAfter = page.getCardBalance(firstCardNumber);
        int secondCardBalanceAfter = page.getCardBalance(secondCardNumber);
        assertEquals(firstCardBalanceAfter, firstCardBalance + transferSum);
        assertEquals(secondCardBalanceAfter, secondCardBalance - transferSum);
        assertEquals(startSum, firstCardBalanceAfter + secondCardBalanceAfter);

    }
}
