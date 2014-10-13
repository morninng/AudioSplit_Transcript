package simple;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.net.ssl.*;


public class GoogleTranscript {

	URI path;
	String lang;
	String content_type;
	
	GoogleTranscript(URI file_path, String speech_language, String ContentType){
		path = file_path;
		lang = speech_language;
		content_type = ContentType;
	}

	public void sendPost() throws Exception {
	    String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.121 Safari/535.2";
	    String url = "https://www.google.com/speech-api/v2/recognize?output=json&lang=en-US&key=AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw&client=chromium&maxresults=6&pfilter=2";
        String url_first_half = "https://www.google.com/speech-api/v2/recognize?output=json&lang=";
	    String url_last_half = "&key=AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw&client=chromium&maxresults=6&pfilter=2";
	    url = url_first_half + lang + url_last_half;
	    
	    URL obj = new URL(url);
	    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

	    // add reuqest header
	    con.setRequestMethod("POST");
	    con.setRequestProperty("User-Agent", USER_AGENT);
	    con.setRequestProperty("Content-Type", content_type );
	    con.setRequestProperty("AcceptEncoding", "gzip,deflate,sdch");

	    // Send post request
	    con.setDoOutput(true);
	    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	    wr.write(Files.readAllBytes(Paths
	            .get(path)));
	    
	    System.out.println("just before sending the data to server");
	    
	    wr.flush();
	    wr.close();

	    int responseCode = con.getResponseCode();
	    System.out.println("\nSending 'POST' request to URL : " + url);
	    System.out.println("Response Code : " + responseCode);

	    BufferedReader in = new BufferedReader(new InputStreamReader(
	            con.getInputStream()));
	    String inputLine;
	    StringBuffer response = new StringBuffer();

	    while ((inputLine = in.readLine()) != null) {
	        response.append(inputLine);
	    }
	    in.close();

	    // print result
	    System.out.println(response.toString());
	}
}
