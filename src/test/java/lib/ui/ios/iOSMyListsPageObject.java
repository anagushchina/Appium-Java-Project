package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class iOSMyListsPageObject extends MyListsPageObject {

    static {
        ARTICLE_BY_TITLE_TPL = "xpath://XCUIElementTypeStaticText[@name='{TITLE}']";
        CLOSE_DIALOG_BUTTON = "xpath://XCUIElementTypeButton[@name='Close']";
        DELETE_ARTICLE_BUTTON = "xpath://XCUIElementTypeButton[@name='swipe action delete']";

    }
    public iOSMyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
