package gui;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Menu to choose the first player.
 */

public class PlayFirst extends JDialog {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private boolean isComputerOpponent;
  private JButton playerFirst;
  private JButton computerFirst;
  private JButton goBack;
  private JPanel mainPanel;

  public PlayFirst(boolean iscomputeropponent, String firstPlayer,
      String secondPlayer) {

    setTitle("Choose who play first!");
    setSize(250, 200);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setModal(true);
    setLocationRelativeTo(null);
    setResizable(false);

    isComputerOpponent = iscomputeropponent;
    mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(3, 1, 1, 20));
    mainPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
    playerFirst = new JButton(firstPlayer);
    computerFirst = new JButton(secondPlayer);
    goBack = new JButton("Main menu");
    mainPanel.add(playerFirst);
    mainPanel.add(computerFirst);
    mainPanel.add(goBack);

    add(mainPanel);

    playerFirstConfig(playerFirst, this);
    computerFirstConfig(computerFirst, this);
    goBackConfig(goBack, this);
  }

  public void playerFirstConfig(final JButton playerOpponent,
      final PlayFirst playFirst) {
    playerOpponent.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        FirstPlayerImage firstPlayerImage = new FirstPlayerImage(
            isComputerOpponent, true);
        playFirst.dispose();
        firstPlayerImage.setVisible(true);
      }
    });
  }

  public void computerFirstConfig(final JButton computerOpponent,
      final PlayFirst playFirst) {
    computerOpponent.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        FirstPlayerImage firstPlayerImage = new FirstPlayerImage(
            isComputerOpponent, false);
        playFirst.dispose();
        firstPlayerImage.setVisible(true);
      }
    });
  }

  public void goBackConfig(final JButton goBack, final PlayFirst gameType) {
    goBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        MainWindow mm = new MainWindow();
        gameType.dispose();
        mm.setVisible(true);
      }
    });
  }
}
