package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

    private final static String folderName = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() throws Exception {

        String article = "Appium";

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring(article);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.waitForTitleElement();
        } else {
            articlePageObject.waitForTitleElementIOS(article);
        }

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(folderName);
        } else {
            articlePageObject.addFirstArticleToMySaved();
        }

        articlePageObject.closeArticle();

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folderName);
        }
        myListsPageObject.swipeByArticleToDelete(article);
    }

    @Test
    public void testSaveTwoArticlesAndDeleteOne() throws Exception {

        String firstArticle = "Kotlin (programming language)";
        String secondArticle = "Appium";

        // Find and add the 1st article

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Kotlin");
        searchPageObject.clickByArticleWithSubstring(firstArticle);

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            articlePageObject.waitForTitleElement();
            articlePageObject.addArticleToMyList(folderName);
        } else {
            articlePageObject.waitForTitleElementIOS(firstArticle);
            articlePageObject.addFirstArticleToMySaved();
        }
        articlePageObject.closeArticle();

        // Find and add the 2nd article

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring(secondArticle);
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyExistingList(folderName);
        } else {
            articlePageObject.addArticleToMySaved();
        }

        // Return to the main page

        articlePageObject.closeArticle();

        // Open folder with articles and delete one

        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            myListsPageObject.openFolderByName(folderName);
        }
        myListsPageObject.swipeByArticleToDelete(firstArticle);

        // Check that the 2nd article is still in 'Saved'

        myListsPageObject.assertArticleIsPresent(secondArticle);

        // Open saved article and verify the name

        myListsPageObject.openSavedArticleByTitle(secondArticle);
        articlePageObject.assertArticleNameIsCorrect(secondArticle);
    }
}
