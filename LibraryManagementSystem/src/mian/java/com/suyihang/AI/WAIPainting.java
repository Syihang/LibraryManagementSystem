package com.suyihang.AI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class WAIPainting {
    final static String APP_ID = "3034198228";
    final static String APP_KEY = "nxJqCucwgccxQwGj";

    public static String task_submit(String input) throws UnsupportedEncodingException {
        final String METHOD = "POST";
        final String URI = "/api/v1/task_submit";
        final String DOMAIN = "api-ai.vivo.com.cn";

        Map<String, Object> data = new HashMap<>();
        data.put("height", 768);
        data.put("width", 576);
        data.put("prompt", input);
        data.put("styleConfig", "55c682d5eeca50d4806fd1cba3628781");

        String requestBody = JSON.toJSONString(data);

        Map<String, Object> query = new HashMap<>();
        String queryString = mapToQueryString(query);

        HttpHeaders headers = VivoAuth.generateAuthHeaders(APP_ID, APP_KEY, METHOD, URI, queryString);
        headers.add("Content-Type", "application/json");

        String url = String.format("http://%s%s", DOMAIN, URI);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = JSON.parseObject(response.getBody());
            JSONObject result = jsonResponse.getJSONObject("result");
            return result.getString("task_id");
        } else {
            return null;
        }
    }

    public static String taskProgress(String taskId) throws UnsupportedEncodingException {
        final String METHOD = "GET";
        final String URI = "/api/v1/task_progress";
        final String DOMAIN = "api-ai.vivo.com.cn";

        Map<String, Object> data = new HashMap<>();
        data.put("task_id", taskId);
        String queryString = mapToQueryString(data);

        HttpHeaders headers = VivoAuth.generateAuthHeaders(APP_ID, APP_KEY, METHOD, URI, queryString);
        String url = String.format("http://%s%s?%s", DOMAIN, URI, queryString);
        headers.add("Content-Type", "application/json");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public static String waitForImage(String taskId) throws UnsupportedEncodingException, InterruptedException {
        while (true) {
            Thread.sleep(5000);
            String progressResponse = taskProgress(taskId);
            JSONObject jsonResponse = JSON.parseObject(progressResponse);
            JSONObject result = jsonResponse.getJSONObject("result");
            boolean finished = result.getBoolean("finished");
            if (finished) {
                JSONArray imagesUrlArray = result.getJSONArray("images_url");
                if (imagesUrlArray.size() > 0) {
                    return imagesUrlArray.getString(0);
                }
            }
        }
    }

    public static String mapToQueryString(Map<String, Object> map) {
        if (map.isEmpty()) {
            return "";
        }
        StringBuilder queryStringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (queryStringBuilder.length() > 0) {
                queryStringBuilder.append("&");
            }
            queryStringBuilder.append(entry.getKey());
            queryStringBuilder.append("=");
            queryStringBuilder.append(entry.getValue());
        }
        return queryStringBuilder.toString();
    }


}
