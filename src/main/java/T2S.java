import com.google.cloud.texttospeech.v1beta1.*;
import com.google.protobuf.ByteString;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class T2S {
    public static void synthesizeText(String text)
            throws Exception {
        // Instantiates a client
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Set the text input to be synthesized
            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(text)
                    .build();

            // Build the voice request
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode("ru") // languageCode = "en_us"
                    .setSsmlGender(SsmlVoiceGender.NEUTRAL) // ssmlVoiceGender = SsmlVoiceGender.FEMALE

                    .build();

            // Select the type of audio file you want returned
            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3) // MP3 audio.
                    .build();

            // Perform the text-to-speech request
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice,
                    audioConfig);

            // Get the audio contents from the response
            ByteString audioContents = response.getAudioContent();

            // Write the response to the output file.
            try (OutputStream out = new FileOutputStream("output.mp3")) {
                out.write(audioContents.toByteArray());


                System.out.println("Audio content written to file \"output.mp3\"");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        synthesizeText("Привет");
    }
}
