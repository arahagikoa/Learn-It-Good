package com.example.main;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class RequestsMNG {
    private String MSG_URL = "http://localhost:5000/API/message";
    private String LOGIN_URL = "http://localhost:5000/API/login";
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build();

    private Gson gson = new Gson(); // Create a Gson instance

    public String sendMessage(String msg, String db_name) throws IOException {
        String response_message = "";
        FormBody formBody = new FormBody.Builder()
                .add("Database", db_name)
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
                String jsonResponse = response.body().string();
                System.out.println("Response: " + jsonResponse); // Print the response
                
                // Parse the response based on its actual type
                if (jsonResponse.startsWith("{")) {
                    JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
                    response_message = jsonObject.get("message").getAsString();
                } else {
                    response_message = jsonResponse;
                }
                return response_message;
            } else {
                JOptionPane.showMessageDialog(null, "Runtime Error");
                response_message = "Runtime Error";
                return response_message;
            }
        } finally {
            if (response != null) {
                response.close();
            }
            System.out.println(response_message);
        }
    }

    public String loginRequest(String db_name) throws IOException {
        String response_message = "";
        FormBody formBody = new FormBody.Builder()
                .add("Database", db_name)
                .build();

        Request request = new Request.Builder()
                .url(LOGIN_URL)
                .post(formBody)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        try {
            if (response.isSuccessful()) {
                String jsonResponse = response.body().string();
                System.out.println("Response: " + jsonResponse); // Print the response
                
                // Parse the response based on its actual type
                if (jsonResponse.startsWith("{")) {
                    JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
                    response_message = jsonObject.get("Bearer").getAsString();
                } else {
                    response_message = jsonResponse;
                }
                return response_message;
            } else {
                JOptionPane.showMessageDialog(null, "Runtime Error");
                response_message = "Runtime Error";
                return response_message;
            }
        } finally {
            if (response != null) {
                response.close();
            }
            System.out.println("TOKEN: " + response_message);
        }
    }

    public static void main(String[] args) {
        RequestsMNG requestsMNG = new RequestsMNG();
        try {
            String response = requestsMNG.sendMessage("Hello, this is a test message!", "test_db");
            System.out.println("Response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
