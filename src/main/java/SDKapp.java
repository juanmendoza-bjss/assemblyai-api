import com.assemblyai.api.AssemblyAI;
import com.assemblyai.api.resources.transcripts.types.Transcript;
import com.assemblyai.api.resources.transcripts.types.TranscriptStatus;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.File;
import java.io.IOException;

public class SDKapp {
    public static void main(String[] args) throws InterruptedException {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("API_KEY");
        String url = "https://github.com/AssemblyAI-Examples/audio-examples/raw/main/20230607_me_canadian_wildfires.mp3";
        String url2 = "https://www.bbc.co.uk/news/av/uk-68112824";
        File file = new File("./Thirsty.mp4");


        // AssemblyAI SDK HTTP client
        AssemblyAI client = AssemblyAI.builder().apiKey(apiKey).build();

        // Generating the transcript using a file as a source (working code)
        Transcript transcript = null;
        try {
            transcript = client.transcripts().transcribe(file);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        long finish = System.currentTimeMillis() + 10000; // end time
        while (System.currentTimeMillis() < finish) {
            TranscriptStatus status = client.transcripts().get(transcript.getId()).getStatus();
            System.out.println(status);
            Thread.sleep(1000); // Sleep for a second before checking again
        }

//       System.out.println(client.transcripts().get(transcript.getId()).getStatus());

//        System.out.println("Transcription: " + transcript.getText().get()); // Class is Optional. get() returns value

//        // Generating a transcript using a URL as a source
//        Transcript transcript = client.transcripts().transcribe(url);
//
//        // Polling for the status until it's not in progress
//        while (transcript.getStatus().equals(TranscriptStatus.COMPLETED)) {
//            System.out.println("inside while loop");
//            try {
//                System.out.println(transcript.getStatus());
//                Thread.sleep(1000); // Sleep for a second before checking again
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            transcript = client.transcripts().get(transcript.getId()); // Retrieve updated transcript
//        }
//
//        if (transcript.getStatus().equals(TranscriptStatus.ERROR)) {
//            System.err.println(transcript.getError());
//        }
//
//        System.out.println("Transcription: " + transcript.getText().get()); // Class is Optional. get() returns value
    }
}
