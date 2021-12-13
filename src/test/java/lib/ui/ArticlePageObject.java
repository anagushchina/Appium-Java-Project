package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;


abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            TITLE_ELEMENT,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST,
            OPTIONS_REMOVE_FROM_MY_LIST,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            FOLDER_BY_NAME_TPL;

    /* TEMPLATES METHODS */
    private static String getFolderXpathByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{TITLE}", name_of_folder);
    }


    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    @Step("Wait for title to appear on the article page")
    public WebElement waitForTitleToAppear()
    {
        return this.waitForElementPresent(TITLE_ELEMENT,"cannot find title element", 15);
    }

    @Step("Get article title")
    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleToAppear();
        screenshot(this.takeScreenshot("article_title"));
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            return title_element.getAttribute("name");
        } else {
            return title_element.getText();
        }
    }

    @Step("Scroll down the article page to footer")
    public void swipeToFooter()
    {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of the article", 40);
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT, "Cannot find the end of the article", 40);
        } else {
            this.scrollWebPageTillWebElementNotVisible(FOOTER_ELEMENT, "Cannot find the end of article", 40);
        }
    }

    @Step("Add article to my list for the first time (for Android platform)")
    public void addArticleToMyListForTheFirstTime(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "cannot find button to open article options",
                5
        );

        this.waitForElementPresent(
                OPTIONS_ADD_TO_MY_LIST,
                "cannot find Add to reading list menu item",
                15
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST,
                "cannot click menu option 'Add article to reading list'",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "cannot find GOT IT button",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "cannot find input field for set name of article folder",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "cannot put text into article folder input field",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "cannot find OK button",
                5
        );
    }

    @Step("Add article to an existing list (for Android platform)")
    public void addArticleToExistingList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "cannot find button to open article options",
                5
        );

        this.waitForElementPresent(
                OPTIONS_ADD_TO_MY_LIST,
                "cannot find Add to reading list menu item",
                15
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST,
                "cannot click menu option 'Add article to reading list'",
                5
        );

        //tap on the 'Learning programming' folder
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        waitForElementAndClick(
                folder_name_xpath,
                "cannot find folder by name:" + name_of_folder,
                5
        );
    }

    @Step("Close the article")
    public void closeArticle()
    {
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS())
        {
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "cannot find close button",
                    5
            );
        } else {
            System.out.println("Method closeArticle does nothing for platform: " + Platform.getInstance().getPlatformVar());
        }

    }

    @Step("Ensure that article title is presented")
    public void assertArticleTitlePresent()
    {
        this.assertElementPresent(
                TITLE_ELEMENT,
                "article title is not present"
        );
    }

    @Step("Add article to My Saved (for iOS and mobile web platforms")
    public void addArticlesToMySaved()
    {
        if (Platform.getInstance().isMW())
        {
            this.removeArticleFromMySavedIfItAdded();
        }

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST,
                "cannot find option to save article to my saved articles",
                5
        );
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Step("Remove article from My Saved if it has been added (for mobile web platform")
    public void removeArticleFromMySavedIfItAdded ()
    {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST)){
            this.waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LIST, "cannot find and click the remove from my list button", 1);
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.waitForElementPresent(OPTIONS_ADD_TO_MY_LIST, "cannot find the Add to my list button after removing article from my list", 1);
    }

}

