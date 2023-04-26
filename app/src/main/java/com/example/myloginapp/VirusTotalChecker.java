package com.example.myloginapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VirusTotalChecker {

    private static final String VIRUS_TOTAL_API_URL = "https://www.virustotal.com/api/v3/urls/";
    private static final String API_KEY = "5db0f71cbc722ccd4d6f9e2c4eb448d7068997def4e07e2e9863dd1b2d8a43ef";

    public interface VirusTotalCheckListener {
        void onCheckComplete(boolean isMalicious);
    }

    public static void checkUrl(String url, VirusTotalCheckListener listener) {
        new CheckUrlTask(listener).execute(url);
    }

    private static class CheckUrlTask extends AsyncTask<String, Void, Boolean> {

        private VirusTotalCheckListener listener;

        public CheckUrlTask(VirusTotalCheckListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String url = params[0];

            // Compute the SHA-256 hash of the URL
            byte[] hash;
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                hash = digest.digest(url.getBytes());
            } catch (NoSuchAlgorithmException e) {
                Log.e("VirusTotalChecker", "Failed to compute hash: " + e.getMessage());
                return false;
            }

            // Build the URL scanner API endpoint URL
            String urlScannerUrl = VIRUS_TOTAL_API_URL + bytesToHex(hash);

            // Make an HTTP GET request to the URL scanner API endpoint
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(urlScannerUrl)
                    .header("x-apikey", API_KEY)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                    int responseCode = jsonObject.get("data").getAsJsonObject().get("attributes").getAsJsonObject().get("last_analysis_stats").getAsJsonObject().get("malicious").getAsInt();
                    return responseCode > 0;
                } else {
                    Log.e("VirusTotalChecker", "Failed to scan URL: " + response.message());
                }
            } catch (IOException e) {
                Log.e("VirusTotalChecker", "Failed to scan URL: " + e.getMessage());
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean isMalicious) {
            listener.onCheckComplete(isMalicious);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}