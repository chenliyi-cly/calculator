package com.chenliyi;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Backend calculator HTTP service.
 * Built on the JDK built-in HttpServer (no extra framework dependency).
 * Listens on port 8000 and exposes GET /calculate?a=..&b=..&op=..
 */
public class WebServer {

    public static void main(String[] args) throws Exception {
        // Create an HTTP server bound to port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        // Register the /calculate route
        server.createContext("/calculate", WebServer::handleCalculate);
        server.setExecutor(null);
        server.start();
        System.out.println("Calculator backend started on port 8000");
    }

    /** Handle every /calculate request: parse query, compute, return JSON. */
    static void handleCalculate(HttpExchange exchange) throws IOException {
        // Allow cross-origin access from the web container (different port)
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

        Map<String, String> params = parseQuery(exchange.getRequestURI().getRawQuery());
        Calculator calculator = new Calculator();
        String body;

        try {
            double a = Double.parseDouble(params.get("a"));
            double b = Double.parseDouble(params.get("b"));
            String op = params.get("op");
            double result;

            switch (op) {
                case "+" -> result = calculator.add(a, b);
                case "-" -> result = calculator.sub(a, b);
                case "*" -> result = calculator.mul(a, b);
                case "/" -> result = calculator.div(a, b);
                default -> throw new IllegalArgumentException("Invalid operator");
            }
            body = "{\"result\":" + result + "}";
            sendResponse(exchange, 200, body);
        } catch (Exception e) {
            body = "{\"error\":\"" + e.getMessage().replace("\"", "") + "\"}";
            sendResponse(exchange, 400, body);
        }
    }

    /** Split the URL query string into a key-value map. */
    static Map<String, String> parseQuery(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null) return map;
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            if (kv.length == 2) {
                map.put(URLDecoder.decode(kv[0], StandardCharsets.UTF_8),
                        URLDecoder.decode(kv[1], StandardCharsets.UTF_8));
            }
        }
        return map;
    }

    /** Write the JSON body back to the client. */
    static void sendResponse(HttpExchange exchange, int code, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(code, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}
