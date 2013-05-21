package com.evi.handlers;

import java.io.File;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.evi.reporting.Report;
import com.evi.utils.DateInjection;

/*
 * This class 
 * 1. Processes the config file - parses the xml and stores it
 * 2. Executes the tests
 * 
 */
public class TestHandler {
	private Properties properties;
	private ArrayList<Testcase> tests = new ArrayList<Testcase>();
	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
			.newInstance();
	DocumentBuilder documentBuilder;
	Document document;
	private Report report;

	public TestHandler(Properties applicationProperties) {
		this.properties = applicationProperties;
	}

	public void processConfigFile() {
		try {
			/*
			 * Process the xml questions file and build the testcases based on
			 * the xml Each question and answer should map to a testcase.
			 * 
			 */
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			File file = new File(properties.getProperty("location"));
			if (file.exists()) {
				document = documentBuilder.parse(file);
				Element docElement = document.getDocumentElement();
				// nodelist of all of the questions in the xml
				NodeList questionList = docElement
						.getElementsByTagName("question");
				for (int i = 0; i < questionList.getLength(); i++) {
					// for each question, create a testcase
					Node node = questionList.item(i);
					Element e = (Element) node;
					NodeList inputList = e.getElementsByTagName("input");
					NodeList answerList = e
							.getElementsByTagName("expectedAnswer");
					/*
					 * // Do we need to inject date?
					 */
					if (answerList.item(0).getChildNodes().item(0)
							.getNodeValue().equals("@CurrentTime@")) {
						answerList.item(0).getChildNodes().item(0)
								.setNodeValue(
										new DateInjection()
												.returnInjectionDate());
					}

					tests.add(new Testcase(inputList.item(0).getChildNodes()
							.item(0).getNodeValue(), answerList.item(0)
							.getChildNodes().item(0).getNodeValue(), e
							.getAttribute("timeout")));
				}
				System.out.println("Total questions in config file: "
						+ questionList.getLength());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}// finish process

	public void executeQuery() {
		String url = properties.getProperty("url");
		String password = properties.getProperty("password");
		String username = properties.getProperty("username");
		for (int i = 0; i < tests.size(); i++) {

			new OSLHandler(url, username, password, tests.get(i).getQuestion(),
					tests.get(i));
		}
	}

	/*
	 * Check 1. Is the expected answer in the answer returned from the server 2.
	 * Split the answer up by ',' and check each answer in the array
	 */
	public void executeTests() {
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			int success = 0;
			for (int i = 0; i < tests.size(); i++) {
				report = new Report(tests.get(i));
				document = documentBuilder.parse(new StringBufferInputStream(
						tests.get(i).getResponse()));
				Element docElement = document.getDocumentElement();
				NodeList answer = docElement
						.getElementsByTagName("tk:text_result");
				String value = answer.item(0).getChildNodes().item(0)
						.getNodeValue();
				String expected = tests.get(i).getExpectedAnswer();
				System.out.println(answer.item(0).getChildNodes().item(0)
						.getNodeValue());
				String[] answerList = new String[value.replace("and", ",")
						.split(",").length];
				String[] expectedList = new String[expected.split(",").length];
				answerList = value.replace("and", ",").split(",");
				expectedList = expected.replace("and", ",").split(",");

				if (value.contains(expected)) {
					System.out.println("Success" + value + expected);
					tests.get(i).setResultOfQuestion(true);
					report.logTestcaseResult(tests.get(i));
				} else if (answerList.length == expectedList.length) {

					for (int j = 0; j < answerList.length; j++) {
						if (answerList[j].replace(",", "").trim().equals(
								expectedList[j].replace(",", "").trim())) {
							success++;
						}
					}
					if (success == answerList.length) {
						System.out.println("Success all values matched");
						tests.get(i).setResultOfQuestion(true);
						report.logTestcaseResult(tests.get(i));
					} else {
						tests.get(i).setResultOfQuestion(false);
						report.logTestcaseResult(tests.get(i));
					}
				} else {
					tests.get(i).setResultOfQuestion(false);
					System.out
							.println("The number of expected results did not match actual");
					report.logTestcaseResult(tests.get(i));
				}
			}
		} catch (Exception e) {
			System.out.println("error" + e.getMessage());
		}

	}
}
