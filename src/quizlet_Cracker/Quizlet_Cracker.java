package quizlet_Cracker;

import java.awt.AWTException;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Quizlet_Cracker {
	static Scanner mainSc = new Scanner(System.in);
	public static void main(String[] args) throws InterruptedException, AWTException {
		// TODO Auto-generated method stub
		Quizlet_Cracker qc = new Quizlet_Cracker();
		System.setProperty("webdriver.chrome.driver", ".\\api\\driver\\chromedriver.exe");
		ChromeDriver driver = qc.log_in();
		
		qc.mainPageAction(driver);
		
	}
	public ChromeDriver log_in() throws InterruptedException {
		Apps apps = new Apps();
		Quizlet_Cracker qc = new Quizlet_Cracker();
		
		ChromeDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.navigate().to("https://quizlet.com/class/13034261/");
		driver.manage().window().setSize(new Dimension(1000,1080));
		
		WebElement loginButton =driver.findElement(By.className("SiteHeader-signInBtn"));
		loginButton.click();
		
		WebElement ongoingUserName = driver.findElement(By.xpath("/html/body/div[6]/div/div[2]/form/div/div/label[1]/div/input"));
		WebElement ongoingPassword = driver.findElement(By.xpath("/html/body/div[6]/div/div[2]/form/div/div/label[2]/div[1]/input"));
		
		while(true) {
			
			System.out.print("UserName: ");
			String uName = mainSc.nextLine();
			String password = qc.readPassword();
			
			
			
			ongoingUserName.sendKeys(uName);
			ongoingPassword.sendKeys(password);
			WebElement log_inButton = driver.findElement(By.xpath("/html/body/div[6]/div/div[2]/form/button"));
			log_inButton.submit();
			//TimeUnit.SECONDS.sleep(2);
			

			if(apps.isElementPresentCssClass(driver,"SiteHeader-username")) {
				System.out.println("Quizlet Username "+uName);
				System.out.println("login success\n\n");//login success
				break;
			}
			System.out.println("\nWrong Username or Password, try again please");
			//ongoingUserName.clear();
			//ongoingPassword.clear();;
			ongoingUserName.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
			ongoingPassword.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
			
		}
		/*
		Select classify = new Select(driver.findElement(By.className("UIDropdown-select")));
		classify.selectByVisibleText("Alphabetical");
		*/
		return driver;
	}
	public void mainPageAction(ChromeDriver driver) throws InterruptedException, AWTException {
		int lastInput = 0;
		loadingPage(driver);
		
		
		while(true) {
			System.out.println("\n\nEnter the number below to navigate to the section you want, or 0 to quit\n");
			List<WebElement> totalPosted = driver.findElements(By.className("DashboardListItem"));
			List<WebElement> section = driver.findElements(By.cssSelector("div.DashboardFeedGroup"));
			String[] sets = new String[totalPosted.size()];
			WebElement[] urls = new WebElement[totalPosted.size()];
			int setsCounter = 0;
			int urlsCounter = 0;
			int outputCounter = 1;
			for(int i=1;i<=section.size();i++) {
				List<WebElement> innerSection = driver.findElements(By.cssSelector("div.DashboardFeedGroup:nth-child("+i+") div.DashboardListItem"));
				
				for(int j=2;j<=innerSection.size()+1;j++) {
					sets[setsCounter] = driver.findElement(By.cssSelector("div.DashboardFeedGroup:nth-child("+i+") div.DashboardListItem:nth-child("+j+") span.SetPreview-cardHeaderTitle")).getText();
					urls[urlsCounter] = driver.findElement(By.cssSelector("#DashboardPageTarget > div > section.DashboardLayout-main > div > div.DashboardPage-inner > div.DashboardPage-main > div > div > div > div.UIDiv.DashboardFeed > div:nth-child("+i+") > div:nth-child("+j+") > div > div > div > div > div > div > div > header > a"));
					
					System.out.println(outputCounter+".\t"+sets[setsCounter]);
					setsCounter++;
					urlsCounter++;
					outputCounter++;
				}
			}
			
			System.out.println((totalPosted.size()+1) +". Auto Mode");
			System.out.println("Last Chapter is "+ lastInput);
			int input = mainSc.nextInt();
			lastInput = input;
			
			if(input==0) {
				driver.quit();
				System.exit(0);//Program end-point
			}else if(input > totalPosted.size()+1) {
				System.out.println("Invalid input. Please try again.");
			}else if(input==totalPosted.size()+1){
				while(true) {
					int start;
					int end;
					System.out.println("Entry the start point of the class number above");
					start = mainSc.nextInt()-1;
					System.out.println("Entry the end point of the class number above");
					end = mainSc.nextInt()-1;
					lastInput = end;
					if(start<=end) {
						for(int i = start;i<=end;i++) {
							System.out.println("===========================");
							System.out.println("Chapter "+i+" start from here");
							String homePage = driver.getWindowHandle();
							urls[start].sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
							ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
							for(int j=0;j<tabs.size();j++) {
								if(!tabs.get(j).equals(homePage)) {
									driver.switchTo().window(tabs.get(j));
									innerPageAction(driver,mainSc,homePage,true);
									break;
								}
							}
							driver.close();
							driver.switchTo().window(homePage);
						}
						break;
					}else {
						System.out.println("Start must be less than end.\nTry again please");
					}
				}
				
			}else {
				String homePage = driver.getWindowHandle();
				driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
				urls[input-1].sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
				
				ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
				for(int i=0;i<tabs.size();i++) {
					if(!tabs.get(i).equals(homePage)) {
						driver.switchTo().window(tabs.get(i));
						innerPageAction(driver,mainSc,homePage,false);
						break;
					}
				}
				driver.close();
				driver.switchTo().window(homePage);
			}
		}
		
		
	}
	
	public void innerPageAction(ChromeDriver driver, Scanner mainSc, String homePage, boolean autoMode) throws InterruptedException, AWTException {
		Apps apps = new Apps();
		String innerPage = driver.getWindowHandle();
		String[] dictionary = apps.dictionaryLoader(driver, innerPage);
		if(dictionary[dictionary.length-1].equals("")) {
			System.out.println("Interrupted by AD");
			System.out.println("Database Reestablishing...");
			driver.findElement(By.cssSelector("div.UIModalHeader-closeIconButton span button span")).click();
			TimeUnit.MICROSECONDS.sleep(100);
			dictionary = apps.dictionaryLoader(driver, innerPage);
		}
		System.out.println("\n\nDatabase Established\n");
		
		if(autoMode) {
			WebElement  e;	//Learn
			ArrayList<String> innertabs;
			e = driver.findElement(By.cssSelector("div.SetPageModes-group.SetPageModes-group--study span:nth-child(3) a"));
			e.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
			innertabs = new ArrayList<String>(driver.getWindowHandles());
			for(int i=0;i<innertabs.size();i++) {
				if(!innertabs.get(i).equals(homePage)&&!innertabs.get(i).equals(innerPage)) {
					driver.switchTo().window(innertabs.get(i));
					apps.learn(driver,dictionary,dictionary.length/2,innerPage);
					driver.close();
					driver.switchTo().window(innerPage);
					break;
				}
			}
			
			//Write
			e = driver.findElement(By.cssSelector("div.SetPageModes-group.SetPageModes-group--study span:nth-child(4) a"));
			e.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));;
			innertabs = new ArrayList<String>(driver.getWindowHandles());
			for(int i=0;i<innertabs.size();i++) {
				if(!innertabs.get(i).equals(homePage)&&!innertabs.get(i).equals(innerPage)) {
					driver.switchTo().window(innertabs.get(i));
					apps.write(driver,dictionary,dictionary.length/2,innerPage);
					driver.close();
					driver.switchTo().window(innerPage);
					break;
				}
			}
		}else {
			while(true) {
				int input;
				System.out.println("Enter the number below to choose to the section you want.\n1. Learn\n2. Write\n3. Learn and Write\n4. Match\n5. Reload Database\n0. Exit\n");
				input = mainSc.nextInt();
				if(input>5||input<0) {
					System.out.println("Number invalid, try again\n");
					continue;
				}
				ArrayList<String> innertabs;
				WebElement e;
				switch(input) {
				case(1):
					e=driver.findElement(By.cssSelector("div.SetPageModes-group.SetPageModes-group--study span:nth-child(3) a"));
					e.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
					innertabs = new ArrayList<String>(driver.getWindowHandles());
					for(int i=0;i<innertabs.size();i++) {
						if(!innertabs.get(i).equals(homePage)&&!innertabs.get(i).equals(innerPage)) {
							driver.switchTo().window(innertabs.get(i));
							apps.learn(driver,dictionary,dictionary.length/2,innerPage);
							driver.close();
							driver.switchTo().window(innerPage);
							break;
						}
					}
					break;
				case(2):
					e=driver.findElement(By.cssSelector("div.SetPageModes-group.SetPageModes-group--study span:nth-child(4) a"));
					e.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
					innertabs = new ArrayList<String>(driver.getWindowHandles());
					for(int i=0;i<innertabs.size();i++) {
						if(!innertabs.get(i).equals(homePage)&&!innertabs.get(i).equals(innerPage)) {
							driver.switchTo().window(innertabs.get(i));
							apps.write(driver,dictionary,dictionary.length/2,innerPage);
							driver.close();
							driver.switchTo().window(innerPage);
							break;
						}
					}
					break;
				case(3):
					e=driver.findElement(By.cssSelector("div.SetPageModes-group.SetPageModes-group--study span:nth-child(3) a"));
					e.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
					innertabs = new ArrayList<String>(driver.getWindowHandles());
					for(int i=0;i<innertabs.size();i++) {
						if(!innertabs.get(i).equals(homePage)&&!innertabs.get(i).equals(innerPage)) {
							driver.switchTo().window(innertabs.get(i));
							apps.learn(driver,dictionary,dictionary.length/2,innerPage);
							driver.close();
							driver.switchTo().window(innerPage);
							break;
						}
					}
					e=driver.findElement(By.cssSelector("div.SetPageModes-group.SetPageModes-group--study span:nth-child(4) a"));
					e.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
					innertabs = new ArrayList<String>(driver.getWindowHandles());
					for(int i=0;i<innertabs.size();i++) {
						if(!innertabs.get(i).equals(homePage)&&!innertabs.get(i).equals(innerPage)) {
							driver.switchTo().window(innertabs.get(i));
							apps.write(driver,dictionary,dictionary.length/2,innerPage);
							driver.close();
							driver.switchTo().window(innerPage);
							break;
						}
					}
					
					break;
				case(4):
					e=driver.findElement(By.cssSelector("div.SetPageModes-group.SetPageModes-group--play span:nth-child(2) a"));						
					e.sendKeys(Keys.chord(Keys.CONTROL, Keys.RETURN));
					innertabs = new ArrayList<String>(driver.getWindowHandles());
					for(int i=0;i<innertabs.size();i++) {
						if(!innertabs.get(i).equals(homePage)&&!innertabs.get(i).equals(innerPage)) {
							driver.switchTo().window(innertabs.get(i));
							apps.match(driver,dictionary,dictionary.length/2);
							driver.close();
							driver.switchTo().window(innerPage);
							driver.get(driver.getCurrentUrl());
							loadingPage(driver);
							break;
						}
					}
					
					break;
				case(5):
					dictionary = apps.dictionaryLoader(driver, innerPage);
					System.out.println("\n\nDatabase Reestablished\n");
					break;
				case(0):
					return;
				}
			}
		}
	}
	
	
	public String stringConverter(String ori) {
		/*
		char[] c = new char[ori.length()];
		char[] newc = new char[ori.length()];
		for(int j=0;j<ori.length();j++) {
			c[j] = ori.charAt(j);
		}
		int counter=0;
		for(int j=0;j<c.length;j++) {
			if(c[j]=='\n') {
				
			}else {
				newc[counter] = c[j];
				counter++;
			}
		}
		char[] newnewc = new char[counter];
		for(int i=0;i<counter;i++) {
			newnewc[i] = newc[i];
		}
		String string = new String(newnewc);
		return string;
		*/
		return ori.replaceAll("\n", "");
	}
	
	public String readPassword() {
		String password;
		Console console=System.console();
		if(console != null) {
			//System.out.println("Console is available");
			System.out.print("Password: ");
			password = System.console().readLine();
		}else {
			//System.out.println("Console is unavailable");
			System.out.print("Password: ");
			password = mainSc.nextLine();
		}
		return password;
	}
	
	
	public void loadingPage(ChromeDriver driver) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Apps apps = new Apps();
		long lastHeight=((Number)js.executeScript("return document.body.scrollHeight")).longValue();
	    
		while (true) {
	        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	        TimeUnit.SECONDS.sleep(1);
	        js.executeScript("window.scrollBy(0, -2000)");
	        TimeUnit.SECONDS.sleep(1);
	        js.executeScript("window.scrollBy(0, 2000)");
	        TimeUnit.SECONDS.sleep(1);
	        long newHeight = ((Number)js.executeScript("return document.body.scrollHeight")).longValue();
	        if (newHeight == lastHeight) {
	        	if(apps.isElementPresentCssSelector(driver, "div.SetPageTerms-seeMore button span")) {
		        	WebElement e = driver.findElement(By.cssSelector("div.SetPageTerms-seeMore button span"));
		        	e.click();
		        }else {
		        	js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		        	return;
		        }
	        	
	        }
	        lastHeight = newHeight;
	    }
	}
	
	public WebElement xpathSelector(ChromeDriver driver, String xpath1, String xpath2) {
		Apps apps = new Apps();
		WebElement e;
		if(apps.isElementPresentXpath(driver, xpath1)) {
			e = driver.findElement(By.xpath(xpath1));
		}else {
			e = driver.findElement(By.xpath(xpath2));
		}
		return e;
	}
}