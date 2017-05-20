package gui;

import game.Board;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Menu to choose the algorithm.
 */

public class ChooseAlgorithm extends JDialog {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private JButton minMaxButton;
  private JButton alfaBetaButton;
  private JButton goBack;
  private JPanel mainPanel;
  private String playerChoice;
  private boolean isPlayerFirst;

  public ChooseAlgorithm(String playerchoice, boolean isplayerfirst) {

    this.playerChoice = new String(playerchoice);
    this.isPlayerFirst = isplayerfirst;

    setTitle("Choose the algorithm for computer!");
    setSize(250, 200);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setModal(true);
    setLocationRelativeTo(null);
    setResizable(false);

    mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(3, 1, 1, 20));
    mainPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

    minMaxButton = new JButton("Min Max");
    alfaBetaButton = new JButton("Alfa Beta");
    goBack = new JButton("Main Menu");
    mainPanel.add(minMaxButton);
    mainPanel.add(alfaBetaButton);
    mainPanel.add(goBack);

    add(mainPanel);

    minMaxButtonConfig(minMaxButton, this);
    alfaBetaButtonConfig(alfaBetaButton, this);
    goBackConfig(goBack, this);
  }

  public void minMaxButtonConfig(final JButton minMaxButton,
      final ChooseAlgorithm chooseAlgorithm) {
    minMaxButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Board board = new Board(playerChoice, true, isPlayerFirst, true);
        chooseAlgorithm.dispose();
        ControlBoard controlBoard = new ControlBoard(board);
        controlBoard.setVisible(true);
      }
    });
  }

  public void alfaBetaButtonConfig(final JButton alfaBeta,
      final ChooseAlgorithm chooseAlgorithm) {
    alfaBeta.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Board board = new Board(playerChoice, true, isPlayerFirst,
            false);
        chooseAlgorithm.dispose();
        ControlBoard controlBoard = new ControlBoard(board);
        controlBoard.setVisible(true);
      }
    });
  }

  public void goBackConfig(final JButton goBack,
      final ChooseAlgorithm chooseAlgorithm) {
    goBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        MainWindow mainWindow = new MainWindow();
        chooseAlgorithm.dispose();
        mainWindow.setVisible(true);
      }
    });
  }
}
