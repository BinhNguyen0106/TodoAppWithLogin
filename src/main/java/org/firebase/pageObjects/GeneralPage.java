package org.firebase.pageObjects;

import org.firebase.commons.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GeneralPage {
    private final By _signOutBtn = By.xpath("//button[contains(@class, 'btn-default')]");
    protected WebElement getSignOutBtn(){
        return Constant.driver.findElement(_signOutBtn);
    }
    public LoginPage signOut(){
        getSignOutBtn().click();
        WebDriverWait explicitWait = new WebDriverWait(Constant.driver, Constant.LONG_TIMEOUT);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(_signOutBtn));
        return new LoginPage();
    }
}
