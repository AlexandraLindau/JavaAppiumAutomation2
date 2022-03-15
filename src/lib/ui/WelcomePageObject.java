package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class WelcomePageObject extends MainPageObject {

    private static final String
    STEP_LEARN_MORE_LINK = "//*[@value='Learn more about Wikipedia']",
    STEP_NEW_WAYS_TO_EXPLORE = "//*[@value='New ways to explore']",
    STEP_SEARCH_IN_300_LANGUAGES = "//*[@value='Search in nearly 300 languages']",
    STEP_LEARN_MORE_ABOUT_DATA_COLLECTION_LINK = "//*[@value='Learn more about data collected']",
    NEXT_LINK = "//*[@value='Next']/..",
    GET_STARTED_BUTTON = "//*[@value='Get started']";



    public WelcomePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreLink() {
        this.waitForElementPresent(By.xpath(STEP_LEARN_MORE_LINK), "Cannot find 'Learn more about Wikipedia' link", 10);
    }

    public void waitForNewWaysToExplore() {
        this.waitForElementPresent(By.xpath(STEP_NEW_WAYS_TO_EXPLORE), "Cannot find 'New ways to explore' text", 10);
    }

    public void waitForSearchInNearly300Languages() {
        this.waitForElementPresent(By.xpath(STEP_SEARCH_IN_300_LANGUAGES), "Cannot find 'Search in nearly 300 languages' text", 10);
    }

    public void waitForLearnMoreAboutDataCollected() {
        this.waitForElementPresent(By.xpath(STEP_LEARN_MORE_ABOUT_DATA_COLLECTION_LINK), "Cannot find 'Learn more about data collected' link", 10);
    }

    public void clickNextButton() {
        this.waitForElementAndClick(By.xpath(NEXT_LINK), "Cannot find and click 'Next' button", 10);
    }

    public void clickGetStartedButton() {
        this.waitForElementAndClick(By.xpath(GET_STARTED_BUTTON), "Cannot find and click 'Get started' button", 10);
    }
}
