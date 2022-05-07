import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Programm for parsing an Wikipedia API and counting how often the topic is mentioned.
 * @author Paskal Drelichowski
 * @version 1.0
 */

public class Wikipedia 
{
	private static HttpURLConnection conn;

	@Test
    public void amountWordInText()
    {
		assertEquals(6, countHowOften("test test teststring asd asdasd testtestasdasd test teyt tesd","test"));
		assertEquals(8, countHowOften("dedederdesdendddede des d e e d d d e","de"));
		String topic="Nico Ditch";	
		assertEquals(4,countHowOften(httpGetResponse(topic),topic));
		String topic2="Si Ronda";
		assertEquals(6,countHowOften(httpGetResponse(topic2),topic2));
	}

	/**
 	* Gets an JSON File out of an API Wikipedia Request
	* @author https://medium.com/javarevisited/how-to-send-http-get-request-and-parse-json-data-into-string-using-java-3c0cf7eeebbc
 	* @param String Topic for API Search
	* @return String of result
 	*/
    public static String httpGetResponse(String topic)
    {
		//Replaces Whitespaces with "_" 
		topic=topic.replaceAll("\\s+","_");
		BufferedReader reader;
		String line;
		StringBuilder responseContent = new StringBuilder();
		
		try{
			URL url = new URL("https://en.wikipedia.org/w/api.php?action=parse&section=0&prop=text&format=json&page="+topic);
			conn = (HttpURLConnection) url.openConnection();
			
			// Request setup
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
			conn.setReadTimeout(5000);
			
			// Test if the response from the server is successful
			int status = conn.getResponseCode();
			if (status >= 300) 
			{
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				while ((line = reader.readLine()) != null) 
				{
					responseContent.append(line);
				}
				reader.close();
			}
			else 
			{
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line = reader.readLine()) != null) 
				{
					responseContent.append(line);
				}
				reader.close();
			}
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			conn.disconnect();
		}
        return responseContent.toString();
    }

	/**
 	* Counting how often a word was found in a text.
 	* @param String Text to seach a word
	* @param String Searchword to find
	* @return Int of the Count
 	*/
	public static int countHowOften(String text, String word)
	{
		int amount=0;
		for(int i=0; i<text.length();i++)
		{
			for(int j=0;j<word.length();j++)
			{
				if(text.charAt(i)==word.charAt(j))
				{
					if(j==word.length()-1)
					{
						amount++;
						break;
					}
					i++;
				}
				else
				{
					break;						
				}
			}
		}
		return amount;
	}
}