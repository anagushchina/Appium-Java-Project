package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject {

    private static final String
    LOGIN_BUTTON = "xpath://body//div/a[text()='Log in']",
    LOGIN_INPUT = "css:input[name='wpName']",
    PASSWORD_INPUT = "css:input[name='wpPassword']",
    SUBMIT_BUTTON = "css:button#wpLoginAttempt";

    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickAuthButton ()
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot find and click Login button", 5);
    }

    public void enterLoginData (String login, String password)
    {
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "cannot find and put data to login input", 5);
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password, "cannot find and put data to password input", 5);
    }

    public void clickSubmitButton ()
    {
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click Submit button", 5);
    }
}
