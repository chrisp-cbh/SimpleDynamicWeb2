package uk.co.tpplc.training.webdriver;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

@RunWith(BlockJUnit4ClassRunner.class)
public class PersistentChromeTests {

	private static ChromeDriverService service;
	private WebDriver driver;

	@BeforeClass
	public static void createAndStartService() throws IOException {
		service = new ChromeDriverService.Builder()
				.usingDriverExecutable(
						new File(
								"C:\\workspace\\SimpleDynamicWeb\\lib\\chromedriver.exe"))
				.usingAnyFreePort().build();
		service.start();
	}

	@AfterClass
	public static void createAndStopService() {
		service.stop();
	}

	@Before
	public void createDriver() {
		driver = new RemoteWebDriver(service.getUrl(),
				DesiredCapabilities.chrome());
	}

	@After
	public void quitDriver() {
		driver.quit();
	}

	@Test
	public void findSecretPhraseChallenge() {
		driver.get("http://localhost:8080/SimpleDynamicWeb");
		assertEquals("Hello, can I help you?", driver.getTitle());

		try {
			WebElement challengeDiv = driver.findElement(By
					.id("passphraseChallenge"));
			assertEquals("What is the secret phrase?", challengeDiv.getText());
		} catch (Exception e) {
			fail("could not find element");
		}
		//
		// searchBox.quit();
	}
	
	@Test
	public void testGoogleSearch() {
		driver.get("http://localhost:8080/SimpleDynamicWeb");
		assertEquals("Hello, can I help you?", driver.getTitle());

		try {
			WebElement challengeDiv = driver.findElement(By
					.id("passphraseChallenge"));
			assertEquals("What is the secret phrase?", challengeDiv.getText());
		} catch (Exception e) {
			fail("could not find element");
		}
		//
		// searchBox.quit();
	}
}
