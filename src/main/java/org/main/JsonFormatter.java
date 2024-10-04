package org.main;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonFormatter {
    public List<Map<String, Object>> parseJson(String responseBody) {
        JSONArray jsonArray = new JSONArray(responseBody);
        List<Map<String, Object>> dataList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Map<String, Object> dataMap = new HashMap<>();

            for (String key : jsonObject.keySet()) {
                if (key.equals("boundingbox")) {
                    JSONArray boundingBoxArray = jsonObject.getJSONArray("boundingbox");
                    List<String> boundingBoxList = new ArrayList<>();
                    for (int j = 0; j < boundingBoxArray.length(); j++) {
                        boundingBoxList.add(boundingBoxArray.getString(j));
                    }
                    dataMap.put(key, boundingBoxList);
                } else {
                    dataMap.put(key, jsonObject.get(key));
                }
            }
            dataList.add(dataMap);
        }

        return dataList;
    }

    public List<Map<String, String>> extractCoordinates(List<Map<String, Object>> data) {
        List<Map<String, String>> coordinates = new ArrayList<>();
        for (Map<String, Object> entry : data) {
            Map<String, String> coord = new HashMap<>();
            coord.put("lat", (String) entry.get("lat"));
            coord.put("lon", (String) entry.get("lon"));
            coordinates.add(coord);
        }
        return coordinates;
    }
}