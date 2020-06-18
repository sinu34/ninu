package kDev;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReusableFunction {
	public static WebDriver driver;
	

	 //public static Environment;
	public static String[][] fetchDataFromExcel(String SheetName) {
		Workbook wb = null;
		String[][] data = null;
		try {
			
			String path = fetchprop("path");
			System.out.println(path);
			
			File excel = new File(path);
			FileInputStream file = new FileInputStream(excel);
			System.out.println(path);

			wb = new XSSFWorkbook(file);
			Sheet sheet = wb.getSheet(SheetName);
			int rowNum = sheet.getLastRowNum();
			System.out.println(rowNum + "  row num");
			int column = sheet.getRow(0).getLastCellNum();
			System.out.println(column + "  col num");
			data = new String[rowNum+1][column];
			//System.out.println(sheet.getRow(9).getCell(5));
			for (int i = 0; i <= rowNum; i++) {
				Row row = sheet.getRow(i);
				for (int j = 0; j < column; j++) {
					
					try {
						Cell cell = row.getCell(j);
						String value = cell.toString();
						//System.out.println(value+ " "+ i +" i " + j + " j");
						data[i][j] = value;
					}catch(Exception e) {
						//e.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				wb.close();
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return data;
	}

	public static String fetchprop(String text) {
		Properties prop = new Properties();
		FileInputStream input;
		try {
			input = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\AutomationCM\\object.properties");
			prop.load(input);

		} catch (Exception ex) {
				ex.printStackTrace();
		}
		return prop.getProperty(text);

	}

	public void open_browser() throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\dell\\Desktop\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.get(fetchprop("URL"));
	}

	public void fill_text(String locatorBy, String locatorValue, String text) throws InterruptedException {

		switch (locatorBy) {
		case "id":
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			driver.findElement(By.id(locatorValue)).sendKeys(text);
			break;
		case "xpath":
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			driver.findElement(By.xpath(locatorValue)).sendKeys(text);
			break;
		}
	}

	public void click(String locatorBy, String locatorElement) throws AWTException, InterruptedException {
		switch (locatorBy) {
		case "id":
			try {
				System.out.println("1 "+locatorElement);
				WebDriverWait wait = new WebDriverWait(driver, 20);
				WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(locatorElement)));
				element.click();
			}catch(Exception e){
				e.printStackTrace();
				try {
					WebDriverWait wait = new WebDriverWait(driver, 20);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorElement)));
					System.out.println("2 "+locatorElement);
					WebElement element1 = driver.findElement(By.id(locatorElement));
					Actions actions1 = new Actions(driver);
					actions1.moveToElement(element1).click().perform();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
			break;
		case "xpath":
			try {
				System.out.println("1 "+locatorElement);
				WebDriverWait wait = new WebDriverWait(driver, 20);
				WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorElement)));
				element.click();
			}catch(Exception e){
				e.printStackTrace();
				try {
					System.out.println("2 " +locatorElement);
					WebDriverWait wait = new WebDriverWait(driver, 20);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorElement)));
					WebElement element1 = driver.findElement(By.xpath(locatorElement));
					Actions actions1 = new Actions(driver);
					actions1.moveToElement(element1).click().perform();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
				
			}
			break;
		case "pressEnter":
			Thread.sleep(3000);
			System.out.println("enter "+locatorElement);
			//driver.findElement(By.xpath(locatorElement)).sendKeys(Keys.ENTER);
			//Robot robot = new Robot();
			//robot.keyPress(KeyEvent.VK_ENTER);
			//robot.keyRelease(KeyEvent.VK_ENTER);
			
			Actions builder=new Actions(driver);
			builder.clickAndHold(driver.findElement(By.xpath(locatorElement))).perform();
			builder.sendKeys(Keys.ENTER).perform();
			builder.release().perform();
			 
			System.out.println("enter2");
			break;
		case "cssselector":
			List<WebElement> skip = driver.findElements(By.cssSelector(locatorElement));
			((WebElement) skip).click();
	break;
		}
		
		}
	
	public void driverClose() {
		driver.close();
	}
	
}
