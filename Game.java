import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Main class of the game
 * @author John Morach
 *
 */

public class Game {
	/**
	 * Panel displaying questions and answers
	 */
	private static ImagePanel questionPanel;
	
	private static JFrame window;
	private static JPanel cardsPanel;
	private static JLabel title;
	private static JRadioButton answerA, answerB, answerC, answerD;
	private static JRadioButton[] buttons;
	private static JButton submit;
	private static ButtonGroup choices;
	
	/**
	 * Current answers that are displayed
	 */
	private static ArrayList<String> currentAnswers;
	/**
	 * Index of the correct answer
	 */
	private static int correctAnswer;
	
	/**
	 * Component displaying text between stages
	 */
	private static ImageCanvas canvas;
	
	// Fonts used
	public static Font augustus18 = null;
	public static Font augustus16 = null;
	public static Font augustus30 = null;
	
	// Used in determining which component is visible
	private static boolean questionsVisible = true;
	
	/**
	 * Manages the different stages of the game
	 */
	public static StageHandler stages = new StageHandler();
	
	// Button images for display
	public static final String[] buttonNames = new String[] {"button_fire", "button_tools", "button_agriculture", "button_writing", "button_metal", "button_money", "button_math", "button_engineering", "button_vaccines", "button_electricity", "button_internet", "button_future"};
	public static BufferedImage button = ImageLoader.getImage(buttonNames[0]);
	
	/**
	 * Sets up window and starts game
	 * @param args
	 */
	public static void init() {
		try {
			augustus30 = augustus18 = augustus16 = Font.createFont(Font.TRUETYPE_FONT, new File(Game.class.getClass().getResource("/fonts/AUGUSTUS.TTF").getFile()));
		} catch (Exception e) {}
		
		// Sets proper font sizes
		if (augustus30 != null) {
			augustus30 = augustus30.deriveFont(30.0f);
			augustus18 = augustus18.deriveFont(18.0f);
			augustus16 = augustus16.deriveFont(16.0f);
		}
		
		window = new JFrame("The Quest For Knowledge");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Uses a JPanel with a CardLayout for switching between panels
		cardsPanel = new JPanel();
		cardsPanel.setLayout(new CardLayout());
		
		questionPanel = new ImagePanel(ImageLoader.getImage("1"));
		questionPanel.setBackground(Color.white);
		questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
		questionPanel.setName("QuestionPanel");
		title = new JLabel();
		title.setFont(augustus18);
		answerA = new JRadioButton();
		answerB = new JRadioButton();
		answerC = new JRadioButton();
		answerD = new JRadioButton();
		buttons = new JRadioButton[] {answerA, answerB, answerC, answerD};
		for (JRadioButton b : buttons) {
			b.setBackground(Color.white);
			b.setFont(augustus16);
		}
		choices = new ButtonGroup();
		choices.add(answerA);
		choices.add(answerB);
		choices.add(answerC);
		choices.add(answerD);
		questionPanel.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBackground(Color.white);
		questionPanel.add(Box.createVerticalStrut(10));
		questionPanel.add(answerA);
		questionPanel.add(answerB);
		questionPanel.add(answerC);
		questionPanel.add(answerD);
		answerA.setAlignmentX(Component.CENTER_ALIGNMENT);
		answerB.setAlignmentX(Component.CENTER_ALIGNMENT);
		answerC.setAlignmentX(Component.CENTER_ALIGNMENT);
		answerD.setAlignmentX(Component.CENTER_ALIGNMENT);
		questionPanel.add(Box.createVerticalStrut(10));
		
		// Only holds the submit button, previous versions had multiple buttons
		JPanel qButtons = new JPanel();
		qButtons.setLayout(new BoxLayout(qButtons, BoxLayout.X_AXIS));
		submit = new JButton("");
		button = resize(button);
		submit.setBorder(BorderFactory.createEmptyBorder());
		submit.setContentAreaFilled(false);
		if (button != null)
			submit.setIcon(new ImageIcon(button));
		
		// Action listener for the submit button
		class CheckAnswer implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				check();
			}
		}
		submit.addActionListener(new CheckAnswer());
		qButtons.add(submit);
		questionPanel.add(qButtons);
		cardsPanel.add(questionPanel, "questions");
		
		canvas = new ImageCanvas();
		canvas.setName("Canvas");
		cardsPanel.add(canvas, "canvas");
		
		window.add(cardsPanel);
		window.setSize(1024, 768);
		window.setResizable(false);
		window.setVisible(true);
		
		// Begins playing of background music, runs for entire game
		SoundUtil.loopSound("music", 0.0f);
		
		switchPanels();
				
		// Beginning text
		canvas.setRenderText("The world as we know it has been shaped for the past millennia by omnipotent beings. Behind the scenes they are behind the biggest advances in history. This is your turn, shape your world. As you answer questions your world prospers and grows, when you fail your world does too.");
		
		// Begins playing the first stage-specific looping sound
		SoundUtil.loopSound(stages.getSound(), stages.getSoundLevel());
		canvas.update(canvas.getGraphics());
		new DelayThread(7000, true).run();
		newQuestion();
		stages.anotherTried();
		questionPanel.update(questionPanel.getGraphics());
	}
	
	/**
	 * 
	 * @return Returns the sentence for a question and the correct answer (the title of the matching Wikipedia page)
	 */
	private static String[] genQuestion() {
		// Gets the title and first sentence of a random article
		String[] article = WikipediaQuestions.getFirstSentence("Special:Random");
		
		String title = article[0];
		String sentence = article[1];
		
		// Removes common issues and generates a new question
		if (sentence.trim().length() == 0 || title.contains("list") || title.contains("List") || sentence.contains("may refer to"))
			return genQuestion();
		
		String[] titleParts = title.split(" ");
		ArrayList<String> parts = new ArrayList<String>();
		
		// Removes commas so all words are recognized
		for (int i = 0; i < titleParts.length; i++) {
			if (titleParts[i].contains(","))
				titleParts[i] = titleParts[i].replace(",", " ");
			parts.add(titleParts[i].trim());
		}
		// Comparator for comparing lengths of strings
		class StringComparator implements Comparator<String> {
			@Override
			public int compare(String o1, String o2) {
				return o2.length() - o1.length();
			}
			
		}
		// Strings sorted in order of descending (hopefully) length
		Collections.sort(parts, new StringComparator());
		try {
			for (String s : parts) {
				if (validTitleWord(s)) {
					// Replaces words from title in question with underscores
					String replace = "";
					for (int i = 0; i < s.length(); i++)
						replace += "_";
					sentence = sentence.replaceAll("(?i)" + s, replace);
				}
			}
		} catch (Exception e) { return genQuestion(); }
		
		return new String[] {title, sentence};
	}
	
	/**
	 * Checks the user's answer
	 */
	private static void check() {
		JRadioButton selected = null;
		for (JRadioButton b : buttons) {
			if (b.isSelected())
				selected = b;
			b.setBackground(Color.white);
		}

		if (StageHandler.NUM_STAGES > stages.getNumTried()) {
			if (selected.getText().equals(currentAnswers.get(correctAnswer))) {
				// Correct answer
				selected.setBackground(Color.green);
				questionPanel.update(questionPanel.getGraphics());
				new DelayThread(2000, true).run();
				
				// Stage progresses
				canvas.setRGB(0);
				canvas.setRenderText(stages.getNextStageText());
				canvas.setBufferedImage(stages.nextImage());
				canvas.update(canvas.getGraphics());
				
				// Game ends
				if (stages.getNumCompleted() == 12) {
					return;
				}
				
				// Plays new stage sound
				SoundUtil.loopSound(stages.getSound(), stages.getSoundLevel());
				new DelayThread(7000, true).run();
			} else {
				// Highlights correct and incorrect answers
				JRadioButton right = null;
				for (JRadioButton b : buttons)
					if (b.getText().equals(currentAnswers.get(correctAnswer)))
						right = b;
				right.setBackground(Color.green);
				selected.setBackground(Color.red);
				questionPanel.update(questionPanel.getGraphics());
				new DelayThread(2000, false).run();
			}
		} else {
			if (stages.getNumCompleted() >= 12) {
				return;
			} else {
				new GameOver();
				closeFrame();
			}
		}
		questionPanel.setImage(ImageLoader.getImage(stages.image()));
		button = resize(button);
		if (button != null)
			submit.setIcon(new ImageIcon(button));
		newQuestion();
		stages.anotherTried();
	}
	
	/**
	 * Switches the visibility of the two panels
	 */
	public static void switchPanels() {
		CardLayout l = (CardLayout) cardsPanel.getLayout();
		if (questionsVisible) {
			l.show(cardsPanel, "canvas");
			questionsVisible = false;
		} else {
			l.show(cardsPanel, "questions");
			questionsVisible = true;
		}
	}
	
	/**
	 * Generates incorrect answers and changes text of JComponents
	 */
	private static void newQuestion() {
		for (JRadioButton b : buttons) {
			b.setBackground(Color.white);
		}
		
		// Gets the question and correct answer
		String[] parts = genQuestion();
		title.setText(parts[1]);
		title.setBackground(Color.white);
		String[] answers = new String[4];
		answers[0] = parts[0];
		
		// Gets 3 other random answers
		answers[1] = WikipediaQuestions.getOtherAnswer();
		answers[2] = WikipediaQuestions.getOtherAnswer();
		answers[3] = WikipediaQuestions.getOtherAnswer();
		ArrayList<String> l = new ArrayList<String>();
		for (int i = 0; i < 4; i++)
			l.add(answers[i]);
		
		// Randomizes answers
		Collections.shuffle(l);
		answerA.setText(l.get(0));
		answerB.setText(l.get(1));
		answerC.setText(l.get(2));
		answerD.setText(l.get(3));
		
		currentAnswers = l;
		correctAnswer = l.indexOf(answers[0]);
		questionPanel.update(questionPanel.getGraphics());
	}
	
	/**
	 * 
	 * @param bi BufferedImage to be resized
	 * @return Resized BufferedImage
	 */
	private static BufferedImage resize(BufferedImage bi) {
		if (bi != null) {
			Image tmp = bi.getScaledInstance(200, 48, BufferedImage.SCALE_FAST);
			BufferedImage buffered = new BufferedImage(200, 48, BufferedImage.TYPE_INT_RGB);
			buffered.getGraphics().drawImage(tmp, 0, 0, null);
			return buffered;
		}
		return null;
	}
	
	/**
	 * Checks for articles and other common words
	 * @param word Word to check validity of
	 * @return Validity of word
	 */
	private static boolean validTitleWord(String word) {
		String[] no = new String[] {"the", "a", "an", "of", "in", "on", "i", "and", "for"};
		for (String s : no) {
			if (s.equalsIgnoreCase(word))
				return false;
		}
		return true;
	}
	
	private static void closeFrame() {
		window.dispose();
	}
}