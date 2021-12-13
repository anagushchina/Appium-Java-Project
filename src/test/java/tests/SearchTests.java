package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for articles")
public class SearchTests extends CoreTestCase {

    @Test
    @Feature(value = "search")
    @DisplayName("Search the 'Java' article")
    @Description("Enter 'Java' to the search field and ensure that expected article is presented in the search results")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    @Feature(value = "search")
    @DisplayName("Cancel search")
    @Description("Exit the search mode")
    @Step("Starting test testCancelSearch")
    @Severity(value = SeverityLevel.MINOR)
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    @Feature(value = "search")
    @DisplayName("Find list of articles")
    @Description("Ensure that amount of search results is greater than 0")
    @Step("Starting test testAmountOfNotEmptySearch")
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();

        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue(
                "we found not enough search results",
                amount_of_search_results >0
        );
    }

    @Test
    @Feature(value = "search")
    @DisplayName("No results found")
    @Description("Ensure that no search results are found")
    @Step("Starting test testAmountOfEmptyResults")
    public void testAmountOfEmptyResults()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "q1w2e3r4";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultsOfSearch();
    }

    @Test
    @Feature(value = "search")
    @DisplayName("Find list of articles and cancel search")
    @Description("Ensure that amount of search results is greater than 1 and cancel search")
    @Step("Starting test testFindListOfArticlesAndCancelSearch")
    public void testFindListOfArticlesAndCancelSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Java";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForSearchResultList();

        int amount_of_articles = SearchPageObject.getAmountOfFoundArticles();
        Assert.assertTrue ("No articles found",amount_of_articles > 1);
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultsOfSearch();
    }

    @Test
    @Feature(value = "search")
    @DisplayName("Find 3 articles")
    @Description("Ensure that the following articles are presented among the search results: 'Tallinn', 'Tallinn Airport', 'Tallinn Town Hall'")
    @Step("Starting test testFindListOfThreeArticles")
    public void testFindListOfThreeArticles()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Tallinn";
        SearchPageObject.typeSearchLine(search_line);
        if (Platform.getInstance().isAndroid()) {
            SearchPageObject.waitForElementByTitleAndDescription("Tallinn", "Capital and most populous city of Estonia");
            SearchPageObject.waitForElementByTitleAndDescription("Tallinn Airport", "Airport in Estonia");
            SearchPageObject.waitForElementByTitleAndDescription("Tallinn Town Hall", "Municipal building in Tallinn, Estonia");
        } else {
            SearchPageObject.waitForElementByTitleAndDescription("Tallinn", "Capital of Estonia");
            SearchPageObject.waitForElementByTitleAndDescription("Tallinn Airport", "Airport in Tallinn, Estonia");
            SearchPageObject.waitForElementByTitleAndDescription("Tallinn Town Hall", "Town hall in Tallinn, Estonia");
        }
    }

}
