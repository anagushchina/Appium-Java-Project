package lib.ui;

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

    public WebElement waitForTitleToAppear()
    {
        return this.waitForElementPresent(TITLE_ELEMENT,"cannot find title element", 15);
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleToAppear();
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            return title_element.getAttribute("name");
        } else {
            return title_element.getText();
        }
    }

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

    public void assertArticleTitlePresent()
    {
        this.assertElementPresent(
                TITLE_ELEMENT,
                "article title is not present"
        );
    }

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
    }

    public void removeArticleFromMySavedIfItAdded ()
    {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST)){
            this.waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LIST, "cannot find and click the remove from my list button", 1);
        }

        this.waitForElementPresent(OPTIONS_ADD_TO_MY_LIST, "cannot find the Add to my list button after removing article from my list", 1);
    }

}

