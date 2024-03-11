package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpImageStatusCli {

    private final HttpStatusImageDownloader DOWNLOADER;

    public HttpImageStatusCli() {
        this.DOWNLOADER = new HttpStatusImageDownloader();
    }

    public void askStatus() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Enter HTTP status code: ");
            String input = reader.readLine();

            try {
                int statusCode = Integer.parseInt(input);
                DOWNLOADER.downloadStatusImage(statusCode);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (IOException e) {
                System.out.println("There is not image for HTTP status " + input);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading input.");
        }
    }

    public static void main(String[] args) {
        HttpImageStatusCli cli = new HttpImageStatusCli();
        cli.askStatus();
    }
}
