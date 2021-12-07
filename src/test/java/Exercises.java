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
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;
import java.util.List;

public class Exercises {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/ana/Documents/GitHub/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

    }

    //Чтобы после теста на поворот экрана сам экран всегда оказывался в правильном положении
    // необходимо добавить в аннотацию @After поворот экрана в нужное положение,
    // тогда каждый тест будет завершаться повотором экрана в нужное положение.
    @After
    public void tearDown() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.quit();
    }

    @Test
    public void testCompareTextInSearchField()
    {
        assertElementHasText(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[@text='Search Wikipedia']"),
                "Search Wikipedia",
                "Actual text doesn't match expected",
                5
        );
    }

    @Test
    public void testFindListOfArticlesAndCancelSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "cannot find 'Search Wikipedia' input field",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "cannot find search input field",
                5
        );

        List<WebElement> list = driver.findElements(By.id("org.wikipedia:id/page_list_item_container"));
        Assert.assertTrue ("No articles found",list.size() > 0);


        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "cannot find 'X' button",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "'X' button is still present",
                5
        );
    }

    @Test
    public void assertSearchResultsHasText()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "cannot find 'Search Wikipedia' input field",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "cannot find search input field",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find search results list",
                5
        );

        List<WebElement> search_results_list = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        for (WebElement List_element: search_results_list)
        {
            String list_element_title = List_element.getAttribute("text");
            Assert.assertTrue(
                    "Expected text not found in one or more list elements",
                    list_element_title.contains("Java")
            );
        }
    }

    @Test
    public void saveTwoArticlesToMyList()
    {
        String search_text = "Java";
        String name_of_folder = "Learning programming";

        //tap on the 'Search Wikipedia' input field
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find search Wikipedia input",
                5
        );

        //enter text "Java"
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_text,
                "cannot find search input",
                5
        );

        //tap on the article "Java(Programming language)"
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "cannot find 'Object-oriented programming language' article",
                5
        );

        //waiting for the article to load and the article title to appear
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "cannot find article title",
                15
        );

        //tap on the three dots button
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "cannot find button to open article options",
                5
        );

        //waiting for the menu item 'Add to reading list' to present
        waitForElementPresent(
                By.xpath("//*[@text='Add to reading list']"),
                "cannot find Add to reading list menu item",
                15
        );

        //tap on the 'Add to reading list' menu item
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "cannot click menu option 'Add article to reading list'",
                5
        );

        //tap on the 'GOT IT' button
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "cannot find GOT IT button",
                5
        );

        //clear the text 'My reading list' in the 'Name of this list' input field
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "cannot find input field for set name of article folder",
                5
        );

        //enter the text 'Learning programming' to the 'Name of this list' input field
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "cannot put text into article folder input field",
                5
        );

        //tap on the OK button
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "cannot find OK button",
                5
        );

        //tap on the 'X' button
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "cannot find close button",
                5
        );

        //tap on the 'Search Wikipedia' input field
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find search Wikipedia input",
                5
        );

        //enter text "Java"
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_text,
                "cannot find search input",
                5
        );

        //tap on the article "JavaScript"
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Programming language']"),
                "cannot find 'Object-oriented programming language' article",
                5
        );

        //waiting for the article to load and the article title to appear
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "cannot find article title",
                15
        );

        //tap on the three dots button
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "cannot find button to open article options",
                5
        );

        //waiting for the menu item 'Add to reading list' to present
        waitForElementPresent(
                By.xpath("//*[@text='Add to reading list']"),
                "cannot find Add to reading list menu item",
                15
        );

        //tap on the 'Add to reading list' menu item
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "cannot click menu option 'Add article to reading list'",
                5
        );

        //tap on the 'Learning programming' folder
        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "cannot click menu option 'Add article to reading list'",
                5
        );

        //tap on the 'X' button
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "cannot find close button",
                5
        );

        //tap on the 'My lists' button
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "cannot find My lists navigation button",
                5
        );


        //waiting for the 'Learning programming' folder to present
        waitForElementPresent(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "cannot find Add to reading list menu item",
                15
        );

        //tap on the folder
        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "cannot find Learning programming folder",
                5
        );


        //delete the 'Java (programming language)' article
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "cannot find article name"
        );

        //ensure that another article is still presented
        Assert.assertTrue(
                "JavaScript article is not presented",
                driver.findElement(By.xpath("//*[@text='JavaScript']")).isDisplayed()
        );

        //tap on the article
        waitForElementAndClick(
                By.xpath("//*[@text='JavaScript']"),
                "cannot find JavaScript article",
                5
        );

        //waiting for the 'JavaScript' article to load and the title to present
        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "cannot find article title",
                15
        );

        String actual_title = title_element.getAttribute("text");

        //ensure that actual title matches expected
        Assert.assertEquals(
                "Actual title doesn't match expected",
                "JavaScript",
                actual_title
        );

    }

    @Test
    public void testAssertElementPresent()
    {
        String search_text = "Appium";

        //tap on the 'Search Wikipedia' input field
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find search Wikipedia input",
                5
        );

        //enter text for searching
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_text,
                "cannot find search input",
                5
        );

        //tap on the searching result
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Appius Claudius Caecus']"),
                "cannot find 'Appius Claudius Caecus' article",
                5
        );

        //ensure that title is presented
        Assert.assertTrue(
                "The article title is not presented",
                driver.findElement(By.id("org.wikipedia:id/view_page_title_text")).isDisplayed()
        );
    }

    @Test
    public void testFindListOfArticles()
    {
        String search_text = "Appium";

        //tap on the 'Search Wikipedia' input field
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find search Wikipedia input",
                5
        );

        //enter text for searching
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                search_text,
                "cannot find search input",
                5
        );

        //tap on the searching result
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Appius Claudius Caecus']/../*[@text='Roman politician']/.."),
                "cannot find 'Appius Claudius Caecus' article",
                5
        );
    }





    private boolean assertElementHasText(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.textToBe(by,value));
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message)
    {
        return waitForElementPresent(by, error_message, 5);
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by,error_message,timeoutInSeconds);
        element.clear();
        return element;
    }

    protected void swipeElementToLeft (By by, String error_message)
    {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;
        TouchAction action = new TouchAction(driver);
        action
                .press(PointOption.point(right_x, middle_y))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(600)))
                .moveTo(PointOption.point(left_x, middle_y))
                .release()
                .perform();

    }



}
