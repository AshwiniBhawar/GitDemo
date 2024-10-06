package Framework.Tests;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Framework.AbstractComponents.AbstractComponent;
import Framework.TestComponents.BaseTest;
import Framework.pageobjects.CartPage;
import Framework.pageobjects.CheckoutPage;
import Framework.pageobjects.ConfirmationPage;
import Framework.pageobjects.LandingPage;
import Framework.pageobjects.OrderPage;
import Framework.pageobjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest{
		
	String countryname="India";
	String productName= "ZARA COAT 3";
	
	@Test(dataProvider="getData", groups="Purchase")
	public void submitOrders(HashMap<String,String> input) throws IOException{
		

		ProductCatalogue productCatalogue=landingpage.loginApplication(input.get("email"), input.get("pass"));
		
		
		List<WebElement> products=productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("productName"));
		CartPage cartPage=productCatalogue.goToCartPage();
		
		Boolean match=cartPage.verifyproductDisplay(input.get("productName"));
		Assert.assertTrue(match);
		CheckoutPage checkoutpage=cartPage.goToCheckout();
		checkoutpage.selectCountry("India");
		ConfirmationPage confirmationpage=checkoutpage.submitOrder();
		
		String confirmmessage=confirmationpage.getConfirmationMessage();
		Assert.assertTrue(confirmmessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		System.out.println("Test pass");
	
	}	
	
	@Test(dependsOnMethods="submitOrders")
	public void OrderHistoryTest() {
		
	ProductCatalogue productCatalogue=landingpage.loginApplication("abc28@gmail.com", "Abc@1234");
	OrderPage orderpage=productCatalogue.goToOrderPage();
	Assert.assertTrue(orderpage.verifyOrderDisplay(productName));
	
	
	}
	
	@DataProvider
	public Object[][] getData() throws IOException {
		List<HashMap<String, String>> data= getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//Framework//data//PurchaseOrder.json");
 		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
	public String getScreenshot(String testCaseName) throws IOException {

		TakesScreenshot ts= (TakesScreenshot)driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		File des= new File(System.getProperty("user.dir")+ "//reports//"+ testCaseName + ".png");
		FileUtils.copyFile(src, des);
		return System.getProperty("user.dir")+ "//reports//"+ testCaseName + ".png";
		
	}
	
/*	@DataProvider
	public Object[][] getData() {
		HashMap<String,String> map= new HashMap<String,String>();
		map.put("email", "anshika@gmail.com");
		map.put("pass", "Iamking@000");
		map.put("productName", "ZARA COAT 3");
		
		HashMap<String,String> map1= new HashMap<String,String>();
		map1.put("email", "abc28@gmail.com");
		map1.put("pass", "Abc@1234");
		map1.put("productName", "ADIDAS ORIGINAL");
		
 		return new Object[][] {{map},{map1}};
	}*/
		
/*public Object[][] getData() {
				
 		return new Object[][] {{"anshika@gmail.com","Iamking@000","ZARA COAT 3"},{"abc28@gmail.com","Abc@1234","ADIDAS ORIGINAL"}};
	} */
	 
	}
	


