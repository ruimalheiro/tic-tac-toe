package gui;

import game.Board;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Window to choose the first player image.
 */

public class FirstPlayerImage extends JDialog {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private ImageIcon xImage;
  private ImageIcon oImage;
  private JLabel oLabel;
  private JLabel xLabel;
  private JButton goBack;

  private JPanel mainPanel;
  private JPanel buttonPanel;

  private boolean isComputer;
  private boolean isPlayerFirst;

  public FirstPlayerImage(boolean iscomputer, boolean isplayerfirst) {

    this.isComputer = iscomputer;
    this.isPlayerFirst = isplayerfirst;

    setTitle("Choose the symbol for the first player!");
    setSize(300, 290);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setModal(true);
    setLocationRelativeTo(null);
    setResizable(false);

    mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(2, 2, 1, 20));
    mainPanel.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

    oImage = new ImageIcon(getClass().getResource("/images/O2.png"));
    xImage = new ImageIcon(getClass().getResource("/images/X2.png"));
    oLabel = new JLabel(oImage);
    xLabel = new JLabel(xImage);
    goBack = new JButton("Main menu");

    mainPanel.add(oLabel);
    mainPanel.add(xLabel);

    buttonPanel = new JPanel();
    buttonPanel.add(goBack);

    mainPanel.add(buttonPanel);

    add(mainPanel);

    oImageConfiguration(oLabel, this);
    xImageConfiguration(xLabel, this);
    goBackConfig(goBack, this);
  }

  public void oImageConfiguration(final JLabel oImage,
      final FirstPlayerImage firstPlayerImage) {
    oImage.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent arg0) {
        if (isComputer) {
          ChooseAlgorithm chooseAlgorithm = new ChooseAlgorithm("O",
              isPlayerFirst);
          firstPlayerImage.dispose();
          chooseAlgorithm.setVisible(true);
        } else {
          Board board = new Board("O", false, isPlayerFirst, true);
          firstPlayerImage.dispose();
          ControlBoard controlBoard = new ControlBoard(board);
          controlBoard.setVisible(true);
        }
      }

      @Override
      public void mouseEntered(MouseEvent arg0) {
      }

      @Override
      public void mouseExited(MouseEvent arg0) {
      }

      @Override
      public void mousePressed(MouseEvent arg0) {
      }

      @Override
      public void mouseReleased(MouseEvent arg0) {
      }

    });
  }

  public void xImageConfiguration(final JLabel xImage,
      final FirstPlayerImage firstPlayerImage) {
    xImage.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent arg0) {
        if (isComputer) {
          ChooseAlgorithm chooseAlgorithm = new ChooseAlgorithm("X",
              isPlayerFirst);
          firstPlayerImage.dispose();
          chooseAlgorithm.setVisible(true);
        } else {
          Board board = new Board("X", false, isPlayerFirst, true);
          firstPlayerImage.dispose();
          ControlBoard controlBoard = new ControlBoard(board);
          controlBoard.setVisible(true);
        }
      }

      @Override
      public void mouseEntered(MouseEvent arg0) {
      }

      @Override
      public void mouseExited(MouseEvent arg0) {
      }

      @Override
      public void mousePressed(MouseEvent arg0) {
      }

      @Override
      public void mouseReleased(MouseEvent arg0) {
      }

    });
  }

  public void goBackConfig(final JButton goBack,
      final FirstPlayerImage firstPlayerImage) {
    goBack.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        MainWindow mainWindow = new MainWindow();
        firstPlayerImage.dispose();
        mainWindow.setVisible(true);
      }
    });
  }
}
