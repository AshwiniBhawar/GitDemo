package Framework.Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneLogin {
	
	public static void main(String[] args) {
	String countryname="India";
	String productname= "ZARA COAT 3";
	//WebDriverManager.chromedriver().setup();
	//System.setProperty("webdriver.chrome.driver", "C:\\Users\\ashwi\\Downloads\\chromedriver-win64\\chromedriver.exe");
	/*ChromeOptions cop= new ChromeOptions();
	cop.addArguments("--remote-allow-origins=*");*/
	WebDriver driver= new ChromeDriver();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	driver.get("https://rahulshettyacademy.com/client");
	driver.manage().window().maximize();
	driver.findElement(By.id("userEmail")).sendKeys("abc28@gmail.com");
	driver.findElement(By.id("userPassword")).sendKeys("Abc@1234");
	driver.findElement(By.id("login")).click();
	
	WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
	List<WebElement> products=driver.findElements(By.cssSelector(".mb-3"));
	WebElement prod1=products.stream().filter(product-> 
	product.findElement(By.cssSelector("b")).getText().equals(productname)).findFirst().orElse(null);
	prod1.findElement(By.cssSelector(".card-body button:last-of-type")).click();
	
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
	wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
	
	driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
	
	List<WebElement> cartproducts=driver.findElements(By.cssSelector(".cartSection h3"));
	Boolean match=cartproducts.stream().anyMatch(cartproduct-> cartproduct.getText().equalsIgnoreCase(productname));
	Assert.assertTrue(match);
	
	driver.findElement(By.cssSelector(".totalRow button")).click();
	
	//driver.findElement(By.cssSelector(".user__address div input")).sendKeys(countryname);
	Actions a= new Actions(driver);
	a.sendKeys(driver.findElement(By.cssSelector(".user__address div input")), countryname).build().perform();
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
	driver.findElement(By.cssSelector(".action__submit")).click();
	String confirmmessage=driver.findElement(By.cssSelector(".hero-primary")).getText();
	Assert.assertTrue(confirmmessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	
	
	}
}
