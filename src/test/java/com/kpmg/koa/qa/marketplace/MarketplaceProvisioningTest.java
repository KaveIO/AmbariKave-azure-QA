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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

@RunWith(Parameterized.class)
public class MarketplaceProvisioningTest extends MarketplaceProvisioningParametrizedRunner {

  /**
   * Instantiates the deployment tests runner.
   * 
   * @param num Iteration number, string for convenience
   * @param subscription The Microsoft Azure subscription ID
   * @param dataCenter The target data center, eg West Europe
   * 
   */
  public MarketplaceProvisioningTest(String num, String subscription, String subscriptionCode,
      String dataCenter) {
    super("test" + num, num, subscription, subscriptionCode, dataCenter,
        "https://portal.azure.com/#create/kave.iokave-analytics-platform", 120);
  }

  private void clickCreate() {
    clickOk("20");
  }

  private void selectEditableComboValue(String num, String value) {
    insertFieldValue(By.name(String.format("__azc-editableCombo%s", num)), value);
  }

  private void insertKaveAdmin(String admin) {
    insertTextBoxValue("2", admin);
  }

  private void insertKaveAdminPass(String pass) {
    insertFieldValue(By.xpath("//input[@type='password']"), pass);
    insertFieldValue(By.xpath("(//input[@type='password'])[3]"), pass);
  }

  private void selectSubscription(String subscription) {
    selectEditableComboValue("0", subscription);
  }

  private void insertResourceGroup(String rg) {
    insertTextBoxValue("3", rg);
  }

  private void selectDataCenter(String dc) {
    selectEditableComboValue("1", dc);
  }

  private void clickOk(String num) {
    safeClick(By.xpath(String.format("(//button[@type='button'])[%s]", num)));
  }

  private void configure(String num) {
    click(By.xpath(String.format("(//*[starts-with(@id,'azc-selector-guid')])[%s]", num)));
  }

  private void configureStorageAccount() {
    configure("1");
  }

  private void insertStorageAccountName(String name) {
    insertTextBoxValue("4", name);
  }

  private void configurePremiumStorageAccount() {
    configure("4");
  }

  private void insertPremiumStorageAccountName(String name) {
    insertTextBoxValue("5", name);
  }

  private void configurePublicIp() {
    configure("1");
  }

  private void insertIpName(String name) {
    insertTextBoxValue("7", name);
  }

  private void insertDnsPrefix(String prefix) {
    insertTextBoxValue("6", prefix);
  }

  private void configureBasics() {
    insertKaveAdmin(kaveAdmin);
    insertKaveAdminPass(kavePass);
    selectSubscription(subscription);
    insertResourceGroup(resourceGroup);
    selectDataCenter(dataCenter);
    clickOk("29");
  }

  private void configureStorage() {
    configureStorageAccount();
    insertStorageAccountName(storageAccountName);
    clickOk("34");
    configurePremiumStorageAccount();
    insertPremiumStorageAccountName(premiumStorageAccountName);
    clickOk("34");
    clickOk("29");
  }

  private void configureDnsNameLabel() {
    configurePublicIp();
    insertIpName(ipName);
    clickOk("39");
    insertDnsPrefix(dnsPrefix);
    clickOk("29");
  }

  private void configureVirtualMachines() {
    clickOk("29");
  }

  private void viewSummary() {
    try {
      clickOk("30");
    } catch (TimeoutException te) {
      clickOk("29");
    }
  }

  private void buy() {
    clickOk("29");
  }

  @Test
  public void testAmbariKaveAzureQa() throws Exception {
    clickCreate();
    configureBasics();
    configureStorage();
    configureDnsNameLabel();
    configureVirtualMachines();
    viewSummary();
    buy();
  }

}
