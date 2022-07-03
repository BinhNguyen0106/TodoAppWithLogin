package org.firebase.pageObjects;

import org.firebase.commons.Common;
import org.firebase.commons.Constant;
import org.firebase.dataObjects.LoginType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    //Locators
    private final By _loginGitHubBtn = By
            .xpath("//a[contains(@class, 'btn-github')]");
    private final By _gitHubUserNameTxt = By.id("login_field");
    private final By _gitHubPwdTxt = By.id("password");
    private final By _gitHubSignInBtn = By.xpath("//input[@name = 'commit']");
    private final By _authorizeBtn = By.name("authorize");
    //Elements
    protected WebElement getLoginGitHubBtn(){
        return Constant.driver.findElement(_loginGitHubBtn);
    }
    protected WebElement getGitHubUserNameTxt(){ return Constant.driver.findElement(_gitHubUserNameTxt);}
    protected WebElement getGitHubPasswordTxt(){ return Constant.driver.findElement(_gitHubPwdTxt);}
    protected WebElement getGitHubSignInBtn(){return Constant.driver.findElement(_gitHubSignInBtn);}
    protected WebElement getAuthorizeBtn(){return Constant.driver.findElement(_authorizeBtn);}
    //Methods
    public void loginWithGitHub(String userName, String password){
        getLoginGitHubBtn().click();

        String mainWindow = Common.switchToNewWindow();
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        getGitHubUserNameTxt().sendKeys(userName);
        getGitHubPasswordTxt().sendKeys(password);
        getGitHubSignInBtn().click();
        //authorize();
        Common.switchBackToOriginWindow(mainWindow);

    }

    public void loginSecondTimes(){
        getLoginGitHubBtn().click();

        String mainWindow = Common.switchToNewWindow();
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //authorize();
        Common.switchBackToOriginWindow(mainWindow);

    }

    public void authorize(){
        if(getAuthorizeBtn().isDisplayed()){
            getAuthorizeBtn().click();
        }
    }

    public TodoListPage login(String userName, String password, LoginType loginType){
        switch (loginType)
        {
            case GITHUB:
                loginWithGitHub(userName, password);
                break;
            case SECOND:
                loginSecondTimes();
                break;
            default:
        }

        return new TodoListPage();
    }

}
