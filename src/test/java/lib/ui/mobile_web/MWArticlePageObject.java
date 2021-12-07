package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {
        TITLE_ELEMENT = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_MY_LIST = "xpath://a[contains(@title,'Watch')]";
        OPTIONS_REMOVE_FROM_MY_LIST = "xpath://a[contains(@href,'action=unwatch')]";
        CLOSE_ARTICLE_BUTTON = "xpath://XCUIElementTypeButton[@name='W']";
        FOLDER_BY_NAME_TPL = "xpath://*[@text='{TITLE}']";
    }


    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
