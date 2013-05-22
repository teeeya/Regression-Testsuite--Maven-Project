package com.evi.handlers;

/**
 * This class will execute all of the queries
 * @author Fathea Chowdhury
 * @version 1.0
 * 
 */
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class OSLHandler {
	private int timeOut;
	private String password, username, question, query;
	private HttpResponse response;
	public OSLHandler(String query, String username, String password,
			String question, Testcase test) {
		this.query = query;
		this.password = password;
		this.username = username;
		this.question = question;
		this.timeOut = test.getMaxTimeOut();
		//build the query
		String fullQuery = query + "question=" + question + "&api_account_id=" + username + "&api_password=" + password;
		//Set up the httpClient and execute the query
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(fullQuery);
		try {
			test.startTimer(); //start the timer for the testcase
			response = httpclient.execute(httpGet);//execute
			test.endTimer();//stop the timer straight after
			HttpEntity entity = response.getEntity();//get the response
			String responseString = EntityUtils.toString(entity, "UTF-8");
			test.setResponse(responseString);//set the response for the testcase
			test.setResultReturnedInTime(test.workOutWhetherWithinTime());
		} catch (Exception e) {
			System.out
					.println("Error could not exercute test" + e.getMessage());
		} finally {
			httpGet.releaseConnection();
		}
	}
}
