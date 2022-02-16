import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "9.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/alexandra/Desktop/JavaAppiumAutomation2/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );
    }

    @Test
    public void testCancelSearch() {

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                10
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'Close button'",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "'Close button' is still present on the screen",
                10
        );
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language'",
                5
        );

        WebElement titleElement = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String articleTitle = titleElement.getAttribute("text");
        Assert.assertEquals(
                "Unexpected title",
                "Java (programming language)",
                articleTitle);
    }

    @Test

    public void testSearchFieldPlaceholder() {

        assertElementHasText(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[@class='android.widget.TextView']"),
                "Search Wikipedia",
                "Search field placeholder is not 'Search Wikipedia'"
        );
    }

    @Test

    public void testSearchResultsAndCancel() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Kotlin",
                "Cannot find search input",
                5
        );

        assertElementHasText(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Kotlin']"),
                "Kotlin",
                "Cannot find article about 'Kotlin'"
        );

        assertElementHasText(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Kotlin (programming language)']"),
                "Kotlin (programming language)",
                "Cannot find article about 'Kotlin (programming language)'"
        );

        assertElementHasText(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Kotlin-class destroyer']"),
                "Kotlin-class destroyer",
                "Cannot find article about 'Kotlin-class destroyer'"
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find 'Close button'",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Kotlin']"),
                "Article about 'Kotlin' is displayed",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Kotlin (programming language)']"),
                "Article about 'Kotlin (programming language)' is displayed",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@text='Kotlin-class destroyer']"),
                "Article about 'Kotlin-class destroyer' is displayed",
                5
        );

    }

    @Test

    public void testSearchResultsContainSearchInput() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        assertElementContainsText(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container'][1]//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Java"
        );

        assertElementContainsText(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container'][2]//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Java"
        );

        assertElementContainsText(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container'][3]//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Java"
        );

    }

    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Automation for Apps']"),
                "Cannot find 'Automation for Apps'",
                5
        );

        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find the end of the article",
                10);

    }

    @Test
    public void testSaveFirstArticleToMyList() {

        String folderName = "Learning programming";

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Automation for Apps']"),
                "Cannot find 'Automation for Apps'",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open options menu",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' button",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set article folder name",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                folderName,
                "Cannot enter name of article folder",
                5
        );

        waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot tap 'OK' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find close article button",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot navigation button to 'My list'",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + folderName + "']"),
                "Cannot find folder " + folderName,
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Automation for Apps']"),
                "Cannot find added article and swipe it"
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description']//*[@text='Automation for Apps']"),
                "Cannot delete saved article",
                5
        );

    }

    @Test
    public void testAmountOfNotEmptySearch() {

        String search = "Linkin Park Diskography";

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia'",
                10
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search,
                "Cannot find search input",
                5
        );

        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        waitForElementPresent(
                By.xpath(searchResultLocator),
                "Cannot find anything by the request " + search,
                15
        );

        int amountOfSearchResults = getAmountOfElements(
                By.xpath(searchResultLocator)
        );

        Assert.assertTrue("Not enough search result items was found", amountOfSearchResults > 0);

    }

    @Test

    public void testChangeScreenOrientationOnSearchResults() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        String search = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search,
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search,
                15
        );

        String titleBeforeRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String titleAfterRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after screen rotation",
                titleBeforeRotation,
                titleAfterRotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String titleAfterSecondRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title",
                15
        );

        Assert.assertEquals(
                "Article title have been changed after second screen rotation",
                titleBeforeRotation,
                titleAfterSecondRotation
        );
    }

    @Test

    public void testCheckSearchArticleInBackground() {


        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Automation for Apps']"),
                "Cannot find 'Automation for Apps'",
                5
        );

        driver.runAppInBackground(Duration.ofSeconds(3));

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Automation for Apps']"),
                "Cannot find 'Automation for Apps' after returning from background",
                5
        );

    }

    @Test
    public void testSaveTwoArticlesAndDeleteOne() {

        String folderName = "Learning programming";
        String firstArticle = "Automation for Apps";
        String secondArticle = "Kotlin (programming language)";

        // Find and add the 1st article

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + firstArticle + "']"),
                "Cannot find article: " + firstArticle,
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title: " + firstArticle,
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open options menu",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' button",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set article folder name",
                5
        );

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                folderName,
                "Cannot enter name of article folder",
                5
        );

        waitForElementAndClick(
                By.id("android:id/button1"),
                "Cannot tap 'OK' button",
                5
        );

        // Find and add the 2nd article

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/menu_page_search']"),
                "Cannot find 'Search Wikipedia' on toolbar of an open article",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Kotlin",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + secondArticle + "']"),
                "Cannot find article: " + secondArticle,
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title: " + secondArticle,
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open options menu",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );


        waitForElementAndClick(
                By.xpath("//*[@text='" + folderName + "']"),
                "Cannot find and choose folder: " + folderName,
                5
        );

        // Return to the main page

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find close article button",
                5
        );

        // Open folder with articles and delete one

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot navigation button to 'My list'",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + folderName + "']"),
                "Cannot find folder " + folderName,
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='" + firstArticle + "']"),
                "Cannot find first article and swipe it"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='" + firstArticle + "']"),
                "Cannot delete first article",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@text='" + secondArticle + "']"),
                "Cannot find second article",
                5
        );

        // Open saved article and verify the name

        waitForElementAndClick(
                By.xpath("//*[@text='" + secondArticle + "']"),
                "Cannot find article in folder: " + secondArticle,
                5
        );

        String savedArticleTitle = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find article title",
                15
        );

        Assert.assertEquals("Article titles do not match", savedArticleTitle, secondArticle);

    }

    @Test
    public void testElementTitleIsPresent() {

        String article = "Automation for Apps";

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + article + "']"),
                "Cannot find article: " + article,
                5
        );

        Assert.assertTrue(
                "Title is not present",
                assertElementPresent(By.id("org.wikipedia:id/view_page_title_1text")));
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String errorMessage) {

        return waitForElementPresent(by, errorMessage, 5);
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    private boolean assertElementHasText(By by, String expectedText, String errorMessage) {
        waitForElementPresent(by, "Cannot find the element");
        WebDriverWait wait = new WebDriverWait(driver, 1);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.textToBe(by, expectedText));
    }

    private boolean assertElementContainsText(By by, String expectedText) {
        waitForElementPresent(by, "Cannot find the element");
        WebDriverWait wait = new WebDriverWait(driver, 1);
        wait.withMessage(String.format("Search result does not contain %s", expectedText) + "\n");
        return wait.until(ExpectedConditions.attributeContains(by, "text", expectedText));
    }

    protected void swipeUp(int durationOfSwipeInSeconds) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        action.press(PointOption.point(x, startY)).waitAction(WaitOptions.waitOptions(Duration
                .ofSeconds(durationOfSwipeInSeconds))).moveTo(PointOption.point(x, endY)).release().perform();
    }

    protected void swipeUpQuick() {
        swipeUp(2);
    }

    protected void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {

        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by, "Cannot find element by swiping up \n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            alreadySwiped++;
        }
    }

    protected void swipeElementToLeft (By by, String errorMessage) {
        WebElement element = waitForElementPresent(by, errorMessage, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();

        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();

        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);

        action
                .press(PointOption.point(rightX, middleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(leftX, middleY))
                .release()
                .perform();
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    private boolean assertElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
