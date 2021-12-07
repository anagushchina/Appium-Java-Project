package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private static final String
            name_of_folder = "Learning programming",
            login = "aguschina",
            password = "Qwerty!23";

    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleToAppear();

        //String article_title = ArticlePageObject.getArticleTitle();
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
            assertEquals("ddd", article_title, ArticlePageObject.getArticleTitle());
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
    public void testSaveTwoArticlesToMyList()
    {
        //search and open an article
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Java";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        //add the article to my list, create the first list, close the article
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleToAppear();
        if (Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyListForTheFirstTime(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

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

        //String second_article_title = ArticlePageObject.getArticleTitle();
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToExistingList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        ArticlePageObject.closeArticle();

        //open My saved list
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();
        MyListsPageObject myListPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListPageObject.openFolderByMyName(name_of_folder);
        } else {
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
