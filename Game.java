import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Game {
	private static JPanel questionPanel;
	
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
	
	private static Font augustus18 = null;
	private static Font augustus12 = null;
	
	private static boolean questionsVisible = true;
	
	private static StageHandler stages = new StageHandler();
		
	public static void main(String[] args) {
		try {
			augustus18 = augustus12 = Font.createFont(Font.TRUETYPE_FONT, new File("bin/fonts/AUGUSTUS.TTF"));
		} catch (Exception e) {}
		
		augustus18 = augustus18.deriveFont(18.0f);
		augustus12 = augustus12.deriveFont(12.0f);
		
		window = new JFrame("HackExeter");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cardsPanel = new JPanel();
		cardsPanel.setLayout(new CardLayout());
		
		questionPanel = new JPanel();
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
		qButtons.setBackground(Color.white);
		submit = new JButton("Submit");
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
		window.setVisible(true);
		
		switchPanels();
		canvas.setRenderText("test");
		switchPanels();
		newQuestion();
		questionPanel.repaint();
	}
	
	private static String[] genQuestion() {
		String[] article = WikipediaQuestions.getFirstSentence("Special:Random");
		
		String title = article[0];
		String sentence = article[1];
		
		String[] titleParts = title.split(" ");
		for (String s : titleParts) {
			String replace = "";
			for (int i = 0; i < s.length(); i++)
				replace += "_";
			sentence = sentence.replaceAll("(?i)" + s, replace);
		}
		
		System.out.println(sentence.trim());
		
		if (sentence.trim().length() == 0)
			return genQuestion();
		
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
			switchPanels();
			canvas.setRGB(0);
			canvas.setRenderText(stages.getNextStageText());
			canvas.setBufferedImage(stages.nextImage());
			switchPanels();
			newQuestion();
		} else {
			JRadioButton right = null;
			for (JRadioButton b : buttons)
				if (b.getText().equals(currentAnswers.get(correctAnswer)))
					right = b;
			right.setBackground(Color.green);
			selected.setBackground(Color.red);
		}
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
	
	/*
	public static void currentPanel() {
		for (Component c : cardsPanel.getComponents()) {
			if (c.isVisible())
				System.out.println(c.getName());
		}
	}
	*/
	
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
	}
}