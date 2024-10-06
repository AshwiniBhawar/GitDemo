package Framework.Tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import Framework.TestComponents.BaseTest;
import Framework.pageobjects.CartPage;
import Framework.pageobjects.CheckoutPage;
import Framework.pageobjects.ConfirmationPage;
import Framework.pageobjects.ProductCatalogue;

public class ErrorValidationTest extends BaseTest{
	@Test(groups= {"ErrorHandling"})
	public void loginErrorValidation() throws IOException{

		ProductCatalogue productCatalogue=landingpage.loginApplication("abc228@gmail.com", "Abc@1234");
		Assert.assertEquals("Incorrect email or password.", landingpage.getErrorMessage());
		
}
	
	@Test
	public void productErrorValidation() throws IOException{
		String countryname="India";
		String productName= "ZARA COAT 3";

		ProductCatalogue productCatalogue=landingpage.loginApplication("anshika@gmail.com", "Iamking@000");
				
		List<WebElement> products=productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage=productCatalogue.goToCartPage();
		
		Boolean match=cartPage.verifyproductDisplay("Zara Coat 33");
		Assert.assertFalse(match);
		
	
	}	
}