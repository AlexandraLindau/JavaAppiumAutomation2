package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {

    private static final String
            FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']/..";

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
                By.xpath(folderNameXpath),
                "Cannot find folder " + folderName,
                5
        );
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByName(articleTitle);
        this.waitForElementPresent(
                By.xpath(articleXpath),
                "Cannot find saved article by title " + articleTitle,
                15
        );
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        String articleXpath = getSavedArticleXpathByName(articleTitle);
        this.waitForElementNotPresent(
                By.xpath(articleXpath),
                "Saved article is still present with title " + articleTitle,
                15
        );
    }

    public void swipeByArticleToDelete(String articleTitle) {
        this.waitForArticleToAppearByTitle(articleTitle);
        String articleXpath = getSavedArticleXpathByName(articleTitle);
        this.swipeElementToLeft(
                By.xpath(articleXpath),
                "Cannot find added article and swipe it"
        );
        this.waitForArticleToDisappearByTitle(articleTitle);
    }



}
