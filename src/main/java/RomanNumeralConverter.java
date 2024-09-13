import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class RomanNumeralConverter {
    public static void main(String[] args) throws IOException {
        // Create an HTTP server that listens on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Create a context for the "/api/romannumeral" endpoint
        server.createContext("/api/romannumeral", new RomanNumeralHandler());

        // Start the server
        server.start();
        System.out.println("Server started on http://localhost:8080");
    }

    // Handler for the endpoint
    static class RomanNumeralHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Check if the request method is GET
            if ("GET".equals(exchange.getRequestMethod())) {
                // Extract the query string from the request URI
                String query = exchange.getRequestURI().getQuery();
                if (query != null && query.startsWith("query=")) {
                    String input = query.split("=")[1];
                    try {
                        int number = Integer.parseInt(input);
                        // Validate that the number is within the acceptable range (1-3999)
                        if (number < 1 || number > 3999) {
                            sendErrorResponse(exchange, "Input must be between 1 and 3999");
                        } else {
                            String roman = RomanNumeralHelper.convertToRoman(number);
                            sendJsonResponse(exchange, input, roman);
                        }
                    } catch (NumberFormatException e) {
                        sendErrorResponse(exchange, "Invalid input: must be an integer.");
                    }
                } else {
                    sendErrorResponse(exchange, "Missing or incorrect query parameter.");
                }
            } else {
                sendErrorResponse(exchange, "Only GET requests are supported.");
            }
        }

        private void sendJsonResponse(HttpExchange exchange, String input, String output) throws IOException {
            // Manually build JSON response 
            // TODO - need to figure out how to configure JSONObject properly 
            String jsonResponse = "{"
                + "\"input\": \"" + input + "\", "
                + "\"output\": \"" + output + "\""
                + "}";

            byte[] response = jsonResponse.getBytes();
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }

        private void sendErrorResponse(HttpExchange exchange, String message) throws IOException {
            byte[] response = message.getBytes();
            // sending a 400 for bad responses
            exchange.sendResponseHeaders(400, response.length);
            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        }

        // Static inner class for RomanNumeralHelper
        public static class RomanNumeralHelper {
            public static String convertToRoman(int number) {
                if (number < 1 || number > 3999) {
                    // still learning java, but thought this would be a good exception class to use. not sure if best practice, but I chose this since it's purpose is to indicate that a method has been provided with an argument that is not appropriate.
                    throw new IllegalArgumentException("Input must be between 1 and 3999");
                }

                // Arrays representing Roman numeral symbols for thousands, hundreds, tens, and 1-9 which I call units
                String[] thousands = {"", "M", "MM", "MMM"};
                String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
                String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
                String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

                // Construct the Roman numeral string by concatenating the appropriate symbols
                // Determine the Roman numeral parts for thousands, hundreds, tens, and units
                return thousands[number / 1000] +
                    hundreds[(number % 1000) / 100] +
                    tens[(number % 100) / 10] +
                    units[number % 10];
            }
        }
    }
}
