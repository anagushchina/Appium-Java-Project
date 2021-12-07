package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject{

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            CLOSE_DIALOG_BUTTON,
            DELETE_ARTICLE_BUTTON,
            REMOVE_FROM_SAVED_BUTTON_TPL;

    /* TEMPLATES METHODS */
    private static String getFolderXpathByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getRemoveButtonByTitle(String article_title)
    {
        return REMOVE_FROM_SAVED_BUTTON_TPL.replace("{TITLE}", article_title);
    }
    /* TEMPLATES METHODS */


    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void openFolderByMyName(String name_of_folder)
    {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementPresent(
                folder_name_xpath,
                "cannot find folder by name: " + name_of_folder,
                15
        );

        this.waitForElementAndClick(
                folder_name_xpath,
                "cannot find and click folder by name: " + name_of_folder,
                5
        );
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_title_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(article_title_xpath, "Cannot find saved article with title: " + article_title, 15);
    }

    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String article_title_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(article_title_xpath, "Saved article with title '" + article_title + "' is still presented", 15);
    }

    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_title_xpath = getSavedArticleXpathByTitle(article_title);

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
            this.swipeElementToLeft(
                    article_title_xpath,
                    "cannot find saved article title"
            );
        } else {
            String remove_locator = getRemoveButtonByTitle (article_title);
            this. waitForElementAndClick(
                    remove_locator,
                    "cannot click button to remove article from saved",
                    10
            );
        }

        if (Platform.getInstance().isIOS()){
            this.waitForElementAndClick(DELETE_ARTICLE_BUTTON, "cannot find the Delete Article button", 5);
        }

        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
        }

        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void assertArticlePresent(String article_title)
    {
        String article_title_xpath = getSavedArticleXpathByTitle(article_title);
        this.assertElementPresent(
                article_title_xpath,
                "Cannot find article with title: " + article_title
        );
    }

    public void clickByArticleWithTitle (String article_title)
    {
        String article_title_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementAndClick(
                article_title_xpath,
                "cannot find and click on article with title: " + article_title,
                5
        );
    }

    public void clickByDialogCloseButton ()
    {
        this.waitForElementAndClick(CLOSE_DIALOG_BUTTON, "Cannot find the close dialog button", 5);
    }

}
