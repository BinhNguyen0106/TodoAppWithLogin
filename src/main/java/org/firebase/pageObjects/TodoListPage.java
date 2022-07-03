package org.firebase.pageObjects;

import org.firebase.commons.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TodoListPage {
    private final By _listTxt = By.xpath("//input[@ng-model = 'home.list']");
    private final By _addListBtn = By.xpath("//button[contains(@class, 'task-btn')]");
    private final By _removeListBtn = By.xpath("//button[contains(@class, 'remove')]");
    private final By _listTask = By.xpath("//a[contains(text(), 'List')]");
    //Elements
    protected WebElement getListTxt(){
        return Constant.driver.findElement(_listTxt);
    }
    protected WebElement getAddListBtn(){
        return Constant.driver.findElement(_addListBtn);
    }
    protected List<WebElement> getRemoveListBtn(){
        return Constant.driver.findElements(_removeListBtn);
    }
    protected List<WebElement> getListTask(){
        return Constant.driver.findElements(_listTask);
    }
    public GeneralPage addList(List<String> listTask){
        System.out.println("TodoListPage - addList");
        List<WebElement> removeListTask = getRemoveListBtn();
        if (removeListTask == null){
            addListTask(listTask);
        }else {
            System.out.println("TodoListPage - Remove existing task before add new task");
            removeListTask(removeListTask);
            addListTask(listTask);
        }

        return new GeneralPage();
    }
    public void removeListTask(List<WebElement> removeListTask){
        for (int i = 0; i < removeListTask.size(); i++){
            removeListTask.get(i).click();
        }
    }
    public void addListTask(List<String> listTask){
        for (int i = 0; i < listTask.size(); i++){
            String task = listTask.get(i);
            System.out.println("TodoListPage - Tast title: " + task);
            getListTxt().sendKeys(task);
            getAddListBtn().click();
        }
    }

    public GeneralPage removeSpecificTasks(List<String> listTask, Integer start, Integer end){
        List<WebElement> removeListTask = getRemoveListBtn();
        for (int i = start - 1 ; i <= end; i++)
        {
            System.out.println("TodoListPage - removeSpecificTasks - Tast title: " + removeListTask.get(i).getText());
            removeListTask.get(i).click();
        }
        return new GeneralPage();
    }



}
