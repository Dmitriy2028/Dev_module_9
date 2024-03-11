package org.example;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpStatusChecker {
    public String getStatusImage(int code) throws IOException {
        String imageUrl = "https://http.cat/" + code + ".jpg";
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        int responseCode = connection.getResponseCode();
        connection.disconnect();

        if (responseCode == 404) {
            throw new IOException("Image not found for status code " + code);
        }

        return imageUrl;
    }

    public static void main(String[] args) {
        HttpStatusChecker checker = new HttpStatusChecker();

        try {
            // Testing with various status codes
            System.out.println(checker.getStatusImage(200)); // Valid status code
            System.out.println(checker.getStatusImage(404)); // Valid status code
            // Testing with an invalid status code
            System.out.println(checker.getStatusImage(10000)); // Invalid status code
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
