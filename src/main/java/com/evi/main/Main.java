package com.evi.main;

/**
 * This is a regression testsuite  that will test that the correct text answer is given to the following 
 * questions at all times and that the response is returned within a (configurable) 5 seconds.
 * How old is the Queen?
 * What is the time?
 * What is the capital of France?
 * Who was president of the US when Barack Obama was a teenager?
 * 
 * @author FatheaChowdhury
 * 
 */

import com.evi.domain.ApplicationProperties;
import com.evi.handlers.TestHandler;
import com.evi.reporting.Report;

public class Main {
	private static ApplicationProperties applicationProperties;
	private static TestHandler testHandler;
	public static void main(String[] args) {
		// Set the application Properties
		applicationProperties = new ApplicationProperties(args);
		// System.out.println();
		testHandler = new TestHandler(applicationProperties.setup());
		testHandler.processConfigFile();
		testHandler.executeQuery();
		testHandler.executeTests();
		Report.outputReport();
	}
}
