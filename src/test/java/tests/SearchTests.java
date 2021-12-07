package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;


public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();

        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "we found not enough search results",
                amount_of_search_results >0
        );
    }

    @Test
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
