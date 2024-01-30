import com.assemblyai.api.AssemblyAI;
import io.github.cdimascio.dotenv.Dotenv;

public class App {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("API_KEY");

        AssemblyAI client = AssemblyAI.builder().apiKey(apiKey).build();
    }
}
