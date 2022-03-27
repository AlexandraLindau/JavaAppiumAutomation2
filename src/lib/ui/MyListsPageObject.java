package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            DELETE_ICON_IOS;

    private static String getFolderXpathByName(String folderName) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", folderName);
    }

    private static String getSavedArticleXpathByName(String title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", title);
    }

    public MyListsPageObject (AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String folderName) {

        String folderNameXpath = getFolderXpathByName(folderName);
        this.waitForElementAndClick(
                folderNameXpath,
                "Cannot find folder " + folderName,
                5
        );
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByName(articleTitle);
        this.waitForElementPresent(
                articleXpath,
                "Cannot find saved article by title " + articleTitle,
                15
        );
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByName(articleTitle);
        this.waitForElementNotPresent(
                articleXpath,
                "Saved article is still present with title " + articleTitle,
                15
        );
    }

    public void swipeByArticleToDelete(String articleTitle) {
        System.out.println(articleTitle);
        this.waitForArticleToAppearByTitle(articleTitle);
        String articleXpath = getSavedArticleXpathByName(articleTitle);
        this.swipeElementToLeft(
                articleXpath,
                "Cannot find added article and swipe it"
        );
        if (Platform.getInstance().isIOS()) {
            this.waitForElementAndClick(DELETE_ICON_IOS, "Cannot find delete icon", 5);
        }
        this.waitForArticleToDisappearByTitle(articleTitle);
    }

    public void openSavedArticleByTitle(String articleName) {
        String articleXpath = getSavedArticleXpathByName(articleName);
        this.waitForElementAndClick(articleXpath, "Cannot find and open article " + articleName, 5);
    }

    public void assertArticleIsPresent(String secondArticle) {
        String locator = getSavedArticleXpathByName(secondArticle);
        assertElementPresent(locator, "Cannot find element with locator: " + locator);
    }
}
