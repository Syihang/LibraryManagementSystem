package com.suyihang.AI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;


public class VivoAuth {

    private static final Logger logger = LoggerFactory.getLogger(VivoAuth.class);
    private static final Charset UTF8 = StandardCharsets.UTF_8;

    private static String generateRandomString(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }


    private static String generateCanonicalQueryString(String queryParams) throws UnsupportedEncodingException {
        if (queryParams == null || queryParams.length() <= 0) {
            return "";
        }

        HashMap<String, String> params = new HashMap<>();
        String[] param = queryParams.split("&");
        for (String item : param) {
            String[] pair = item.split("=");
            if (pair.length == 2) {
                params.put(pair[0], pair[1]);
            } else {
                params.put(pair[0], "");
            }
        }
        SortedSet<String> keys = new TreeSet<>(params.keySet());
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String key : keys) {
            if (!first) {
                sb.append("&");
            }
            String item = URLEncoder.encode(key, UTF8) + "=" + URLEncoder.encode(params.get(key), UTF8);
            sb.append(item);
            first = false;
        }

        return sb.toString();
    }

    private static String generateSignature(String appKey, String signingString) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret = new SecretKeySpec(appKey.getBytes(UTF8), mac.getAlgorithm());
            mac.init(secret);
            return Base64.getEncoder().encodeToString(mac.doFinal(signingString.getBytes()));
        } catch (Exception err) {
            logger.error("create sign exception", err);
            return "";
        }
    }


    public static HttpHeaders generateAuthHeaders(String appId, String appKey, String method, String uri, String queryParams)
            throws UnsupportedEncodingException {
        String nonce = generateRandomString(8);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String canonical_query_string = generateCanonicalQueryString(queryParams);
        String signed_headers_string = String.format("x-ai-gateway-app-id:%s\n" +
                "x-ai-gateway-timestamp:%s\nx-ai-gateway-nonce:%s", appId, timestamp, nonce);
//        System.out.println(signed_headers_string);
        String[] fields = {
                method,
                uri,
                canonical_query_string,
                appId,
                timestamp,
                signed_headers_string
        };
        final StringBuilder buf = new StringBuilder(fields.length * 16);
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                buf.append("\n");
            }
            if (fields[i] != null) {
                buf.append(fields[i]);
            }
        }
//        System.out.println(buf.toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-AI-GATEWAY-APP-ID", appId.toString());
        headers.add("X-AI-GATEWAY-TIMESTAMP", timestamp.toString());
        headers.add("X-AI-GATEWAY-NONCE", nonce.toString());
        headers.add("X-AI-GATEWAY-SIGNED-HEADERS", "x-ai-gateway-app-id;x-ai-gateway-timestamp;x-ai-gateway-nonce");
        headers.add("X-AI-GATEWAY-SIGNATURE", generateSignature(appKey, buf.toString()));
        return headers;
    }

    public static void printHeaders(HttpHeaders headers) {
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}