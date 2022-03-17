package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {

        String folderName = "Learning programming";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring("Automation for Apps");

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();
        articlePageObject.addArticleToMyList(folderName);
        articlePageObject.closeArticle();

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(folderName);
        myListsPageObject.swipeByArticleToDelete(articleTitle);
    }

    @Test
    public void testSaveTwoArticlesAndDeleteOne() {

        String folderName = "Learning programming";
        String firstArticle = "Automation for Apps";
        String secondArticle = "Kotlin (programming language)";

        // Find and add the 1st article

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring(firstArticle);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        articlePageObject.addArticleToMyList(folderName);
        articlePageObject.closeArticle();

        // Find and add the 2nd article

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Kotlin");
        searchPageObject.clickByArticleWithSubstring(secondArticle);
        articlePageObject.addArticleToMyExistingList(folderName);

        // Return to the main page

        articlePageObject.closeArticle();

        // Open folder with articles and delete one

        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = new MyListsPageObject(driver);
        myListsPageObject.openFolderByName(folderName);
        myListsPageObject.swipeByArticleToDelete(firstArticle);

        // Open saved article and verify the name

        myListsPageObject.openSavedArticleByTitle(secondArticle);
        articlePageObject.assertArticleNameIsCorrect(secondArticle);
    }
}
