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
public class MarketplaceProvisioningTestCleanup extends MarketplaceProvisioningParametrizedRunner {

  /**
   * Instantiates the cleanup runner.
   * 
   * @param num Iteration number, string for convenience
   * @param subscription The Microsoft Azure subscription ID
   * @param dataCenter The target data center, eg West Europe
   * 
   */
  public MarketplaceProvisioningTestCleanup(String num, String subscription,
      String subscriptionCode, String dataCenter) {
    super("test" + num, num, subscription, subscriptionCode, dataCenter,
        String.format(
            "https://portal.azure.com/#resource/subscriptions/%s/resourceGroups/%s/overview",
            subscriptionCode, "test" + num),
        5);
  }

  private void insertResourceGroup(String rg) {
    insertTextBoxValue("3", rg);
  }

  private void clickOk(String num) {
    safeClick(By.xpath(String.format("(//button[@type='button'])[%s]", num)));
  }

  private void clickBin() {
    clickOk("18");
  }

  private void clickDelete() {
    clickOk("25");
  }

  private void deleteResourceGroup() {
    try {
      clickBin();
      insertResourceGroup(resourceGroup);
      clickDelete();
    } catch (TimeoutException te) {
      logger.fine("Cannot delete the RG, most probably it does not exist anymore");
    }
  }

  @Test
  public void cleanAmbariKaveAzureQa() throws Exception {
    deleteResourceGroup();
  }

}
