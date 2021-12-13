package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for articles")
public class MyListsTests extends CoreTestCase {

    private static final String
            name_of_folder = "Learning programming",
            login = "aguschina",
            password = "Qwerty!23";

    @Test
    @Features(value = {@Feature(value="search"), @Feature(value = "Article"), @Feature(value = "Navigation"), @Feature(value = "Saving articles")})
    @DisplayName("Save an article")
    @Description("Open the 'Java (Object-oriented programming language)' article. Save the article (Android: to the 'Learning programming' list; iOS: to My Saved list; mobile_web: to the Watchlist). Open the list and delete the article from there")
    @Step("Starting test testSaveFirstArticleToMyList")
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleToAppear();

        if (Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyListForTheFirstTime(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        if (Platform.getInstance().isMW())
        {
            AuthorizationPageObject AuthorizationPageObject = new AuthorizationPageObject(driver);
            AuthorizationPageObject.clickAuthButton();
            AuthorizationPageObject.enterLoginData(login, password);
            AuthorizationPageObject.clickSubmitButton();

            ArticlePageObject.waitForTitleToAppear();
            String article_title = "Java (programming language)";
            Assert.assertEquals("We are not on the same page after login", article_title, ArticlePageObject.getArticleTitle());
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject myListPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListPageObject.openFolderByMyName(name_of_folder);
        } else if (Platform.getInstance().isIOS()) {
            myListPageObject.clickByDialogCloseButton();
        }

        String article_title = "Java (programming language)";
        myListPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    @Features(value = {@Feature(value="search"), @Feature(value = "Article"), @Feature(value = "Navigation"), @Feature(value = "Saving articles")})
    @DisplayName("Save two articles")
    @Description("Open two articles and save them (Android: to the 'Learning programming' list; iOS: to My Saved list; mobile_web: to the Watchlist). Open the list, delete one of the article from there and ensure that second article is still presented")
    @Step("Starting test testSaveTwoArticlesToMyList")
    public void testSaveTwoArticlesToMyList()
    {
        //search and open an article
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Java";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        //add the article to my list, create the first list, close the article
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleToAppear();


        if (Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyListForTheFirstTime(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        if (Platform.getInstance().isMW())
        {
            AuthorizationPageObject AuthorizationPageObject = new AuthorizationPageObject(driver);
            AuthorizationPageObject.clickAuthButton();
            AuthorizationPageObject.enterLoginData(login, password);
            AuthorizationPageObject.clickSubmitButton();

            ArticlePageObject.waitForTitleToAppear();
            String article_title = "Java (programming language)";
            Assert.assertEquals("We are not on the same page after login", article_title, ArticlePageObject.getArticleTitle());
        }

        //search and open another article
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        if (Platform.getInstance().isAndroid()) {
            SearchPageObject.clickByArticleWithSubstring("Programming language");
        } else {
            SearchPageObject.clickByArticleWithSubstring("High-level programming language");
        }

        //add the article to created list, close the article
        ArticlePageObject.waitForTitleToAppear();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToExistingList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        //open My saved list
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();
        MyListsPageObject myListPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListPageObject.openFolderByMyName(name_of_folder);
        } else if (Platform.getInstance().isIOS()) {
            myListPageObject.clickByDialogCloseButton();
        }

        //delete first article
        String first_article_title = "Java (programming language)";
        myListPageObject.swipeByArticleToDelete(first_article_title);

        //ensure that second article is still presented
        String second_article_title = "JavaScript";
        myListPageObject.assertArticlePresent(second_article_title);
    }

}
