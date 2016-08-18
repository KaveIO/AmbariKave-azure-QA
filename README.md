# Ambarikave-azure-QA: automatic provisioning of KaveOnAzure instances via the Azure portal

The purpose of this test suite is to emulate the interaction a user goes through on the Azure portal via the Marketplace wizard for KAVE.
This allows for easy and automated QA of our marketplace solution.

Additonally there is support to also check the status of the argument deployment requests, and delete them afterwards.

This code is not extremely stable, the reasons are that the libraries we use have some glitches and the Azure portal is not super fast and clean.

## How to run

The programs are best run from inside an IDE, e.g. Eclipse, but they can of course be started from a command line, perhaps with a shell wrapper to make it a tool.

There are three marketplace provisioning test classes:

* test - create all the resource group deployments
* check - check the status of the argument deployments
* clean - clean the workspace (delete the resource groups)

and they share a common abstraction where the arguments for the provisioning instances are hardcoded; the arguments for every resource group are the name, the data center and the subscription to use - check the code for the details.

The following [JUnit4](http://junit.org/junit4/) arguments have to be provided:

* `-ea`
* `-Dwebdriver.chrome.driver=src/test/resources/chromedriver.bin` 
* `-DAzureSiteUser=ASK@ME.com -DAzureSitePass=ASKME` 
* `-DSubscription1="ASKME" -DSubscription2="ASKME" -DSubscription3="ASKME"`
* `-DSubscriptionCode1="ASKME" -DSubscriptionCode2="ASKME" -DSubscriptionCode3="ASKME"`

so assertions, the path to the [Selenium](http://docs.seleniumhq.org/) web driver for the browser you test with, your Azure portal account, a few subscription names and hashes.

Please beware that the portal account and subscription codes are sensitive information.

## Future directions

This code can be turned into a real tool at the core of a user interaction health tests system.

Such approach is dual to one where the provisioning cycles are managed from a console using the Azure CLI tool; in that case there are other open points though, e.g. managing the auth tokens to the API.
