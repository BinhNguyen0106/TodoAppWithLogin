import org.firebase.commons.Common;
import org.firebase.commons.Constant;
import org.firebase.dataObjects.LoginType;
import org.firebase.pageObjects.GeneralPage;
import org.firebase.pageObjects.LoginPage;
import org.firebase.pageObjects.TodoListPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TotoListTest {
    String testURL = "https://todo-list-login.firebaseapp.com";
    Common common = new Common();
    LoginPage loginPage = new LoginPage();

    @BeforeTest
    public void BeforeTest(){
        System.out.println("TotoListTest - BeforeTest: Step 01");
        common.launchBrowser();
        System.out.println("TotoListTest - BeforeTest: Step 02");
        common.openUrl(testURL);
    }

    @Test
    public void testLogin(){
        System.out.println("TotoListTest - testLogin");
        List<String> listTasks = Common.generateListTask(10);

        loginPage.login(Constant.USERNAME, Constant.PASSWORD, LoginType.GITHUB)
                .addList(listTasks)
                .signOut()
                .login(Constant.USERNAME, Constant.PASSWORD, LoginType.SECOND)
                .removeSpecificTasks(listTasks, 5, 10)
                .signOut();
    }

    @AfterTest
    public void AfterTest(){
        //common.closeBrowser();
    }

}
