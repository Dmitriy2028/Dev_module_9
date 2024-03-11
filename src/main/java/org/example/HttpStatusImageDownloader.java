package org.example;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpStatusImageDownloader {

    private final HttpStatusChecker CHECKER;

    public HttpStatusImageDownloader() {
        this.CHECKER = new HttpStatusChecker();
    }

    public void downloadStatusImage(int code) throws IOException {
        String imageUrl;
        try {
            imageUrl = CHECKER.getStatusImage(code);
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "status_" + code + ".jpg";
                String savePath = "src/main/resources/images/" + fileName;
                try (InputStream inputStream = connection.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(savePath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    System.out.println("Image downloaded successfully: " + savePath);
                }
            } else {
                throw new IOException("Failed to download image for status code " + code);
            }
            connection.disconnect();
        } catch (IOException e) {
            throw new IOException("Image not found for status code " + code);
        }
    }

    public static void main(String[] args) {
        HttpStatusImageDownloader downloader = new HttpStatusImageDownloader();

        try {
            // Testing with various status codes
            downloader.downloadStatusImage(200); // Valid status code
            downloader.downloadStatusImage(404); // Valid status code
            // Testing with an invalid status code
            downloader.downloadStatusImage(10000); // Invalid status code
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
