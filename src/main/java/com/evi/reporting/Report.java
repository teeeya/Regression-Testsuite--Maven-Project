package com.evi.reporting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import com.evi.handlers.Testcase;
/*
 * This class will create the report for each testcase
 */
public class Report {
	private static final Map<String, Testcase> reports = new HashMap<String, Testcase>();
	private static String question;
	private static Testcase testcase;
	private int amountFailed, amountSuccessful, amountErrored;
	public Report(Testcase testcase) {
		this.testcase = testcase;
	}
	public boolean logTestcaseResult(Testcase testcase) {
		reports.put(testcase.getQuestion(), testcase);
		return true;
	}
	public static void outputReport() throws IllegalStateException {
		try {
			Collection<String> keys = reports.keySet();
			Iterator<String> keyIterator = keys.iterator();
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				Testcase report = reports.get(key);
				JAXBContext context = JAXBContext.newInstance(Testcase.class);
				Marshaller m = context.createMarshaller();
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				File output = new File("/results");
				if (output.exists() == false) {
					output.mkdirs();
				}	
				File outputFile = new File("results/"+(new Date()).getTime()+ ".xml");
				if (outputFile.exists() == false) {
					outputFile.createNewFile();
				} else {
					System.out.println("Unable to create report ");
				}
				OutputStream outputStream = new FileOutputStream(outputFile);
				m.marshal(report, outputStream);
			}
		} catch (IOException e) {
			System.out.println("Unable to create test report" + e.getMessage());
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}
}
