import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Startup {
    public static void main(String[] args) throws IOException {
        // Create an HTTP server that listens on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Create a context for the "/api/hello" endpoint
        server.createContext("/api/hello", new HelloWorld());

        // Start the server
        server.start();
        System.out.println("Server started on http://localhost:8080");
    }

    // Handler for the endpoint where my business logic will likely live
    static class HelloWorld implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Get the query parameter "name" from the request
            String query = exchange.getRequestURI().getQuery();
            String name = "World";
            if (query != null && query.startsWith("name=")) {
                name = query.substring("name=".length());
            }

            // Prepare the response
            String response = "Hello, " + name + "!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}