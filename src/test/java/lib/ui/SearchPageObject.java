package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject {

    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INIT_INPUT,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL,
            SEARCH_CANCEL_BUTTON,
            SEARCH_RESULT_ELEMENT,
            SEARCH_EMPTY_RESULT_ELEMENT;

    public SearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitleAndDescription(String title, String description)
    {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{SUBSTRING_TITLE}", title).replace("{SUBSTRING_DESCRIPTION}", description);
    }

    /* TEMPLATES METHODS */


    @Step("Initiate the search input field")
    public void initSearchInput()
    {
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "Cannot find search init element");
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
    }

    @Step("Wait for the search cancel button to appear")
    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON,"cannot find search cancel button", 15);
    }

    @Step("Wait for the search cancel button to disappear")
    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON,"search cancel button is still present", 5);
    }

    @Step("Click on the search cancel button")
    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON,"cannot find and click search cancel button", 5);
    }


    @Step("Enter the text '{search_line}' to the search input field")
    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(SEARCH_INIT_INPUT, search_line, "Cannot find and type into search input", 5);
    }

    @Step("Wait for the search results list to appear")
    public void waitForSearchResultList()
    {
        this.waitForElementPresent(SEARCH_RESULT_ELEMENT, "cannot find search results list",5);
        screenshot(this.takeScreenshot("search_results"));
    }

    @Step("Wait for the search results item containing specific subtitle to appear")
    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "cannot find search result with substring: " + substring);
        screenshot(this.takeScreenshot("search_results"));
    }

    @Step("Wait for the search results item containing specific title and subtitle to appear")
    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String search_result_xpath = getResultSearchElementByTitleAndDescription(title, description);
        this.waitForElementPresent(search_result_xpath, "cannot find search result with substrings: '" + title + "' and '" + description + "'");
        screenshot(this.takeScreenshot("search_results"));
    }

    @Step("Wait for and click on the search results item containing specific subtitle")
    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "cannot find and click search result with substring" + substring,10);
    }

    @Step("Get the number of search results")
    public int getAmountOfFoundArticles()
    {
        String search_result_locator = SEARCH_RESULT_ELEMENT;
        this.waitForElementPresent(
                search_result_locator,
                "cannot find anything by request: ",
                15
        );
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Wait for the empty results label to appear")
    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty results label", 15);
    }

    @Step("Ensure that there are no search results items")
    public void assertThereIsNoResultsOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "There should not be any results");
    }

}
