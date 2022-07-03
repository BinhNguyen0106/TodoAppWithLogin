package org.firebase.commons;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Common {
    private WebDriverWait explicitWait;
    private Alert alert;
    private Select select;
    private JavascriptExecutor jsExecutor;
    private Actions action;
    private final Duration longTimeout = Duration.ofSeconds(30);

    public void launchBrowser(){
        System.out.println("Pre-condition - launchBrowser");
        System.setProperty("webdriver.chrome.driver",
                String.format("%s/browserDrivers/chromedriver.exe", Constant.PROJECT_PATH));
        Constant.driver = new ChromeDriver();
    }
    public void openUrl(String url){
        System.out.println("Pre-condition - openUrl: " + url);
        Constant.driver.get(url);
        maximum();
        Constant.driver.manage().timeouts().implicitlyWait(longTimeout);
    }

    public void closeBrowser(){
        Constant.driver.quit();
    }

    public void maximum(){
        Constant.driver.manage().window().maximize();
    }

    public String getPageTitle(){
        return Constant.driver.getTitle();
    }

    public String getCurrentPageUrl(){
        return Constant.driver.getCurrentUrl();
    }

    public String getPageSource(){
        return Constant.driver.getPageSource();
    }

    public void backToPage(){
        Constant.driver.navigate().back();
    }

    public void refreshCurrentPage(){
        Constant.driver.navigate().refresh();
    }

    public void forwardToPage(){
        Constant.driver.navigate().forward();
    }

    public void waitToAlertPresence(){
        explicitWait = new WebDriverWait(Constant.driver, longTimeout);
        explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    public void acceptAlert(){
        alert = Constant.driver.switchTo().alert();
        alert.accept();
    }
    public void cancelAlert() {
        alert = Constant.driver.switchTo().alert();
        alert.dismiss();
    }
    public void sendKeyToAlert(String value){
        alert = Constant.driver.switchTo().alert();
        alert.sendKeys(value);
    }
    public String getTextAlert(){
        alert = Constant.driver.switchTo().alert();
        return alert.getText();
    }

    public void switchToWindowByID(String parentID){
        //chỉ sử dụng trường hợp có 2 cửa sổ, >2 chạy ko đúng
        Set<String> allWindows = Constant.driver.getWindowHandles();
        for(String runWindow: allWindows){
            if(!runWindow.equals(parentID)){
                Constant.driver.switchTo().window(runWindow);
                break;
            }
        }
    }

    public void switchToWindowByTitle(String title){
        Set<String> allWindows = Constant.driver.getWindowHandles();
        for(String runWindows: allWindows){
            Constant.driver.switchTo().window(runWindows);
            String currentWindow = Constant.driver.getTitle();
            if(currentWindow.equals(title)){
                break;
            }
        }
    }

    public void closeAllWindowsWithoutParent(String parentID){
        Set<String> allWindows = Constant.driver.getWindowHandles();
        for(String runWindows: allWindows){
            if(!runWindows.equals(parentID)){
                Constant.driver.switchTo().window(runWindows);
                Constant.driver.close();//đóng tab hiện tại, không đóng toàn bộ browser
            }
        }
        Constant.driver.switchTo().window(parentID);
    }

    public By byXpath(String locator){
        return By.xpath(locator);
    }

    public WebElement findElementByXpath(String locator){
        return Constant.driver.findElement(byXpath(locator));
    }

    public List<WebElement> findElementsByXpath(String locator){
        return Constant.driver.findElements(byXpath(locator));
    }

    public void clickToElement(String locator){
        findElementByXpath(locator).click();
    }

    public void sendKeyToElement(String locator, String value){
        findElementByXpath(locator).clear();
        findElementByXpath(locator).sendKeys(value);
    }

    public void selectItemInDropDown(String locator, String itemValue){
        select = new Select(findElementByXpath(locator));
        select.selectByValue(itemValue);
    }

    public String getFirstSelectedItemInDropDown(String locator){
        select = new Select(findElementByXpath(locator));
        return select.getFirstSelectedOption().getText();
    }

    public boolean isDropDownMultiple(String locator){
        select = new Select(findElementByXpath(locator));
        return select.isMultiple();
    }

    public void selectItemInCustomDropDown(String parentLocator, String childLocator, String expectedItem){
        findElementByXpath(parentLocator);
        sleepInSecond(1);

        explicitWait = new WebDriverWait(Constant.driver, longTimeout);
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byXpath(childLocator)));

        List<WebElement> allItems = findElementsByXpath(childLocator);

        for (WebElement item: allItems){
            if(item.getText().equals(expectedItem)){
                jsExecutor = (JavascriptExecutor) Constant.driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
                sleepInSecond(1);

                item.click();
                sleepInSecond(1);
                break;
            }
        }
    }

    public void sleepInSecond(long timeout){
        try {
            Thread.sleep(timeout * 1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public String getElementAttribute(String locator, String attributeName){
        return findElementByXpath(locator).getAttribute(attributeName);
    }

    public String getElementText(String locator){
        return findElementByXpath(locator).getText();
    }

    public int countElementsSize(String locator){
        return findElementsByXpath(locator).size();
    }

    public void checkToCheckbox(String locator){
        if(!findElementByXpath(locator).isSelected()){
            findElementByXpath(locator).click();
        }
    }

    public void uncheckToCheckbox(String locator){
        if(findElementByXpath(locator).isSelected()){
            findElementByXpath(locator).click();
        }
    }

    public boolean isControlDisplayed(String locator){
        return findElementByXpath(locator).isDisplayed();
    }

    public boolean isControlEnabled(String locator){
        return findElementByXpath(locator).isEnabled();
    }

    public boolean isControlSelected(String locator){
        return findElementByXpath(locator).isSelected();
    }

    public void switchToFrame(String locator){
        Constant.driver.switchTo().frame(findElementByXpath(locator));
    }

    public void switchToDefaultPage(){
        Constant.driver.switchTo().defaultContent();
    }

    public void doubleClickToElement(String locator){
        action = new Actions(Constant.driver);
        action.doubleClick(findElementByXpath(locator)).perform();
    }

    public void rightClickToElement(String locator){
        action = new Actions(Constant.driver);
        action.contextClick(findElementByXpath(locator)).perform();
    }

    public void hoverToElement(String locator){
        action = new Actions(Constant.driver);
        action.moveToElement(findElementByXpath(locator)).perform();
    }

    public void dragAndDropElement(String sourceLocator, String targetLocator){
        action = new Actions(Constant.driver);
        action.dragAndDrop(findElementByXpath(sourceLocator),
                findElementByXpath(targetLocator)).perform();
    }

    public void sendKeyBoardToElement(String locator, Keys key){
        action = new Actions(Constant.driver);
        action.sendKeys(findElementByXpath(locator), key).perform();
    }

    public Object executeForBrowser(String javascript){
        jsExecutor = (JavascriptExecutor) Constant.driver;
        return jsExecutor.executeScript(javascript);
    }

    public boolean verifyTextInInnerText(String textExpected){
        jsExecutor = (JavascriptExecutor) Constant.driver;
        String textActual = (String) jsExecutor.executeScript(
                "return document.documentElement.innerText.match('" + textExpected+ "')[0]");
        System.out.println("Text actual: " + textActual);
        return textActual.equals(textExpected);
    }

    public void scrollToBottomPage(){
        jsExecutor = (JavascriptExecutor) Constant.driver;
        jsExecutor.executeScript("window.scrollBy(0, document.body.scrollHeight)");
    }

    public void navigateToUrlByJS(String url){
        jsExecutor = (JavascriptExecutor) Constant.driver;
        jsExecutor.executeScript("window.location = '" + url + "'");
    }


    public void waitToElementPresence(String locator){
        explicitWait = new WebDriverWait(Constant.driver, longTimeout);
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(byXpath(locator)));
    }

    public void waitToElementVisible(String locator){
        explicitWait = new WebDriverWait(Constant.driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(byXpath(locator)));
    }

    public void waitToElementInvisible(String locator){
        explicitWait = new WebDriverWait(Constant.driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(byXpath(locator)));
    }

    public void waitToElementClickable(String locator){
        explicitWait = new WebDriverWait(Constant.driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(byXpath(locator)));
    }

    public static String switchToNewWindow(){
        String MainWindow = Constant.driver.getWindowHandle();
        System.out.println(MainWindow);

        // Get all new opened window.
        Set<String> windows = Constant.driver.getWindowHandles();

        //Set là một Collection không thể chứa các phần tử trùng lặp.
        //Cách duyệt từng phần tử không trùng lặp trong Collection (Set)
        for(String window : windows){
            System.out.println(window);
            if (!MainWindow.equalsIgnoreCase(window)) {
                //So sánh nếu thằng nào khác thằng Chính (đầu tiên) thì chuyển hướng qua nó mới thao tác được
                //Switch to Child window
                Constant.driver.switchTo().window(window);

                System.out.println("Đã chuyển đến lớp Window con");
                //Constant.driver.close();
            }
        }
        return MainWindow;
    }

    public static void switchBackToOriginWindow(String originWindow){
        Constant.driver.switchTo().window(originWindow);
    }

    public static List<String> generateListTask(Integer numberOfTask){
        List<String> tasks = new ArrayList<>();
        for (int i  = 1; i <= numberOfTask; i++)
        {
            tasks.add(String.format("Task: %s", i));
        }
        return tasks;
    }
}
