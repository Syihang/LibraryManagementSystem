package com.suyihang.AI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WAIDialogue {

    public static String vivogpt(String input) throws Exception {

        String appId = "3034198228";
        String appKey = "nxJqCucwgccxQwGj";
        String URI = "/vivogpt/completions";
        String DOMAIN = "api-ai.vivo.com.cn";
        String METHOD = "POST";
        UUID requestId = UUID.randomUUID();
//        System.out.println("requestId: " + requestId);

        Map<String, Object> map = new HashMap<>();
        map.put("requestId", requestId.toString());
        String queryStr = mapToQueryString(map);

        //构建请求体
        Map<String, Object> data = new HashMap<>();

        data.put("prompt", input);
        data.put("model", "vivo-BlueLM-TB");
        UUID sessionId = UUID.randomUUID();
        data.put("sessionId", sessionId.toString());

        // 设置角色情景
        data.put("systemPrompt", "你是负责管理图书的小助手,当用户输入书籍标题时," +
                "你需要对该书籍写出简要的介绍.同时,你也应该回答用户关于书籍的其他问题");

        Map<String, Object> extra = new HashMap<>();
        extra.put("temperature", 0.2);
//        extra.put("max_new_tokens", 3);  设置最大生成字符数
        data.put("extra", extra);

        printMap(data);

        HttpHeaders headers = VivoAuth.generateAuthHeaders(appId, appKey, METHOD, URI, queryStr);
        headers.add("Content-Type", "application/json");
        //System.out.println(headers);
        String url = String.format("http://%s%s?%s", DOMAIN, URI, queryStr);
        String requsetBodyString = new ObjectMapper().writeValueAsString(data);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        httpHeaders.addAll(headers);
        HttpEntity<String> requestEntity = new HttpEntity<>(requsetBodyString, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseData = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
            Map<String, Object> text = (Map<String, Object>) responseData.get("data");
            String content = (String) text.get("content");
            //System.out.println("Response body: " + response.getBody());
            //System.out.println("Content: " + content);
            return content;
        } else {
            System.out.println("Error response: " + response.getStatusCode());
            return "Error response:" + response.getStatusCode();
        }

    }


    public static String vivogptLibrary(String input , String sessionId) throws Exception {

        String appId = "3034198228";
        String appKey = "nxJqCucwgccxQwGj";
        String URI = "/vivogpt/completions";
        String DOMAIN = "api-ai.vivo.com.cn";
        String METHOD = "POST";
        UUID requestId = UUID.randomUUID();
//        System.out.println("requestId: " + requestId);

        Map<String, Object> map = new HashMap<>();
        map.put("requestId", requestId.toString());
        String queryStr = mapToQueryString(map);

        //构建请求体
        Map<String, Object> data = new HashMap<>();

        data.put("prompt", input);
        data.put("model", "vivo-BlueLM-TB");
        data.put("sessionId", sessionId);

        // 设置角色情景
        data.put("systemPrompt", "你是负责管理图书的小助手,当用户输入书籍标题时," +
                "你需要对该书籍写出简要的介绍.同时,你也应该回答用户关于书籍的其他问题");


        Map<String, Object> extra = new HashMap<>();
        extra.put("temperature", 0.5);
//        extra.put("max_new_tokens", 3);  设置最大生成字符数
        data.put("extra", extra);

//        printMap(data);

        HttpHeaders headers = VivoAuth.generateAuthHeaders(appId, appKey, METHOD, URI, queryStr);
        headers.add("Content-Type", "application/json");
        //System.out.println(headers);
        String url = String.format("http://%s%s?%s", DOMAIN, URI, queryStr);
        String requsetBodyString = new ObjectMapper().writeValueAsString(data);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        httpHeaders.addAll(headers);
        HttpEntity<String> requestEntity = new HttpEntity<>(requsetBodyString, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseData = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
            Map<String, Object> text = (Map<String, Object>) responseData.get("data");
            String content = (String) text.get("content");
            //System.out.println("Response body: " + response.getBody());
            //System.out.println("Content: " + content);
            return content;
        } else {
            System.out.println("Error response: " + response.getStatusCode());
            return "Error response:" + response.getStatusCode();
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

    // 递归打印 Map 内容
    public static void printMap(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.print(entry.getKey() + ": ");
            if (entry.getValue() instanceof Map) {
                System.out.println();
                printMap((Map<String, Object>) entry.getValue());
            } else {
                System.out.println(entry.getValue());
            }
        }
    }


}
