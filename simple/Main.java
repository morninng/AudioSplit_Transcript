package simple;

import java.io.IOException;
import java.net.URI;

public class Main {

//	static String content_type_wav = "audio/wav; rate=16000";
	static String content_type_l16_16000 = "audio/l16; rate=16000"; 
	static String content_type_l16_44100 = "audio/l16; rate=44100"; 
	static String content_type_flac = "audio/x-flac; rate=44100";


	public static void main(String[] args) throws Exception {
		
		
/**/	
		URI file_path = new URI("file:///home/morninng/audio/BanDisplaySacrilegiousArts.wav");
		String speech_language = "en-us";
		String conetnt_type = content_type_l16_44100; 

		GoogleTranscript obj =   new GoogleTranscript(file_path, speech_language, conetnt_type);
		obj.sendPost();

	}
}
