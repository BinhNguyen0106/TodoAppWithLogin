package org.firebase.commons;

import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class Constant {
    public static WebDriver driver;
    public static String PROJECT_PATH = System.getProperty("user.dir");
    public static final String TEST_URL = "https://todo-list-login.firebaseapp.com/#!/";
    public static final String USERNAME = "BinhNguyen0106";
    public static final String PASSWORD = "Thanhbinh246";
    public static final Duration LONG_TIMEOUT = Duration.ofSeconds(30);

}
