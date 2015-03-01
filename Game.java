import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private static JFrame window;
	private static JLabel title;
	private static JRadioButton answerA, answerB, answerC, answerD;
	private static JRadioButton[] buttons;
	private static JButton newQuestion, checkAnswer;
	private static ButtonGroup choices;
	
	private static ArrayList currentAnswers;
	private static int correctAnswer;
	
	public static void main(String[] args) {
		window = new JFrame("HackExeter");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		title = new JLabel();
		answerA = new JRadioButton();
		answerB = new JRadioButton();
		answerC = new JRadioButton();
		answerD = new JRadioButton();
		buttons = new JRadioButton[] {answerA, answerB, answerC, answerD};
		for (JRadioButton b : buttons)
			b.setBackground(Color.white);
		choices = new ButtonGroup();
		choices.add(answerA);
		choices.add(answerB);
		choices.add(answerC);
		choices.add(answerD);
		panel.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBackground(Color.white);
		panel.add(Box.createVerticalStrut(10));
		panel.add(answerA);
		panel.add(answerB);
		panel.add(answerC);
		panel.add(answerD);
		answerA.setAlignmentX(Component.CENTER_ALIGNMENT);
		answerB.setAlignmentX(Component.CENTER_ALIGNMENT);
		answerC.setAlignmentX(Component.CENTER_ALIGNMENT);
		answerD.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(Box.createVerticalStrut(10));
		JPanel qButtons = new JPanel();
		qButtons.setLayout(new BoxLayout(qButtons, BoxLayout.X_AXIS));
		qButtons.setBackground(Color.white);
		newQuestion = new JButton("New Question");
		class NewQuestion implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
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
		newQuestion.addActionListener(new NewQuestion());
		qButtons.add(newQuestion);
		qButtons.add(Box.createHorizontalStrut(10));
		checkAnswer = new JButton("Check Answer");
		class CheckAnswer implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				check();
			}
		}
		checkAnswer.addActionListener(new CheckAnswer());
		qButtons.add(checkAnswer);
		panel.add(qButtons);
		window.add(panel);
		
		window.setSize(1280, 200);
		window.setVisible(true);
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
		} else {
			JRadioButton right = null;
			for (JRadioButton b : buttons)
				if (b.getText().equals(currentAnswers.get(correctAnswer)))
					right = b;
			right.setBackground(Color.green);
			selected.setBackground(Color.red);
		}
	}
}