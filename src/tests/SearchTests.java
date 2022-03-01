package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearchFieldPlaceholder() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.assertSearchFieldPlaceholder();
    }

    @Test
    public void testSearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("JAVA");
        searchPageObject.waitForSearchResult("Object-oriented programming language");

    }

    @Test
    public void testCancelSearch() {

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {

        String searchLine = "Linkin Park Diskography";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        int amountOfSearchResults = searchPageObject.getAmountOfFoundArticles();

        assertTrue("Not enough searchLine result items was found", amountOfSearchResults > 0);

    }

    @Test
    public void testAmountOfEmptySearch() {

        String searchLine = "werververv3r4ervre24";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForEmptyResultLabel();
        searchPageObject.assertThereIsNoSearchResult();

    }

    @Test
    public void testSearchResultsAndCancel() {

        String searchLine = "Kotlin";
        String firstArticleNameSubstring = "Kotlin (programming language)";
        String secondArticleNameSubstring = "Kotlin-class destroyer";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForSearchResult(firstArticleNameSubstring);
        searchPageObject.waitForSearchResult(secondArticleNameSubstring);
        searchPageObject.clickCancelSearch();
        searchPageObject.assertArticleNotPresentInSearchResults("Kotlin");
        searchPageObject.assertArticleNotPresentInSearchResults("Kotlin (programming language)");
        searchPageObject.assertArticleNotPresentInSearchResults("Kotlin-class destroyer");
    }

    @Test
    public void testSearchResultsContainSearchInput() {

        String searchLine = "Java";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.assertSearchResultContainsText(searchLine, 1);
        searchPageObject.assertSearchResultContainsText(searchLine, 2);
        searchPageObject.assertSearchResultContainsText(searchLine, 3);
    }

    @Test
    public void testSearchResultsContainTitleAndDescription() {

        String searchLine = "Java";

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitForElementByTitleAndDescription("Java", "Island of Indonesia");
        searchPageObject.waitForElementByTitleAndDescription("JavaScript", "Programming language");
        searchPageObject.waitForElementByTitleAndDescription("Java (programming language)", "Object-oriented programming language");
    }
}
