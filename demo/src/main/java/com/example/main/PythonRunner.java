package com.example.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class PythonRunner {
    private static Process flaskProcess;

    public static void main(String[] args) {
        
        String flaskScriptPath = getPythonScriptPath("server.py");

        if (flaskScriptPath != null) {
            startFlaskServer(flaskScriptPath);
            Runtime.getRuntime().addShutdownHook(new Thread(PythonRunner::stopFlaskServer));

            try {
            	System.out.println("Java application running...");
                while (true) {
                    
                    Thread.sleep(120);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Unable to locate the Flask server script.");
        }
    }

    public static String getPythonScriptPath(String resourcePath) {
        
        URL resourceUrl = PythonRunner.class.getClassLoader().getResource(resourcePath);
        if (resourceUrl == null) {
            System.err.println("Resource not found: " + resourcePath);
            return null;
        }

        try {
            
            File file = new File(resourceUrl.toURI());
            return file.getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void startFlaskServer(String scriptPath) {
        File pythonScript = new File(scriptPath);
        if (!pythonScript.exists()) {
            System.out.println("Flask server script does not exist: " + scriptPath);
            return;
        }

        String command = "python " + scriptPath;

        try {
            ProcessBuilder pb = new ProcessBuilder(command.split(" "));
            pb.redirectErrorStream(true);

            flaskProcess = pb.start();

            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(flaskProcess.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("[Flask Server] " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            System.out.println("Flask server started.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopFlaskServer() {
        if (flaskProcess != null && flaskProcess.isAlive()) {
            flaskProcess.destroy();
            System.out.println("Flask server stopped.");
        }
    }
}
