package main;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestsMNG {
	private String MSG_URL = "http://localhost:5000/API/message";
	private String FILE_URL = "http://localhost:5000/API/login";
	private OkHttpClient client = new OkHttpClient();
	
	
	public String sendMessage(String msg) throws IOException {
	        String response_message = "";
	        FormBody formBody = new FormBody.Builder()
	                .add("username", "test")
	                .add("password", "test")
	                .add("message", msg)
	                .build();
	
	        Request request = new Request.Builder()
	                .url(MSG_URL)
	                .post(formBody)
	                .build();
	
	        Call call = client.newCall(request);
	        Response response = call.execute();
	        
	        try {
	            if (response.isSuccessful()) {
	                
	                
	                response_message = response.body().string();
	                return response_message;
	            } else {
	            	
	                
	                response_message = "request failed, please try again";
	                return response_message;
	            }
	        } finally {
	        	
	        	System.out.println("Request");
	        	
	        }
	    }
	public static void main(String[] args) {
	    RequestsMNG requestsMNG = new RequestsMNG();
	    //requestsMNG.sendMessage("Hello, this is a test message!");
		System.out.println("Response: " );
}
	
}
