package com.kpmg.koa.qa.marketplace;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class MarketplaceProvisioningTest {
  private WebDriver driver;
  private String baseUrl;
  private StringBuffer verificationErrors = new StringBuffer();

  private String user = System.getProperty("AzureSiteUser");
  private String pass = System.getProperty("AzureSitePass");

  private void initialize() {
    driver = new FirefoxDriver();
    baseUrl = "https://portal.azure.com/#create/kave.io-previewkave-analytics-platform";
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  private void login() {
    driver.get(baseUrl);

    driver.findElement(By.id("cred_userid_inputtext")).clear();
    driver.findElement(By.id("cred_userid_inputtext")).sendKeys(user);
    driver.findElement(By.id("cred_keep_me_signed_in_checkbox")).click();
    driver.findElement(By.id("mso_account_tile_link")).click();


    driver.findElement(By.id("i0116")).clear();
    driver.findElement(By.id("i0116")).sendKeys(user);
    driver.findElement(By.id("i0118")).clear();
    driver.findElement(By.id("i0118")).sendKeys(pass);

    driver.findElement(By.id("idSIButton9")).click();
  }

  /**
   * Sets up the web driver for Firefox configuring a proper timeout and attempts the login on
   * Azure.
   */
  @Before
  public void setUp() throws Exception {
    initialize();
    login();
  }

  @Test
  public void testAmbariKaveAzureQa() throws Exception {
    // driver.get(baseUrl + "/#create/kave.io-previewkave-analytics-platform");
    // driver.findElement(By.xpath("(//button[@type='button'])[20]")).click();



    // driver.findElement(By.name("__azc-textBox3")).clear();
    // driver.findElement(By.name("__azc-textBox3")).sendKeys("kaveadmin");
    // driver.findElement(By.xpath("//input[@type='password']")).clear();
    // driver.findElement(By.xpath("//input[@type='password']")).sendKeys("KavePassword01");
    // driver.findElement(By.xpath("(//input[@type='password'])[3]")).clear();
    // driver.findElement(By.xpath("(//input[@type='password'])[3]")).sendKeys("KavePassword01");
    // driver.findElement(By.name("__azc-textBox4")).clear();
    // driver.findElement(By.name("__azc-textBox4")).sendKeys("selenium-prova-xxx");
    // driver.findElement(By.xpath("(//button[@type='button'])[34]")).click();
    // driver.findElement(By.id("azc-selector-guid-0ac385c8-bddb-4582-a141-034bc81e62c3-value"))
    // .click();
    // driver.findElement(By.name("__azc-textBox4")).click();
    // driver.findElement(By.name("__azc-textBox4")).click();
    // driver.findElement(By.name("__azc-textBox4")).clear();
    // driver.findElement(By.name("__azc-textBox4")).sendKeys("sasasssdsdsd");
    // driver.findElement(By.xpath("(//button[@type='button'])[39]")).click();
    // driver.findElement(By.id("azc-selector-guid-0ac385c8-bddb-4582-a141-034bc81e62c7-value"))
    // .click();
    // driver.findElement(By.name("__azc-textBox5")).clear();
    // driver.findElement(By.name("__azc-textBox5")).sendKeys("cccddrgffddhhhggccx");
    // driver.findElement(By.xpath("(//button[@type='button'])[39]")).click();
    // driver.findElement(By.xpath("(//button[@type='button'])[34]")).click();
    // driver.findElement(By.name("__azc-textBox6")).clear();
    // driver.findElement(By.name("__azc-textBox6")).sendKeys("somedns");
    // driver.findElement(By.id("azc-selector-guid-0ac385c8-bddb-4582-a141-034bc81e6357-value"))
    // .click();
    // driver.findElement(By.name("__azc-textBox7")).clear();
    // driver.findElement(By.name("__azc-textBox7")).sendKeys("myipdddaaaw");
    // driver.findElement(By.xpath("(//button[@type='button'])[44]")).click();
    // driver.findElement(By.xpath("(//button[@type='button'])[34]")).click();
    // driver.findElement(By.xpath("(//button[@type='button'])[34]")).click();
    // driver.findElement(By.xpath("(//button[@type='button'])[34]")).click();
  }

  /**
   * Wrap-up hook, we close the closable; this should kill the browser too.
   * 
   * @throws Exception In case something goes (really) wrong when closing down.
   */
  @After
  public void tearDown() throws Exception {
    // driver.close();
    // driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

}
