package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text='View page in browser']",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
            ADD_TO_MY_EXISTING_LIST_OVERLAY = "//*[@text='{FOLDER_NAME}']",
            ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
            MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "android:id/button1",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";

    /* Template methods */
    private static String getFolderElement(String folderName) {
        return ADD_TO_MY_EXISTING_LIST_OVERLAY.replace("{FOLDER_NAME}", folderName);
    }
    /* Template methods */

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(By.id(TITLE), "Cannot find article on the page", 15);
    }

    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        return titleElement.getAttribute("text");
    }

    public void swipeToFooter() {
        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT), "Cannot find the end of the article", 10);
    }

    public void addArticleToMyList(String folderName) {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open options menu",
                5
        );

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' button",
                5
        );

        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "Cannot find input to set article folder name",
                5
        );

        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                folderName,
                "Cannot enter name of article folder",
                5
        );

        this.waitForElementAndClick(
                By.id(MY_LIST_OK_BUTTON),
                "Cannot tap 'OK' button",
                5
        );
    }

    public void closeArticle() {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot find close article button",
                5
        );
    }

    public void addArticleToMyExistingList(String folderName) {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open options menu",
                5
        );

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                5
        );

        String xpath = getFolderElement(folderName);
        this.waitForElementAndClick(
                By.xpath(xpath),
                "Cannot find existing folder " + folderName,
                5
        );
    }

    public void assertArticleNameIsCorrect(String articleName) {
        this.waitForTitleElement();
        WebElement element = driver.findElement(By.id(TITLE));
        if (!element.getAttribute("text").equals(articleName)) {
            String defaultMessage = "The article title does not equal " + articleName;
            throw new AssertionError(defaultMessage);
        }
    }
}