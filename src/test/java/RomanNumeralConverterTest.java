import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RomanNumeralConverterTest {

    private static ScheduledExecutorService serverExecutor;
    private static final int PORT = 8080;

    @BeforeAll
    public static void startServer() throws IOException {
        serverExecutor = Executors.newSingleThreadScheduledExecutor();
        serverExecutor.schedule(() -> {
            try {
                RomanNumeralConverter.main(new String[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, TimeUnit.SECONDS);
        
        // Wait a bit to ensure server has started
        try {
            Thread.sleep(2000); // Adjust sleep time as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void stopServer() {
        serverExecutor.shutdownNow();
    }

    @Test
    public void testInvalidQueryParameter() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:" + PORT + "/api/romannumeral?query=abcd"))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
        assertTrue(response.body().contains("Invalid input: must be an integer."));
    }

    @Test
    public void testMissingQueryParameter() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:" + PORT + "/api/romannumeral"))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode());
        assertTrue(response.body().contains("Missing or incorrect query parameter."));
    }

    @Test
    public void testInvalidRequestMethod() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:" + PORT + "/api/romannumeral?query=2024"))
            .POST(HttpRequest.BodyPublishers.noBody())
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, response.statusCode()); // Or another appropriate status code if you handle POST differently
        assertTrue(response.body().contains("Only GET requests are supported."));
    }
}
