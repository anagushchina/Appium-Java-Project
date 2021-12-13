package tests.iOS;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

@Epic("Tests for profile")
public class GetStartedTest extends CoreTestCase {

    @Test
    @Feature(value = "app launch")
    @DisplayName("Pass through welcome screens")
    @Description("Pass through welcome screens")
    @Step("Starting test testPassThroughWelcome")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testPassThroughWelcome(){

        if ((Platform.getInstance().isIOS()) || (Platform.getInstance().isMW())) {
            return;
        }

        WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
        WelcomePageObject.waitForLearnMoreLink();
        WelcomePageObject.clickNextButton();

        WelcomePageObject.waitForNewWaysToExploreText();
        WelcomePageObject.clickNextButton();

        WelcomePageObject.waitForSearchInNearly300LangText();
        WelcomePageObject.clickNextButton();

        WelcomePageObject.waitForHelpMakeTheAppBetterText();
        WelcomePageObject.clickGetStartedButton();

    }

}
