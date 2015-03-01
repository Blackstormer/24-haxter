import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WikipediaQuestions {
	private static String getFirstSentence(String pageName) {
		//"http://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=content&format=json&&titles=United_States"
		String url = "http://en.wikipedia.org/wiki/" + pageName;
		try {
			URLConnection connection = new URL(url).openConnection();
			connection.setRequestProperty("Accept-Charset", java.nio.charset.StandardCharsets.UTF_8.name());
			InputStream response = connection.getInputStream();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, java.nio.charset.StandardCharsets.UTF_8.name()))) {
		        boolean found = false;
		        boolean done = false;
		        String paragraph = "";
		        String title = null;
		        boolean titleFound = false;
				for (String line; (line = reader.readLine()) != null;) {
					if (!titleFound && line.contains("<title>")) {
						title = line;
					}
		            if (!found && line.contains("<p>")) {
		            	found = true;
		            	paragraph += line;
		            }
		            while (found && !done) {
		            	if(line.contains("</p>"))
		            		done = true;
		            	paragraph += line;
		            }
		        }
				paragraph = paragraph.replaceAll("<[^>]*>", "");
				paragraph = paragraph.replaceAll("\\[\\s*([^\\[]*?)\\s*\\]", "");
				paragraph = paragraph.replaceAll("&#160;", " ");
				paragraph = paragraph.trim();
				int firstSentence = paragraph.indexOf(". ");
				paragraph = paragraph.substring(0, firstSentence + 1);
				System.out.println(title.substring(7, title.length() - 42));
				return paragraph;
		    }
		} catch (Exception e) {}
		return null;
	}
	
	/*
	public static void main(String[] args) {
		System.out.println(getFirstSentence("Special:Random"));
	} */
}