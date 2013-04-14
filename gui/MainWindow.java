package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Main menu window.
 */

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImageIcon titleGaloImage;

	private JLabel titleGaloLabel;
	private JButton startButton;
	private JButton exitButton;

	private JPanel mainPanel;

	private ImageIcon playerImage;
	private ImageIcon computerImage;

	public MainWindow() {
		setTitle("Tic Tac Toe");
		setSize(400, 410);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		titleGaloImage = new ImageIcon(getClass().getResource(
				"/images/titlegalo.png"));

		titleGaloLabel = new JLabel(titleGaloImage);
		titleGaloLabel.setPreferredSize(new Dimension(500, 300));

		startButton = new JButton("New game");
		exitButton = new JButton("Exit game");

		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(new Insets(0, 10, 10, 10)));
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(titleGaloLabel, BorderLayout.NORTH);
		mainPanel.add(startButton, BorderLayout.WEST);
		mainPanel.add(exitButton, BorderLayout.EAST);

		playerImage = new ImageIcon(getClass().getResource("/images/O2.png"));
		computerImage = new ImageIcon(getClass().getResource("/images/X2.png"));

		configureStartButton(startButton, this);
		configureExitButton(exitButton);
		startButton.setIcon(playerImage);
		exitButton.setIcon(computerImage);

		add(mainPanel);
	}

	private void configureStartButton(final JButton start,
			final MainWindow mainWindow) {
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				GameType gameType = new GameType();
				mainWindow.dispose();
				gameType.setVisible(true);

			}
		});
	}

	private void configureExitButton(final JButton exit) {
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				System.exit(0);
			}
		});
	}
}
