package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class IOSSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@value='Search Wikipedia']";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeStaticText[@name='{SUBSTRING}']";
        SEARCH_RESULT_BY_INDEX_TPL = "xpath://*[@type='XCUIElementTypeCollectionView' and @index='0']//*[@type='XCUIElementTypeCell' and @index='{SEARCH_RESULT_INDEX}']";
        SEARCH_RESULT_BY_SUBSTRING_IN_TITLE_AND_DESCRIPTION_TPL = "xpath://*[XCUIElementTypeStaticText[@value='{SUBSTRING_1}'] and XCUIElementTypeStaticText[@value='{Programming SUBSTRING_2}']]";
        SEARCH_RESULT_ELEMENT = "xpath://*[@type='XCUIElementTypeCollectionView' and @index='0']//*[@type='XCUIElementTypeCell']";
        SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
    }

    public IOSSearchPageObject(AppiumDriver driver) {
        super(driver);
    }
}
