package simple;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URI;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.text.Segment;
import WavManagement.WavFile;


public class WaveSplit{

	URI path;
	WavFile wavfile;

//wav edit campus にmain ありmouselistenerを実行し、Event駆動型へ変更
 
    public void WaveSplit(URI file_path){
    	path = file_path;
    	wavfile = new WavFile();
    	wavfile.readFile(new File(file_path));
    	//WavFile.java  readstream()　-> wavfile initでAudioFileを読み込んでいる、。
    }

	//SegmentListクラスのSplit関数で、Endタイムと、StartTimeを抽出し、
	//SegmentのArrayListとして追加して管理している。
    //
	//SegmentListのSplitは、WavControlのSplitから、wavfile.list.split()
	//で呼び出す。WavControlのSplitの中に閾値などが設定されている。
	
	//Split関数は、どこからも呼ばれていないので、呼び出し方法は考える必要がアル。
	//閾値（threshould）は、関数呼び出し時につかえるのでちょうどがよい。
	

    
    //WavControl.javaから呼ばれる　-> SegmentList.javaの中のSplitを呼び出す処理
    public void split(int threshold, int interval, int duration)
    {
    	wavfile.list.split(threshold, interval, duration);
    }


	//WavSplit.javaのSaveSegment()にある、AudioSystem.Writeが分割したファイルを作り出している。
	//wavfile.listにある、StartとEndの時間をもとに、Audioの切り出しを管理している。
	//ここでいう、wavfile.listは、SegmentListクラスのオブジェクト
	//saveAllで、listの数を抽出し、ループですべてが保存されている。
	//SaveAllは、ボタンを押したときに呼び出される。

    
    
	public void OutputSplittedFile(){
	

		 try
		     {
			 String outputFile="";
			 int nSegments=wavfile.list.size();
			 for(int i=0;i<nSegments;i++)
			     {
				 if(wavfile.fileName.toLowerCase().endsWith(".wav"))
				     outputFile=wavfile.fileName.substring(0,wavfile.fileName.length()-4)+"-";
				 int k=0;
				 if(i+1>=9)
				     k=(int)Math.log10(i+1);
				 for(int j=k;j<(int)Math.log10(nSegments);j++)
				     {
					 outputFile+="0";
				     }
				 outputFile+=(i+1)+".wav";
				 saveSegment(i,outputFile);
			     }
		     }
		 catch(Exception e){e.printStackTrace();}
		
		
	}

	

    public void saveSegment(int i, String outputFile)
    {
	 try
	     {
		 int frameSize=wavfile.audioFormat.getFrameSize();
		 WavManagement.Segment s = wavfile.list.get(i);
		 long start=s.start;
		 long end=s.end;
		 start-=start%frameSize;
		 end-=end%frameSize;
		 int length=(int)(end-start);
		 ByteArrayInputStream byteStream=new ByteArrayInputStream(wavfile.list.getBuffer(),(int)start,length);
		 AudioInputStream stream=new AudioInputStream(byteStream, wavfile.audioFormat, length/frameSize);		    
		 File file=new File(outputFile);
		 System.out.println("Writing to "+outputFile);
		 AudioSystem.write(stream,AudioFileFormat.Type.WAVE,file);
	     }
	 catch(Exception e){e.printStackTrace();}
    }
}


