package kDev.testrunner;

import java.awt.AWTException;

import kDev.ReusableFunction;

public class ExecuteTestRunner
{

	public static void main(String[] args) throws AWTException, InterruptedException 
	{
		ReusableFunction func = new ReusableFunction();
		String[][] keywords = ReusableFunction.fetchDataFromExcel("Test Steps");

		int rownum = keywords.length;
		int colnum = keywords[0].length;

		String TC_Name = keywords[0][0];
		String TC_Step = keywords[0][1];
		String TC_step_name = keywords[0][2];
		String TC_func = keywords[0][3];
		String TC_Location = keywords[0][4];
		String TC_lv = keywords[0][5];
		String TC_param = keywords[0][6];
		String TC_Execute = keywords[0][7];

		for (int counter = 1; counter < rownum; counter++) 
		{
			String function = keywords[counter][3];
			String locatorBy = keywords[counter][4];
			String content_param = keywords[counter][6];
			String locatorElement = keywords[counter][5];
			System.out.println(function + '\t' + locatorBy + '\t' + locatorElement + '\t' + content_param);
			switch (function) {
			case "open_browser":
				func.open_browser();	
				break;
			case "fill_text":
				//Thread.sleep(3000);
				func.fill_text(locatorBy, locatorElement, content_param);
				break;
			case "click":
				func.click(locatorBy, locatorElement);
				break;
			case "nextSheet":
				String sheetName = keywords[counter][4];
				counter=1;
				
				keywords = ReusableFunction.fetchDataFromExcel(sheetName);
			}

		}

	}

}
