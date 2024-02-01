import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIapp {
    public static void main (String[] args) throws Exception {
        String url = "https://github.com/AssemblyAI-Examples/audio-examples/raw/main/20230607_me_canadian_wildfires.mp3";

        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("API_KEY");

        String baseURI = "https://api.assemblyai.com/v2";
        URI postRequestURI = new URI(baseURI + "/transcript");

        Transcript transcript = new Transcript();
        transcript.setAudio_url("https://github.com/AssemblyAI-Examples/audio-examples/raw/main/20230607_me_canadian_wildfires.mp3");

        Gson gson = new Gson();

        // POST Request builder
        String jsonRequest = gson.toJson(transcript);
        System.out.println(jsonRequest);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(postRequestURI)
                .header("Authorization", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        // Calling API with a POST Request
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(postResponse.body());

        // GET Request builder
        transcript = gson.fromJson(postResponse.body(), Transcript.class);
        String id = transcript.getId();
        System.out.println("POST request id: " + id);

        URI getRequestURI = new URI(baseURI + "/transcript/" + id);

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(getRequestURI)
                .header("Authorization", apiKey)
                .build();

        // polling the status of POST request
        while (true) {
            // Calling API with a GET Request
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
//            System.out.println(getResponse.body());
            transcript = gson.fromJson(getResponse.body(), Transcript.class);

            System.out.println(transcript.getStatus());

            if ("completed".equals(transcript.getStatus()) || "error".equals(transcript.getStatus())) {
                break;
            }
            Thread.sleep(1000); // Holds execution for 1s before querying status again.
        }

        System.out.println("Transcription completed!");
        System.out.println(transcript.getText());
    }
}
