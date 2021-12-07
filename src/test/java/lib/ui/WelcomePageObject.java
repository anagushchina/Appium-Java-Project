package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject{

    private static final String
    STEP_LEARN_MORE_LINK = "xpath://XCUIElementTypeButton[@name='Learn more about Wikipedia']",
    STEP_NEW_WAYS_TO_EXPLORE_TEXT = "id:New ways to explore",
    STEP_SEARCH_NEARLY_300_LANG_TEXT = "id:Search in nearly 300 languages",
    STEP_HELP_MAKE_THE_APP_BETTER_TEXT = "id:Help make the app better",
    NEXT_BUTTON = "xpath://XCUIElementTypeButton[@name='Next']",
    GET_STARTED_BUTTON = "xpath://XCUIElementTypeButton[@name='Get started']",
    SKIP_BUTTON = "xpath://XCUIElementTypeButton[@name='Skip']";

    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink()
    {
        this.waitForElementPresent(STEP_LEARN_MORE_LINK, "Cannot find 'Learn more about Wikipedia' link", 10);
    }

    public void clickNextButton()
    {
        this.waitForElementAndClick(NEXT_BUTTON, "Cannot find the Next button and click", 5);
    }

    public void waitForNewWaysToExploreText()
    {
        this.waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT, "Cannot find 'New ways to explore' text", 10);
    }

    public void waitForSearchInNearly300LangText()
    {
        this.waitForElementPresent(STEP_SEARCH_NEARLY_300_LANG_TEXT, "Cannot find 'Search in nearly 300 languages' text", 10);
    }

    public void waitForHelpMakeTheAppBetterText()
    {
        this.waitForElementPresent(STEP_HELP_MAKE_THE_APP_BETTER_TEXT, "Cannot find 'Help make the app better' text", 10);
    }

    public void clickGetStartedButton()
    {
        this.waitForElementAndClick(GET_STARTED_BUTTON, "Cannot find the Get started button and click", 5);
    }

    public void clickSkip()
    {
        this.waitForElementAndClick(SKIP_BUTTON, "Cannot find the Skip button and click", 5);
    }
}
