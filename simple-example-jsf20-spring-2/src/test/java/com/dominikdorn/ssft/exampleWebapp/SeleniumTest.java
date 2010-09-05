package com.dominikdorn.ssft.exampleWebapp;

import com.thoughtworks.selenium.*;

public class SeleniumTest extends SeleneseTestCase {


    public final String USERNAME_JIMI = "jimi";
    public final String PASSWORD_JIMI = "jimispassword";
    public final String ROLES_JIMI = "ROLE_USER,ROLE_ADMIN";

    public final String USERNAME_BOB = "bob";
    public final String PASSWORD_BOB = "bobspassword";
    public final String ROLES_BOB = "ROLE_USER";

    public final String USERNAME_STEVE = "steve";
    public final String PASSWORD_STEVE = "stevespassword";
    public final String ROLES_STEVE = "ROLE_MODERATOR";

    public final String USERNAME_DANIEL = "daniel";
    public final String PASSWORD_DANIEL = "danielspassword";
    public final String ROLES_DANIEL = "ROLE_VIEWER";




    public void setUp() throws Exception {
        setUp("http://127.0.0.1:9080/", "*firefox");
        selenium.open("/");
        selenium.deleteAllVisibleCookies();
    }



    public void doLogin(String username, String password)
    {
        selenium.click("link=Login");
        selenium.waitForPageToLoad("30000");
        selenium.type("j_username", username);
        selenium.type("j_password", password);
        selenium.click("submit");
        selenium.waitForPageToLoad("30000");

    }
    
    public void doLogout() 
    {
      selenium.click("link=Logout");
      selenium.waitForPageToLoad("30000");
    }

    public boolean isLoggedIn()
    {
        return selenium.isTextPresent("LOGGED_IN:true");
    }

    public boolean isUserLoggedIn(String username)
    {
        return selenium.isTextPresent("LOGGEDIN_USERNAME:"+username);
    }

    public void testSelenium_jimi() throws Exception {
        doLogin(USERNAME_JIMI, PASSWORD_JIMI);
		selenium.open("/example.xhtml");
		verifyTrue(isLoggedIn());
		verifyTrue(isUserLoggedIn(USERNAME_JIMI));
		verifyTrue(selenium.isTextPresent("TAG_IFANYGRANTED_USER_ADMIN:true"));
		verifyTrue(selenium.isTextPresent("TAG_IFALLGRANTED_USER_ADMIN:true"));
		verifyFalse(selenium.isTextPresent("TAG_IFNOTGRANTED_USER_ADMIN: true"));
		verifyFalse(selenium.isTextPresent("TAG_IFANYGRANTED_USER_MODERATOR: true"));
		verifyFalse(selenium.isTextPresent("TAG_IFALLGRANTED_USER_MODERATOR: true"));
		verifyFalse(selenium.isTextPresent("TAG_IFNOTGRANTED_USER_MODERATOR: true"));
		verifyFalse(selenium.isTextPresent("TAG_IFANYGRANTED_MODERATOR_VIEWER: true"));
		verifyFalse(selenium.isTextPresent("TAG_IFALLGRANTED_MODERATOR_VIEWER: true"));
		verifyTrue(selenium.isTextPresent("TAG_IFNOTGRANTED_MODERATOR_VIEWER:true"));
		verifyTrue(selenium.isTextPresent("EL_IFANYGRANTED_USER_ADMIN: true"));
		verifyTrue(selenium.isTextPresent("EL_IFALLGRANTED_USER_ADMIN: true"));
		verifyTrue(selenium.isTextPresent("EL_IFNOTGRANTED_USER_ADMIN: false"));
		verifyTrue(selenium.isTextPresent("EL_IFANYGRANTED_USER_MODERATOR: false"));
		verifyTrue(selenium.isTextPresent("EL_IFALLGRANTED_USER_MODERATOR: false"));
		verifyTrue(selenium.isTextPresent("EL_IFNOTGRANTED_USER_MODERATOR: false"));
		verifyTrue(selenium.isTextPresent("EL_IFANYGRANTED_MODERATOR_VIEWER: false"));
		verifyTrue(selenium.isTextPresent("EL_IFALLGRANTED_MODERATOR_VIEWER: false"));
		verifyTrue(selenium.isTextPresent("EL_IFNOTGRANTED_MODERATOR_VIEWER: true"));
		verifyFalse(selenium.isTextPresent("EL_ISANONYMOUS: true"));
    verifyTrue(selenium.isTextPresent("EL_ISAUTHENTICATED: true"));
	}
    
    public void testSelenium_anonymous() throws Exception 
    {
        doLogout();
        selenium.open("/example.xhtml");
        verifyFalse(isLoggedIn());
        verifyFalse(selenium.isTextPresent("TAG_IFANYGRANTED_USER_ADMIN:true"));
        verifyFalse(selenium.isTextPresent("TAG_IFALLGRANTED_USER_ADMIN:true"));
        verifyTrue(selenium.isTextPresent("TAG_IFNOTGRANTED_USER_ADMIN:true"));
        verifyFalse(selenium.isTextPresent("TAG_IFANYGRANTED_USER_MODERATOR:true"));
        verifyFalse(selenium.isTextPresent("TAG_IFALLGRANTED_USER_MODERATOR:true"));
        verifyTrue(selenium.isTextPresent("TAG_IFNOTGRANTED_USER_MODERATOR:true"));
        verifyFalse(selenium.isTextPresent("TAG_IFANYGRANTED_MODERATOR_VIEWER:true"));
        verifyFalse(selenium.isTextPresent("TAG_IFALLGRANTED_MODERATOR_VIEWER:true"));
        verifyTrue(selenium.isTextPresent("TAG_IFNOTGRANTED_MODERATOR_VIEWER:true"));
        verifyTrue(selenium.isTextPresent("TAG_ISANONYMOUS:true"));
        verifyFalse(selenium.isTextPresent("TAG_ISAUTHENTICATED:true"));
        verifyFalse(selenium.isTextPresent("EL_IFANYGRANTED_USER_ADMIN: true"));
        verifyFalse(selenium.isTextPresent("EL_IFALLGRANTED_USER_ADMIN: true"));
        verifyTrue(selenium.isTextPresent("EL_IFNOTGRANTED_USER_ADMIN: true"));
        verifyFalse(selenium.isTextPresent("EL_IFANYGRANTED_USER_MODERATOR: true"));
        verifyFalse(selenium.isTextPresent("EL_IFALLGRANTED_USER_MODERATOR: true"));
        verifyTrue(selenium.isTextPresent("EL_IFNOTGRANTED_USER_MODERATOR: true"));
        verifyFalse(selenium.isTextPresent("EL_IFANYGRANTED_MODERATOR_VIEWER: true"));
        verifyFalse(selenium.isTextPresent("EL_IFALLGRANTED_MODERATOR_VIEWER: true"));
        verifyTrue(selenium.isTextPresent("EL_IFNOTGRANTED_MODERATOR_VIEWER: true"));
        verifyTrue(selenium.isTextPresent("EL_ISANONYMOUS: true"));
        verifyFalse(selenium.isTextPresent("EL_ISAUTHENTICATED: true"));
    }
}
