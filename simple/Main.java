package simple;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {

//	static String content_type_wav = "audio/wav; rate=16000";
	static String content_type_l16_16000 = "audio/l16; rate=16000"; 
	static String content_type_l16_44100 = "audio/l16; rate=44100"; 
	static String content_type_flac = "audio/x-flac; rate=44100";

	static int threshold_8bit = 12; //in 128
	static int init_interval_msec = 100; //msec
	static int min_duration_msec = 200; //msec
	static int max_duration_msec = 6000; //msec
	

	public static void main(String[] args) throws Exception {
		
/*Spilit wav file*/
		URI file_path = new URI("file:///home/morninng/audio/emma_simple_super_noise.wav");
		File audio_file = new File(file_path);
		AudioFileFormat audio_file_format = AudioSystem.getAudioFileFormat(audio_file);
		AudioFormat audio_format = audio_file_format.getFormat();
		float sample_rate = audio_format.getSampleRate();
		Output_Audio_Format(file_path);
		WaveSplit wavsplit = new WaveSplit(file_path);
		AudioInputStream audio_input_stream = AudioSystem.getAudioInputStream(audio_file);
		long frame_len = audio_input_stream.getFrameLength();

//convert actual second to number of flame
		

		int init_interval_frame = (int)sample_rate * init_interval_msec /1000; //frame
		int min_duration_frame = (int)sample_rate * min_duration_msec /1000; //frame
		int max_duration_frame = (int)sample_rate * max_duration_msec / 1000; //frame
		
//split the audio file
		wavsplit.split(threshold_8bit, init_interval_frame, min_duration_frame, max_duration_frame, frame_len);
		wavsplit.OutputSplittedFile();
		
/*Transcription */	
		String speech_language = "en-us";
		String conetnt_type = content_type_l16_44100; 

	//	GoogleTranscript obj =   new GoogleTranscript(file_path, speech_language, conetnt_type);
	//	obj.sendPost();
	}
	
	static void Output_Audio_Format(URI file_path) throws UnsupportedAudioFileException, IOException{

		File audio_file = new File(file_path);
		AudioFileFormat audio_file_format = AudioSystem.getAudioFileFormat(audio_file);
		AudioFormat audio_format = audio_file_format.getFormat();
		System.out.println("channels = " + audio_format.getChannels());
		System.out.println("FrameRate = " + audio_format.getFrameRate());
		System.out.println("FrameSize = " + audio_format.getFrameSize());

		System.out.println("SampleRate = " + audio_format.getSampleRate());
		System.out.println("isBigEndian = " + audio_format.isBigEndian());
		System.out.println("String= " + audio_format.toString());
		
		AudioInputStream audio_input_stream = AudioSystem.getAudioInputStream(audio_file);
		long audio_frame_length = audio_input_stream.getFrameLength();
		System.out.println("Audio Frame Length = " + audio_frame_length);
		float sample_rate = audio_format.getSampleRate();
		System.out.println("audio duration = " +   (audio_frame_length/sample_rate) + "second");
		
		/*output the timing of emma_simple.wav */
		
		float sampleRate = audio_format.getSampleRate();
		
		System.out.println("0.22630 sec " +  sampleRate * 0.22630);
		System.out.println("0.8970 sec " +  sampleRate * 0.8970);
		System.out.println("1.73040 sec " +  sampleRate * 1.73040);
		System.out.println("2.7230 sec " +  sampleRate * 2.7230);
		
	}
}
