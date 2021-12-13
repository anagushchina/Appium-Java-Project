package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for articles")
public class ArticleTests extends CoreTestCase {

    @Test
    @Features(value = {@Feature(value="search"), @Feature(value = "Article")})
    @DisplayName("Compare article title with expected")
    @Description("Open 'Java (Object-oriented programming language)' article and ensure that title meets expected one")
    @Step("Starting test testCompareArticleTitle")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCompareArticleTitle()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title = ArticlePageObject.getArticleTitle();
        Assert.assertEquals(
                "Actual title doesn't match expected",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    @Features(value = {@Feature(value="search"), @Feature(value = "Article")})
    @DisplayName("Swipe article to footer")
    @Description("Open 'Java (Object-oriented programming language)' article and swipe it to footer")
    @Step("Starting test testSwipeArticle")
    @Severity(value = SeverityLevel.MINOR)
    public void testSwipeArticle()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleToAppear();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    @DisplayName("Ensure that article title is presented")
    @Features(value = {@Feature(value="search"), @Feature(value = "Article")})
    @Description("Open 'Tallinn' article and ensure that article title is presented on the article page")
    @Step("Starting test testAssertArticleTitlePresent")
    @Severity(value = SeverityLevel.MINOR)
    public void testAssertArticleTitlePresent()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Tallinn";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring("Capital of Estonia");

        //ensure that title is presented
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.assertArticleTitlePresent();
    }
}
