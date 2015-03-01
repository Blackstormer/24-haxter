import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Gets first sentences and titles from Wikipedia articles for use in questions
 * @author John Morach
 *
 */

public class WikipediaQuestions {
	/**
	 * Returns the first sentence (in most cases) of a Wikipedia article based on its page name
	 * @param pageName Page name of the article
	 * @return Array containing the title and first sentence of the article
	 */
	public static String[] getFirstSentence(String pageName) {
		String url = "http://en.wikipedia.org/wiki/" + pageName;
		try {
			// Establishes connection with page
			URLConnection connection = new URL(url).openConnection();
			connection.setRequestProperty("Accept-Charset", java.nio.charset.StandardCharsets.UTF_8.name());
			InputStream response = connection.getInputStream();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, java.nio.charset.StandardCharsets.UTF_8.name()))) {
		        // First paragraph found
				boolean found = false;
				// First paragraph end found
		        boolean done = false;
		        String paragraph = "";
		        String title = null;
		        boolean titleFound = false;
		        
		        // Loops through all lines until title and first paragraph found
				for (String line; (line = reader.readLine()) != null && !(found && done && titleFound);) {
					// Adds the title
					if (!titleFound && line.contains("<title>")) {
						title = line;
					}
					
					// Beginning of the paragraph
		            if (!found && line.contains("<p>")) {
		            	found = true;
		            	paragraph += line;
		            }
		            
		            // End of the paragraph
		            while (found && !done) {
		            	if(line.contains("</p>"))
		            		done = true;
		            	paragraph += line;
		            }
		        }
				// Removes HTML tags
				paragraph = paragraph.replaceAll("<[^>]*>", "");
				// Removes citations
				paragraph = paragraph.replaceAll("\\[\\s*([^\\[]*?)\\s*\\]", "");
				paragraph = paragraph.replaceAll("&#160;", " ");
				paragraph = paragraph.trim();
				
				// Attempts to find where the sentence ends
				int firstSentence = paragraph.indexOf(". ");
				paragraph = paragraph.substring(0, firstSentence + 1);
				
				// Returns title and first sentence
				return new String[] {title.substring(7, title.length() - 42), paragraph};
		    }
		} catch (Exception e) {}
		return null;
	}
	
	/**
	 * Returns only the title
	 * @return Title of a random Wikipedia page
	 */
	public static String getOtherAnswer() {
		return getFirstSentence("Special:Random")[0];
	}
}