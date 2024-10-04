package org.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        DataFetcher dataFetcher = new DataFetcher();
        JsonFormatter jsonFormatter = new JsonFormatter();
        MapViewer mapViewer = new MapViewer();

        try {
            String uri = "https://nominatim.openstreetmap.org/search?format=json&q=Roermond";
            String rawData = dataFetcher.fetchData(uri);
            List<Map<String, Object>> structuredData = jsonFormatter.parseJson(rawData);
            List<Map<String, String>> coordinates = jsonFormatter.extractCoordinates(structuredData);
            mapViewer.displayMap(coordinates);
            logger.info("Data fetched and displayed successfully");
        } catch (IOException | InterruptedException e) {
            logger.error("Error occurred while fetching data", e);
        }
    }
}