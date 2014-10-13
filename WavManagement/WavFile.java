package WavManagement;

// WavFile.java  -- WavFile class reads WAV files and stores Segment and SegmentList class info.


import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

public class WavFile
{
    public SegmentList list;
    public String fileName="";
    AudioInputStream audioInputStream=null;
    public AudioFormat audioFormat=null;
    long nBytesRead=0;
    long frameSize;
    byte[] buf;
    AudioStream stream;
    SourceDataLine line=null;
    DataLine.Info info;

    public void readURL(URL url)
    {
	try
	    {
		InputStream stream=url.openConnection().getInputStream();
		readStream(stream);
	    }
	catch(Exception e){e.printStackTrace();}
    }

    public void readFile(File file)
    {
	try
	    {
		if(file.exists())
		    {
			FileInputStream stream=new FileInputStream(file);
			readStream(stream);
		    }
	    }
	catch(Exception e){e.printStackTrace();}
    }

    public void readStream(InputStream stream)
    {
	if(stream==null)
	    return;
	try
 	    {
		System.out.println("WavFile.java");
		InputStream bufferedIn=new BufferedInputStream(stream);
		audioInputStream=AudioSystem.getAudioInputStream(bufferedIn);
		buf=new byte[audioInputStream.available()];
		audioInputStream.read(buf,0,buf.length);
		audioFormat=audioInputStream.getFormat();
		frameSize=audioFormat.getFrameSize();
		nBytesRead=frameSize*audioInputStream.getFrameLength();
		list=new SegmentList(buf,nBytesRead);
		info=new DataLine.Info(SourceDataLine.class,audioFormat);
	    }
	catch(Exception e){e.printStackTrace();}
    }

    public void playSegment(int index)
    {
	Segment s=list.get(index);
	long start=s.start;
	long end=s.end;
	start-=start%frameSize;
	end-=end%frameSize;
	long length=end-start;	
	playSegment(start, length);
    }

    public void playSegment(long start, long length)
    {
	try
	    {

		//line.write(buf,(int)start,(int)length);
			line=(SourceDataLine)AudioSystem.getLine(info);
			line.open(audioFormat);
			line.start();
		stream=new AudioStream(line, buf,(int)start,(int)length);
		//	stream.run();

	    }
	catch(Exception e)
	    {
		e.printStackTrace();
	    }
    }

    public void quit()
    {
	try
	    {
		line.close();
	    }	
	catch(Exception e){e.printStackTrace();}
    }
    
    public void playAll()
    {
	playSegment(0,nBytesRead);
    }

    public void stopLine()
    {
	try
	    {
		line.stop();
	    }	
	catch(Exception e){e.printStackTrace();}
    }
}


// WavFile.java ends here
