package tests.iOS;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase {
    @Test
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
