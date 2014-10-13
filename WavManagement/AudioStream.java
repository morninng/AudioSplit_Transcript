package WavManagement;
// AudioStream.java  -- Reads AudioStream.

// Copyright (c) 2004 - 2014 easai

// Author: easai 
// Created: Sat Jan 11 10:43:37 2014
// Keywords: 

// This file is not part of GNU Emacs.

// AudioStream.java is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2, or (at your option)
// any later version.

// This software is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program; see the file COPYING.  If not, write to the
// Free Software Foundation, Inc., 59 Temple Place - Suite 330,
// Boston, MA 02111-1307, USA.

// Commentary:
//
//
//

// Code:


import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

public class AudioStream extends Thread
{
    SourceDataLine line;
    int start;
    int length;
    byte[] buf;
    AudioInputStream audioInputStream=null;
    AudioFormat audioFormat=null;

    int index=0;

    boolean debug=false;
    
    AudioStream(SourceDataLine line, byte[] buf, int start, int length)
    {
	this.line=line;
	this.start=start;
	this.index=start;
	this.length=length;
	this.buf=buf;

	start();
    }
    public void run()
    {
	if(this==currentThread())
	    {
		line.write(buf,index,length);
		line.drain();

		/*	
		do
		    {
			line.start();
			System.out.print("*");
			int bufferSize=0;
			int end=0;
			if(0<(bufferSize=line.available()))
			    {
				index-=(index%frameSize);
				end=Math.min(bufferSize,start+length-index);		
				
				end-=(end%frameSize);			
				
				if(debug)
				    System.out.println("line is available "+bufferSize);
				line.write(buf,index,end);
				line.drain();				

				index+=end;
			    }
			line.stop();
		    }
		    while(index<start+length);
		*/
	    }
    }
}


// AudioStream.java ends here
