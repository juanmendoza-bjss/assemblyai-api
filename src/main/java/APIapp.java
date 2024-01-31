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
        String jsonRequest = gson.toJson(transcript);
//        System.out.println(jsonRequest);

        // POST Request builder
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(postRequestURI)
                .header("Authorization", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        // Calling API with a POST Request
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(postResponse.body());
    }
}
