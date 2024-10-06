package Framework.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Framework.pageobjects.LandingPage;


public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingpage;
	
	public WebDriver initializeDriver() throws IOException {
	FileInputStream fis= new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\Framework\\resources\\GlobalData.properties");
	Properties prop= new Properties();
	prop.load(fis);
	String browser=prop.getProperty("browser");
	
	if(browser.equalsIgnoreCase("chrome")) {
		driver= new ChromeDriver();
	}
	else if(browser.equalsIgnoreCase("firefox")) {
			driver= new FirefoxDriver();
		
	}
	else if(browser.equalsIgnoreCase("edge")) {
		driver= new EdgeDriver();
	}
	else {
		System.out.println("No driver found");
	}
	
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	return driver;
}
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException {
		driver= initializeDriver();
		landingpage= new LandingPage(driver);
		landingpage.goTo();
		return landingpage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		driver.close();
	}
	
	public List<HashMap<String,String>> getJsonDataToMap(String filepath) throws IOException {
		
		String jsonContent= FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);
		ObjectMapper mapper= new ObjectMapper();
		List<HashMap<String,String>> data=mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){
		});
			return data;
		
	
	}
	
	
}
