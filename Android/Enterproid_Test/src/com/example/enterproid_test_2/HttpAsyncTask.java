package com.example.enterproid_test_2;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class HttpAsyncTask extends AsyncTask<String, String, Void> {
	
	private Context mContext;
	
	public HttpAsyncTask(Context context){
		mContext=context;
	}
	
	@Override
    protected Void doInBackground(String... params) {
		 // Circumventing the NetworkOnMainThreadException
		 try{ 
 		 		// Parameters to handle connection timeout case
 		 		HttpParams clientParams = new BasicHttpParams();
 		 		int timeoutConnection = 3000; // 3 s=3000 ms
 		 		int timeoutSocket=5000; // 5 s=5000 ms
 		 		HttpConnectionParams.setConnectionTimeout(clientParams, timeoutConnection);
 		 		HttpConnectionParams.setSoTimeout(clientParams, timeoutSocket);
 		 		// Create http client object to send request to server
 		 		HttpClient client = new DefaultHttpClient(clientParams); 
 		 		/* alternative initialization */
 		 		/*
           			HttpClient client=new DefaultHttpClient();
           			client.setParams(clientParams);
 		 		 */
 		 		// Create URL string
 		 		String get_url = "http://app.enterproid.com:8180/android/query";
 		 		String host="http://app.enterproid.com:8180/";
 		 		//Log.i("httpget", URL);
 		 		try{
 		 			// Create Http Request with method "GET" to server
 		 			HttpGet httpget = new HttpGet(get_url);
 		 			try{
 		 				// Create HttpResponse object
 		 				HttpResponse get_response= client.execute(httpget);
 		 				if(get_response!=null){
 		 					String get_response_content=EntityUtils.toString(get_response.getEntity());
 		 					/* check data */
 		 					/*
                   		  		System.out.println(get_response_content);
 		 					 */
 		 					// convert json string to json object
 		 					JSONObject json_get=new JSONObject(get_response_content);
 		 					String post_url=json_get.getString("url"); post_url=host+post_url;
 		 					String key=json_get.getString("key");
 		 					/*check data*/
 		 					/*
                   		  		System.out.println("post_url="+post_url+"\nkey="+key);
 		 					 */
 		 					JSONObject json_post = new JSONObject();
 		 					json_post.put("question", "what is the answer");
 		 					json_post.put("key", key);
 		 					HttpPost httppost=new HttpPost(post_url);
 		 					// set POST Content-Type
 		 					httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
 		 					// set POST parameters
 		 					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
 		 					nameValuePairs.add(new BasicNameValuePair("json", json_post.toString()));
 		 					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                   		
 		 					try {
 		 							HttpResponse post_response=client.execute(httppost);
 		 							if(post_response!=null){
 		 									String post_response_content=EntityUtils.toString(post_response.getEntity());
 		 									System.out.println(post_response_content);
 		 									publishProgress(post_response_content);
 		 							}
 		 					}catch(ConnectTimeoutException ex){
 		 						System.out.println("Connection Timeout in POST!");
 		 					}catch(SocketTimeoutException ex){
 		 						System.out.println("Socket Timeout in POST!");
 		 					}catch(Exception ex){
 		 						System.out.println("Fail at 4!");
 		 					}
 		 				}
 		 				else{
 		 					// check data
 		 					System.out.println("GET Response Error");
 		 				}
 		 			} catch(ConnectTimeoutException ex){
 		 				System.out.println("Connection Timeout in GET");
 		 			} catch(SocketTimeoutException ex){
 		 				System.out.println("Socket Timeout in GET!");
 		 			} catch(Exception ex){
 		 				System.out.println("Fail at 3!");
 		 			}
 		 		} catch(Exception ex){
 		 			System.out.println("Fail at 2!");
 		 		}
		 }catch(Exception ex){
			 	System.out.println("Fail at 1");
		 }
		 return null;     
	}

	protected void onProgressUpdate(String... values) { 
		// handling UI thread
		Toast.makeText(mContext.getApplicationContext(), values[0], Toast.LENGTH_SHORT).show();
	}
}
