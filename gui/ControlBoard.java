package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import game.Board;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Main window to support the game and the console.
 */

public class ControlBoard extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel statusPanel;
	private JPanel gamePanel;
	private JPanel southPanel;

	private Board mainBoard;

	private JScrollPane mainScrollPane;

	private JTextArea textArea;

	private TextAreaOutputStream textStream;

	private JButton hideConsole;

	public ControlBoard(Board mainboard) {

		this.mainBoard = mainboard;

		setTitle("Tic Tac Toe");
		setSize(650, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setPreferredSize(new Dimension(250, 419));
		gamePanel = new JPanel();
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		hideConsole = new JButton("Hide console");

		Font font = new Font("Euphemia", Font.PLAIN, 10);
		textArea = new JTextArea();
		textArea.setFont(font);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textStream = new TextAreaOutputStream(textArea, "");
		System.setOut(new PrintStream(textStream));
		mainScrollPane = new JScrollPane();

		mainScrollPane.getViewport().add(textArea);

		statusPanel.add(mainScrollPane);
		gamePanel.add(mainBoard);
		mainBoard.setVisible(true);
		southPanel.add(hideConsole);

		hideConsole.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (statusPanel.isVisible()) {
					statusPanel.setVisible(false);
					hideConsole.setText("Show console");
					setSize(405, 450);
				} else {
					statusPanel.setVisible(true);
					hideConsole.setText("Hide console");
					setSize(650, 450);
				}
			}
		});

		add(statusPanel, BorderLayout.WEST);
		add(mainBoard, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);

		mainBoard.setReferences(this);

		if (mainBoard.getIsComputer()) {
			if (mainBoard.getIsMinMax()) {
				System.out
						.println("Min-max algorithm with simple optimizations");
				System.out.println("");
			} else {
				System.out
						.println("Min-max algorithm with alpha-beta cut and simple optimizations");
				System.out.println("");
			}
			if (mainBoard.getIsPlayerFirst()) {
				System.out.println(mainBoard.getTurn() + " Turn - Player");
			}

		} else if (mainBoard.getIsPlayerFirst()) {
			System.out.println(mainBoard.getTurn() + " Turn - Player 1");
			System.out.println(mainBoard.getPlayLeft() + " moves to end");
		} else {
			System.out.println(mainBoard.getTurn() + " Turn - Player 2");
			System.out.println(mainBoard.getPlayLeft() + " moves to end");
		}
	}
}
