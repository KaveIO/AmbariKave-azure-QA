// Copyright 2016 KPMG Advisory N.V. (unless otherwise stated)
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.kpmg.koa.qa.marketplace;

import static org.junit.Assert.fail;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public abstract class MarketplaceProvisioningParametrizedRunner {

  protected final String kaveAdmin = "kaveadmin";
  protected final String kavePass = "KavePassword01";
  protected String resourceGroup;
  protected String dataCenter;
  protected String subscription;
  protected String subscriptionCode;
  protected String storageAccountName = "deplkave%s";
  protected String premiumStorageAccountName = "pdeplkave%s";
  protected String ipName = "kaveip";
  protected String dnsPrefix = "depltestkave%s";

  protected Logger logger = Logger.getLogger("test");

  protected WebDriver driver;
  protected WebDriverWait driverWait;
  protected String baseUrl;
  protected StringBuffer verificationErrors = new StringBuffer();

  protected int timeout;
  protected int clickHits = 300;

  protected String user = System.getProperty("AzureSiteUser");
  protected String pass = System.getProperty("AzureSitePass");

  /**
   * Constructor for the marketplace provisioning test runner.
   * 
   * @param num The number of the test iteration
   * @param dataCenter The data center where the resource group will be created
   */
  public MarketplaceProvisioningParametrizedRunner(String resourceGroup, String num,
      String subscription, String subscriptionCode, String dataCenter, String baseUrl,
      int timeout) {
    this.resourceGroup = resourceGroup;
    this.dataCenter = dataCenter;
    this.subscription = subscription;
    this.subscriptionCode = subscriptionCode;
    this.baseUrl = baseUrl;
    this.timeout = timeout;
    storageAccountName = String.format(storageAccountName, num);
    premiumStorageAccountName = String.format(premiumStorageAccountName, num);
    dnsPrefix = String.format(dnsPrefix, num);
  }

  protected void pause(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException ie) {
      ie.printStackTrace();
    }
  }

  protected void open(String url) {
    driver.get(url);
  }

  protected void safeClick(By element) {
    driverWait.until(ExpectedConditions.visibilityOfElementLocated(element));
    pause(2000);
    try {
      for (int i = 0; i < clickHits; i++) {
        click(element);
      }
    } catch (WebDriverException wde) {
      logger.fine("Oeps, too many clicks!");
    } finally {
      logger.fine("Element should have been clicked by now, moving forward...");
    }
  }

  protected void click(By element) {
    driver.findElement(element).click();
  }

  protected void insertFieldValue(By element, String value) {
    pause(500);
    driver.findElement(element).clear();
    pause(500);
    driver.findElement(element).sendKeys(value);
  }

  protected void insertTextBoxValue(String num, String value) {
    insertFieldValue(By.name(String.format("__azc-textBox%s", num)), value);
  }

  private void initialize() {
    driver = new ChromeDriver();
    driverWait = new WebDriverWait(driver, timeout);
    driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
  }

  private void login() {
    open(baseUrl);
    insertFieldValue(By.id("cred_userid_inputtext"), user);
    click(By.id("cred_keep_me_signed_in_checkbox"));
    click(By.id("mso_account_tile_link"));
    insertFieldValue(By.id("i0116"), user);
    insertFieldValue(By.id("i0118"), pass);
    click(By.id("idSIButton9"));
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

  /**
   * JUnit4 handler method to provide the test run iteration parameters. At every iteration this
   * class is instantiated with the contents of the list.
   * 
   * @return An array of lists containing the test run parameters for each iteration.
   */
  @Parameters
  public static Collection<Object[]> generateData() {
    String subscription1 = System.getProperty("Subscription1");
    String subscription2 = System.getProperty("Subscription2");
    String subscription3 = System.getProperty("Subscription3");
    String subscriptionCode1 = System.getProperty("SubscriptionCode1");
    String subscriptionCode2 = System.getProperty("SubscriptionCode2");
    String subscriptionCode3 = System.getProperty("SubscriptionCode3");
    // Test cases for which the Azure wizard gives issues are commented out:
    // West US: the wizard prompts to confirm the data node size
    // East Asia: there are no matching data nodes VM sizes on this datacenter (known issue)
    @SuppressWarnings("unused")
    String[][] batch1 = {{"1", subscription1, subscriptionCode1, "Central US"},
        {"2", subscription1, subscriptionCode1, "East US"},
        {"3", subscription1, subscriptionCode1, "East US 2"},
        {"4", subscription1, subscriptionCode1, "South Central US"},
        /* {"5", subscription1, subscriptionCode1, "West US"}, */ {"6", subscription1,
            subscriptionCode1, "North Europe"},
        {"7", subscription1, subscriptionCode1, "West Europe"},
        {"8", subscription1, subscriptionCode1, "Southeast Asia"},
        {"9", subscription1, subscriptionCode1, "Japan West"},
        {"10", subscription1, subscriptionCode1, "Japan East"}};
    @SuppressWarnings("unused")
    String[][] batch2 = {{"11", subscription2, subscriptionCode2, "Canada Central"},
        {"12", subscription2, subscriptionCode2, "Canada East"},
        /* {"13", subscription2, subscriptionCode2, "East Asia"}, */ {"14", subscription2,
            subscriptionCode2, "Central US"},
        {"15", subscription2, subscriptionCode2, "East US"},
        {"16", subscription2, subscriptionCode2, "East US 2"},
        {"17", subscription2, subscriptionCode2,
            "South Central US"}, /* {"18", subscription2, subscriptionCode2, "West US"}, */
        {"19", subscription2, subscriptionCode2, "North Europe"},
        {"20", subscription2, subscriptionCode2, "West Europe"}};
    @SuppressWarnings("unused")
    String[][] batch3 = {{"21", subscription3, subscriptionCode3, "Southeast Asia"},
        {"22", subscription3, subscriptionCode3, "Japan West"},
        {"23", subscription3, subscriptionCode3, "Japan East"},
        {"24", subscription3, subscriptionCode3, "Central US"},
        {"25", subscription3, subscriptionCode3, "East US"},
        {"26", subscription3, subscriptionCode3, "East US 2"},
        {"27", subscription3, subscriptionCode3,
            "South Central US"}, /* {"28", subscription3, subscriptionCode3, "West US"}, */
        {"29", subscription3, subscriptionCode3, "North Europe"},
        {"30", subscription3, subscriptionCode3, "West Europe"}};
    String[][] batchtest = {{"13", subscription1, subscriptionCode1, "West US"}};
    String[][] batch = ArrayUtils.addAll(ArrayUtils.addAll(batch1, batch2), batch3);
    return Arrays.asList((Object[][]) batchtest);
  }

  /**
   * Wrap-up hook. The close method closes the test browser window. Don't rush, otherwise there is
   * not enough time to send the RG deploy to Azure.
   */
  @After
  public void tearDown() {
    pause(30000);
    driver.close();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

}
