package test;

import data.DataHelper;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.TransferPage;

import java.util.Objects;
import java.util.Random;

import static com.codeborne.selenide.Selenide.open;

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
        int firstCardBalance = page.getCardBalance(DataHelper.getFirstCard().getCardNumber());
        int secondCardBalance = page.getCardBalance(DataHelper.getSecondCard().getCardNumber());
        System.out.println(firstCardBalance);
        System.out.println(secondCardBalance);
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
        Objects.equals(firstCardBalanceAfter, firstCardBalance + transferSum);
        Objects.equals(secondCardBalanceAfter, secondCardBalance - transferSum);
        Objects.equals(startSum, firstCardBalanceAfter + secondCardBalanceAfter);

    }
}
