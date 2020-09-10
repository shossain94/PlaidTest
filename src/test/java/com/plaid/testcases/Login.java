package com.plaid.testcases;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.plaid.base.TestBase;

public class Login extends TestBase{
	
	@Test
	public static void login() {
	
	boolean plaidImageVerification = isDisplayed(By.xpath(OR.getProperty("IMAGE_VERIFICATION")));
		if(plaidImageVerification == true)	{
	log.debug("Image Verified");
}
	
	}

}
