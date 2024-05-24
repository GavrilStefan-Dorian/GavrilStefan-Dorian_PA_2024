package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatasetTool {
    public static List<String[]> getData(String filename) {
        try (
                CSVReader reader = new CSVReader(new FileReader(filename));
                )
        {
            return reader.readAll();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void addGenreData(String filename) {
        try(
                CSVReader reader = new CSVReader(new FileReader(filename));
                CSVWriter writer = new CSVWriter(new FileWriter("newbooks.csv"))
                ) {
            List<String> columns = new ArrayList<>(List.of(reader.readNext()));
            columns.add(2, "Genre");
            writer.writeNext(columns.toArray(new String[0]));

            String[] nextLine;
            while((nextLine = reader.readNext()) != null) {
                List<String> newLine = new ArrayList<>(List.of(nextLine));
                newLine.add(2, getGenreForBook(newLine.get(1)));
                writer.writeNext(newLine.toArray(new String[0]));
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
    private static String getGenreForBook(String title) {
        try {
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + URLEncoder.encode(title, StandardCharsets.UTF_8);

            URL url = new URI(apiUrl).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Take the genre from the API response
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray items = jsonResponse.getJSONArray("items");
            if (!items.isEmpty()) {
                JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");
                if (volumeInfo.has("categories")) {
                    JSONArray categories = volumeInfo.getJSONArray("categories");
                    if (!categories.isEmpty()) {
                        return categories.getString(0);
                    }
                }
            }
            return "Unknown"; // Return "Unknown" if genre is not found
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Unknown"; // Return "Unknown" in case of an error
        }
    }
}
