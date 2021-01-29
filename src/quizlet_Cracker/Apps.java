package quizlet_Cracker;

import java.awt.AWTException;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Apps {
	public void learn(ChromeDriver driver, String[] dictionary, int vocaSize, String innerPage) throws InterruptedException {
		Quizlet_Cracker qc = new Quizlet_Cracker();
		int counter = 1;                    
		String press_any_key_to_continue2 = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[3]/div/div[2]/div/button";
		String press_any_key_to_continue = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[1]/div/div/div[2]/div/button/span/span";
		String got_it = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[1]/div/div[2]/div/button";
		String finish = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[1]/div/div/div[1]/div/h3";
		
		String fill_in_question = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[2]/div/div[1]/div/div[2]/div/div/div/div/div";
		String fill_in_answer = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[2]/div/div[2]/div/form/div[1]/div/label/div/div[1]/div[2]/textarea";
		String fill_in_button = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[2]/div/div[2]/div/form/div[2]/button";
		
		String choice_question = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[2]/div/div/div[2]/div/div[1]/div/div/div/div/div";
		String choice_option1 = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[2]/div/div/div[2]/div/div[2]/div/div[1]/div[2]/div/div";
		String choice_option2 = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[2]/div/div/div[2]/div/div[2]/div/div[2]/div[2]/div/div";
		String choice_option3 = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[2]/div/div/div[2]/div/div[2]/div/div[3]/div[2]/div/div";
		String choice_option4 = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[2]/div/div/div[2]/div/div[2]/div/div[4]/div[2]/div/div";
		
		String click_to_flip = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[1]/div/div[1]/div/div/div[1]/div[2]/div[2]/div";
		String got_it_2 = "//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[1]/div/div[2]/div/div/div[1]/div/button";
		System.out.println("Learn start from here");
		TimeUnit.SECONDS.sleep(1);
		driver.findElement(By.cssSelector("div.ModeControls-actions div.ModeControls-action button")).click();
		List<WebElement> checkBox = driver.findElements(By.cssSelector("input.UICheckbox-input"));
		for(int i=0;i<checkBox.size();i++) {
			if(!checkBox.get(i).isSelected()) {
				checkBox.get(i).click();
			}
		}
		driver.findElement(By.cssSelector("div.UIModalHeader-closeIconButton")).click();
		while(true) {
			if(counter>vocaSize*4) {
				return;
			}
			if(isElementPresentXpath(driver,fill_in_question)) {
				System.out.println(counter);
				counter++;
				
				String question = driver.findElement(By.xpath(fill_in_question)).getText();
				if(question.contains("\n")) {
					System.out.println(question+"\n");
					question = qc.stringConverter(question);
				}
				System.out.println(question+"\n");
				String defult_answer = "abc";
				String answer = defult_answer;
				TimeUnit.SECONDS.sleep(1);
				WebElement answerArea = driver.findElement(By.xpath(fill_in_answer));
				WebElement button = driver.findElement(By.xpath(fill_in_button));
				for(int i=0;i<dictionary.length;i++) {
					if(question.equals(dictionary[i])) {
						if(i>=vocaSize) {
							i=i-vocaSize;
						}else {
							i=i+vocaSize;
						}
						answer = dictionary[i];
						break;
					}
				}
				if(answer.equals(defult_answer)){
					System.out.println("Invalid Answer");
					dictionary = dictionaryLoader(driver, innerPage);
					counter--;
					System.out.println("\n\nDatabase Reestablished\n");
					continue;
				}
				try{
					answerArea.clear();
					TimeUnit.SECONDS.sleep(1);
					answerArea.sendKeys(answer);
					TimeUnit.SECONDS.sleep(1);
					button.click();
				}catch(StaleElementReferenceException e1) {
					System.out.println("Exception");
					TimeUnit.SECONDS.sleep(1);
					counter--;
					continue;
				}
				TimeUnit.SECONDS.sleep(3);
			}else if(isElementPresentXpath(driver,choice_question)) {
				System.out.println(counter);
				counter++;
				String question = driver.findElement(By.xpath(choice_question)).getText();
				if(question.contains("\n")) {
					System.out.println(question+"\n");
					question = qc.stringConverter(question);
				}
				System.out.println(question+"\n");
				String defult_answer = "abc";
				String answer = defult_answer;
				for(int i=0;i<dictionary.length;i++) {
					if(question.equals(dictionary[i])) {
						if(i>=vocaSize) {
							i=i-vocaSize;
						}else {
							i=i+vocaSize;
						}
						answer = dictionary[i];
						break;
					}
				}
				if(answer.equals(defult_answer)){
					System.out.println("Invalid Answer");
					dictionary = dictionaryLoader(driver, innerPage);
					counter--;
					System.out.println("\n\nDatabase Reestablished\n");
					continue;
				}
				for(int i=1;i<=4;i++) {                               
					String choiceString = driver.findElement(By.xpath("//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[2]/div/div/div[2]/div/div[2]/div/div["+i+"]/div[2]/div/div")).getText();
					if(choiceString.equals(answer)) {
						WebElement d = driver.findElement(By.xpath("//*[@id=\"AssistantModeTarget\"]/div/div/div/div[2]/div/span[2]/div/div/div[2]/div/div[2]/div/div["+i+"]/div[2]/div/div"));
						d.click();
						break;
					}
				}
				TimeUnit.SECONDS.sleep(3);
			}else if(isElementPresentXpath(driver,press_any_key_to_continue)) {
				WebElement button = driver.findElement(By.xpath(press_any_key_to_continue));
				button.click();
				TimeUnit.SECONDS.sleep(1);
			}else if(isElementPresentXpath(driver,press_any_key_to_continue2)) {
				WebElement button = driver.findElement(By.xpath(press_any_key_to_continue2));
				button.click();
				TimeUnit.SECONDS.sleep(1);
			}else if(isElementPresentXpath(driver,got_it)) {
				WebElement button = driver.findElement(By.xpath(got_it));
				button.click();
				TimeUnit.SECONDS.sleep(1);
			}else if(isElementPresentXpath(driver,finish)){
				TimeUnit.SECONDS.sleep(5);
				return;
			}else if(isElementPresentXpath(driver, click_to_flip)) {
				WebElement flipper = driver.findElement(By.xpath(click_to_flip));
				flipper.click();
				TimeUnit.SECONDS.sleep(1);
				WebElement button = driver.findElement(By.xpath(got_it_2));
				button.click();
				TimeUnit.SECONDS.sleep(3);
			}else {
				driver.get(driver.getCurrentUrl());
			}
			
		}
		
	}
	
	public void write(ChromeDriver driver, String[] dictionary, int vocaSize, String innerPage) throws InterruptedException {
		Quizlet_Cracker qc = new Quizlet_Cracker();
		int time = 0;
		int counter = 0;
		System.out.println("Write start from here");
		while(true) {
			TimeUnit.SECONDS.sleep(3);
			if(isElementPresentXpath(driver, "//*[@id=\"js-learnModeAnswerButton\"]")) {
				counter++;
				System.out.println(counter);
				String question = driver.findElement(By.xpath("//*[@id=\"js-learnModeInner\"]/div/div/div[1]/div/div/div/div/span")).getText();
				if(question.contains("\n")) {
					System.out.println(question+"\n");
					question = qc.stringConverter(question);
				}
				System.out.println(question+"\n");
				
				WebElement answerArea = driver.findElement(By.xpath("//*[@id=\"user-answer\"]"));
				WebElement button = driver.findElement(By.xpath("//*[@id=\"js-learnModeAnswerButton\"]"));
				
				String defult_answer = "abc";
				String answer = defult_answer;
				for(int i=0;i<dictionary.length;i++) {
					if(question.equals(dictionary[i])) {
						if(i>=vocaSize) {
							i=i-vocaSize;
						}else {
							i=i+vocaSize;
						}
						answer = dictionary[i];
						break;
					}
				}
				
				if(answer.equals(defult_answer)){
					System.out.println("Invalid Answer");
					dictionary = dictionaryLoader(driver, innerPage);
					counter--;
					System.out.println("\n\nDatabase Reestablished\n");
					continue;
				}
				
				try{
					answerArea.clear();
					TimeUnit.SECONDS.sleep(1);
					answerArea.sendKeys(answer);
					TimeUnit.SECONDS.sleep(1);
					button.click();
					TimeUnit.SECONDS.sleep(1);
					WebElement press_to_continue = driver.findElement(By.xpath("//*[@id=\"js-learnModeInner\"]/div[2]/button"));
					press_to_continue.click();
				}catch(StaleElementReferenceException e1) {
					System.out.println("Exception");
					TimeUnit.SECONDS.sleep(1);
					counter--;
					continue;
				}
			}else if(isElementPresentXpath(driver, "//*[@id=\"js-learnModeInner\"]/div/div/div/div/header/div/div[2]/a")) {//star over
				if(time>=1) {
					return;
				}
				time++;
				counter=0;
				WebElement press_to_continue = driver.findElement(By.xpath("//*[@id=\"js-learnModeInner\"]/div/div/div/div/header/div/div[2]/a"));
				press_to_continue.click();
			}else if(isElementPresentXpath(driver, "//*[@id=\"js-learnModeInner\"]/div[2]/button")){//press any key to continue
				WebElement press_to_continue = driver.findElement(By.xpath("//*[@id=\"js-learnModeInner\"]/div[2]/button"));
				press_to_continue.click();
			}else {
				driver.get(driver.getCurrentUrl());
			}
			
		}
		
	}
	
	public void match(ChromeDriver driver, String[] dictionary, int vocaSize) throws InterruptedException, AWTException {
		Quizlet_Cracker qc = new Quizlet_Cracker();
		Scanner sc = new Scanner(System.in);
		
		int squareNum = 12;
		boolean iffound[] = new boolean[squareNum];
		int sequence[] = new int[squareNum];
		for(int i=0;i<iffound.length;i++) {
			iffound[i] = false;
		}
		System.out.println("Delay time (ms): ");//minimum process time is 150 millisecond
		int delay = sc.nextInt();
		
		WebElement b = driver.findElement(By.xpath("/html/body/div[5]/div/div/div/div[2]/button"));
		b.click();
		
		
		
		TimeUnit.MILLISECONDS.sleep(Math.abs(delay));
		
		String[] webText = new String[squareNum];
		int sequenceIndex=0;
		for(int i=0;i<squareNum;i++) {
			webText[i] = driver.findElement(By.cssSelector("\"#MatchModeTarget > div > div > div > div.ModeLayout-content > div > div > div:nth-child("+i+1+") > div > div > div > div\"")).getText();
			if(webText[i].contains("\n")) {
				webText[i] = qc.stringConverter(webText[i]);
			}
		}
		for(int i=0;i<squareNum;i++) {
			if(!iffound[i]) {
				iffound[i] = true;
				sequence[sequenceIndex] = i;
				sequenceIndex++;
				
				for(int j=0;j<dictionary.length;j++) {
					if(webText[i].equals(dictionary[j])) {
						if(j>=vocaSize) {
							j=j-vocaSize;
						}else {
							j=j+vocaSize;
						}
						for(int k=0;k<webText.length;k++) {
							if(webText[k].equals(dictionary[j])) {
								sequence[sequenceIndex] = k;
								sequenceIndex++;
								iffound[k] = true;
								break;
							}
						}
						break;
					}
				}
			}
		}
		for(int i=0;i<sequence.length;i++) {
			WebElement answer = driver.findElement(By.cssSelector("#MatchModeTarget > div > div > div > div.ModeLayout-content > div > div > div:nth-child("+(sequence[i]+1)+")"));
			answer.click();
		}
		TimeUnit.SECONDS.sleep(7);
		return;
	}
	
	public int calaulate_X(WebElement block1, WebElement block2) { //from 1 to 2
		Point block1Loc = block1.getLocation();
		Point block2Loc = block2.getLocation();
		int b1X = block1Loc.getX();
		int b2X = block2Loc.getX();
		
		return (b2X-b1X);
	}
	
	public int calaulate_Y(WebElement block1, WebElement block2) { //from 1 to 2
		Point block1Loc = block1.getLocation();
		Point block2Loc = block2.getLocation();
		int b1Y = block1Loc.getY();
		int b2Y = block2Loc.getY();
		
		return (b2Y-b1Y);
	}
	
	public boolean isElementPresentXpath(ChromeDriver driver, String xpath){
        try{
            driver.findElement(By.xpath(xpath));
            return true;
        }
        catch(NoSuchElementException e) {
            return false;
        }
    }
	public boolean isElementPresentCssClass(ChromeDriver driver, String className){
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
        boolean exist = driver.findElement(By.className(className)) != null;
        return exist;
    }
	public boolean isElementPresentCssSelector(ChromeDriver driver, String cssPath) {
		List<WebElement> w = driver.findElements(By.cssSelector(cssPath));
		if(w.size()>0) {
			return true;
		}else {
			return false;
		}

	}
	
	public String[] dictionaryLoader(ChromeDriver driver, String innerPage) throws InterruptedException {
		Quizlet_Cracker qc = new Quizlet_Cracker();
		String currentHandle = driver.getWindowHandle();
		if(!currentHandle.equals(innerPage)) {
			driver.switchTo().window(innerPage);
		}
		//driver.get(driver.getCurrentUrl());
		qc.loadingPage(driver);
		
		if(driver.findElements(By.cssSelector("div.UIFieldset-fields select.UIDropdown-select")).size()>0) {
			Select classify = new Select(driver.findElement(By.cssSelector("div.UIFieldset-fields select.UIDropdown-select")));
			classify.selectByValue("original");
		}
		List<WebElement> totalPosted = driver.findElements(By.cssSelector("div.SetPageTerms-term"));
		
		String[] dictionary = new String[2*totalPosted.size()];
															
		for(int i=1;i<=totalPosted.size();i++) {		   
			dictionary[i-1] = driver.findElement(By.cssSelector("div.SetPageTerms-term:nth-child("+i+") a.SetPageTerm-wordText span")).getText();//eng							  
			dictionary[i-1+totalPosted.size()] = driver.findElement(By.cssSelector("div.SetPageTerms-term:nth-child("+i+") a.SetPageTerm-definitionText span")).getText();//zh_ch
			if(dictionary[i-1+totalPosted.size()].contains("\n")) {
				dictionary[i-1+totalPosted.size()] = qc.stringConverter(dictionary[i-1+totalPosted.size()]);//remove \n to \0
			}
			System.out.println(i+". "+dictionary[i-1]+"\t"+dictionary[i-1+totalPosted.size()]);
		}
		
		System.out.println("\nTotal words: "+totalPosted.size());
		if(!currentHandle.equals(innerPage)) {
			driver.switchTo().window(currentHandle);
		}
		return dictionary;
	}
}
