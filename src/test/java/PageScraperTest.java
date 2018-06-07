import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import sample.ImageScraper;

import java.io.File;

import static sample.HelperClass.numberCeiling;


/**
 * Created by Królik on 23.10.2016.
 */
public class PageScraperTest {

    private WebDriver setDriver(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Krolik\\IdeaProjects\\InstaScrapperGUI\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.instagram.com/instagram");
        return driver;
    }
    @Test
    public void testImageCounter(){

        WebDriver testDriver = setDriver();
        ImageScraper testScraper = new ImageScraper(testDriver);
        double expectImageNumber = 5273;

        Assert.assertEquals(expectImageNumber, testScraper.imageCounter(),0.1);
        testDriver.quit();
    }
    @Test
    public void testNumberCeiling(){
        WebDriver testDriver = setDriver();

        ImageScraper testScraper = new ImageScraper(testDriver);
        int expectedNumber = 1757;

        Assert.assertEquals(expectedNumber, numberCeiling(testScraper.imageCounter()/3));
        testDriver.quit();
    }
}
