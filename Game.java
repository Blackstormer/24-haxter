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

public class Game {
	private static ImagePanel questionPanel;
	
	private static JFrame window;
	private static JPanel cardsPanel;
	private static JLabel title;
	private static JRadioButton answerA, answerB, answerC, answerD;
	private static JRadioButton[] buttons;
	private static JButton submit;
	private static ButtonGroup choices;
	
	private static ArrayList<String> currentAnswers;
	private static int correctAnswer;
	
	private static ImageCanvas canvas;
	
	public static Font augustus18 = null;
	public static Font augustus12 = null;
	public static Font augustus30 = null;
	
	private static boolean questionsVisible = true;
	
	private static StageHandler stages = new StageHandler();
	
	public static final String[] buttonNames = new String[] {"button_fire", "button_agriculture", "button_writing", "button_metal", "button_money", "button_math", "button_engineering", "button_vaccines", "button_electricity", "button_internet", "button_future"};
	public static BufferedImage button = ImageLoader.getImage(buttonNames[0]);
		
	public static void main(String[] args) {
		try {
			augustus30 = augustus18 = augustus12 = Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/AUGUSTUS.TTF"));
		} catch (Exception e) {}
		
		augustus30 = augustus30.deriveFont(30.0f);
		augustus18 = augustus18.deriveFont(18.0f);
		augustus12 = augustus12.deriveFont(12.0f);
		
		window = new JFrame("HackExeter");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
			b.setFont(augustus12);
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
		JPanel qButtons = new JPanel();
		qButtons.setLayout(new BoxLayout(qButtons, BoxLayout.X_AXIS));
		submit = new JButton("");
		button = resize(button);
		submit.setBorder(BorderFactory.createEmptyBorder());
		submit.setContentAreaFilled(false);
		submit.setIcon(new ImageIcon(button));
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
		
		SoundUtil.loopSound("music", 0.0f);
		
		switchPanels();
		canvas.setRenderText("The world as we know it has been shaped for the past millennia by omnipotent beings. Behind the scenes they are behind the biggest advances in history. This is your turn, shape your world. As you answer questions your world prospers and grows, when you fail your world does too.");
		new DelayThread(10000, true).run();
		newQuestion();
		questionPanel.update(questionPanel.getGraphics());
		
		SoundUtil.loopSound(stages.getSound(), stages.getSoundLevel());
	}
	
	private static String[] genQuestion() {
		String[] article = WikipediaQuestions.getFirstSentence("Special:Random");
		
		String title = article[0];
		String sentence = article[1];
		
		if (sentence.trim().length() == 0 || title.contains("list") || title.contains("List") || sentence.contains("may refer to"))
			return genQuestion();
		
		String[] titleParts = title.split(" ");
		ArrayList<String> parts = new ArrayList<String>();
		for (int i = 0; i < titleParts.length; i++) {
			if (titleParts[i].contains(","))
				titleParts[i] = titleParts[i].replace(",", " ");
			parts.add(titleParts[i].trim());
		}
		class StringComparator implements Comparator<String> {
			@Override
			public int compare(String o1, String o2) {
				return o1.length() - o2.length();
			}
			
		}
		Collections.sort(parts, new StringComparator());
		try {
			for (String s : parts) {
				if (validTitleWord(s)) {
					String replace = "";
					for (int i = 0; i < s.length(); i++)
						replace += "_";
					sentence = sentence.replaceAll("(?i)" + s, replace);
				}
			}
		} catch (Exception e) { return genQuestion(); }
		
		return new String[] {title, sentence};
	}
	
	private static void check() {
		JRadioButton selected = null;
		for (JRadioButton b : buttons) {
			if (b.isSelected())
				selected = b;
			b.setBackground(Color.white);
		}
		
		if (selected.getText().equals(currentAnswers.get(correctAnswer))) {
			selected.setBackground(Color.green);
			questionPanel.update(questionPanel.getGraphics());
			new DelayThread(2000, true).run();
			canvas.setRGB(0);
			canvas.setRenderText(stages.getNextStageText());
			canvas.setBufferedImage(stages.nextImage());
			canvas.update(canvas.getGraphics());
			SoundUtil.loopSound(stages.getSound(), stages.getSoundLevel());
			new DelayThread(10000, true).run();
		} else {
			JRadioButton right = null;
			for (JRadioButton b : buttons)
				if (b.getText().equals(currentAnswers.get(correctAnswer)))
					right = b;
			right.setBackground(Color.green);
			selected.setBackground(Color.red);
			questionPanel.update(questionPanel.getGraphics());
			new DelayThread(2000, false).run();
		}
		questionPanel.setImage(ImageLoader.getImage(stages.image()));
		button = resize(button);
		submit.setIcon(new ImageIcon(button));
		newQuestion();
	}
	
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
	
	private static void newQuestion() {
		for (JRadioButton b : buttons) {
			b.setBackground(Color.white);
		}
		
		String[] parts = genQuestion();
		title.setText(parts[1]);
		title.setBackground(Color.white);
		String[] answers = new String[4];
		answers[0] = parts[0];
		answers[1] = WikipediaQuestions.getOtherAnswer();
		answers[2] = WikipediaQuestions.getOtherAnswer();
		answers[3] = WikipediaQuestions.getOtherAnswer();
		ArrayList<String> l = new ArrayList<String>();
		for (int i = 0; i < 4; i++)
			l.add(answers[i]);
		Collections.shuffle(l);
		answerA.setText(l.get(0));
		answerB.setText(l.get(1));
		answerC.setText(l.get(2));
		answerD.setText(l.get(3));
		
		currentAnswers = l;
		correctAnswer = l.indexOf(answers[0]);
		questionPanel.update(questionPanel.getGraphics());
	}
	
	private static BufferedImage resize(BufferedImage bi) {
		Image tmp = bi.getScaledInstance(200, 48, BufferedImage.SCALE_FAST);
		BufferedImage buffered = new BufferedImage(200, 48, BufferedImage.TYPE_INT_RGB);
		buffered.getGraphics().drawImage(tmp, 0, 0, null);
		return buffered;
	}
	
	private static boolean validTitleWord(String word) {
		String[] no = new String[] {"the", "a", "an", "of", "in", "on", "i", "and", "for"};
		for (String s : no) {
			if (s.equalsIgnoreCase(word))
				return false;
		}
		return true;
	}
}