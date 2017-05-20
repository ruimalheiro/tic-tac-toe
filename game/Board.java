package game;

import gui.ControlBoard;
import gui.MainWindow;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import minmax.MinMax;

/**
 * The board to play.
 */

public class Board extends JPanel {
  private static final long serialVersionUID = 1L;

  private ImageIcon boardImage;
  private ImageIcon playerImage;
  private ImageIcon computerImage;

  private JLabel mainPanel;
  private JLabel topLeftLabel;
  private JLabel topLabel;
  private JLabel topRightLabel;
  private JLabel leftLabel;
  private JLabel middleLabel;
  private JLabel rightLabel;
  private JLabel bottomLeftLabel;
  private JLabel bottomLabel;
  private JLabel bottomRightLabel;

  private volatile boolean isPlayer;
  private volatile boolean terminate;

  private Node currentState;

  private int playLeft;
  private int numberOfExpandedNodes;
  private int numberOfVisitedNodes;
  private int turn;

  ArrayList<Node> tempList;

  private boolean isMinMax;

  private MinMax minMax;
  private Computer computer;
  private ControlBoard controlBoard;

  private boolean isComputer;
  private boolean isPlayerFirst;

  public Board(String player, boolean iscomputer, boolean isplayerfirst,
      boolean isminMax) {

    setBorder(new EmptyBorder(0, 0, 5, 5));
    setBorder(LineBorder.createBlackLineBorder());

    boardImage = new ImageIcon(getClass().getResource(
        "/images/tabuleiro.png"));
    mainPanel = new JLabel();
    mainPanel.setIcon(boardImage);
    mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    mainPanel.setLayout(new GridLayout(3, 3, 10, 10));

    if (player.equals("x") || player.equals("X")) {
      playerImage = new ImageIcon(getClass()
          .getResource("/images/X2.png"));
      computerImage = new ImageIcon(getClass().getResource(
          "/images/O2.png"));
    }
    if (player.equals("o") || player.equals("O")) {
      playerImage = new ImageIcon(getClass()
          .getResource("/images/O2.png"));
      computerImage = new ImageIcon(getClass().getResource(
          "/images/X2.png"));
    }

    topLeftLabel = new JLabel("");
    topLeftLabel.setHorizontalAlignment(SwingConstants.CENTER);
    topLeftLabel.setVerticalAlignment(SwingConstants.CENTER);

    topLabel = new JLabel("");
    topLabel.setHorizontalAlignment(SwingConstants.CENTER);
    topLabel.setVerticalAlignment(SwingConstants.CENTER);

    topRightLabel = new JLabel("");
    topRightLabel.setHorizontalAlignment(SwingConstants.CENTER);
    topRightLabel.setVerticalAlignment(SwingConstants.CENTER);

    leftLabel = new JLabel("");
    leftLabel.setHorizontalAlignment(SwingConstants.CENTER);
    leftLabel.setVerticalAlignment(SwingConstants.CENTER);

    middleLabel = new JLabel("");
    middleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    middleLabel.setVerticalAlignment(SwingConstants.CENTER);

    rightLabel = new JLabel("");
    rightLabel.setHorizontalAlignment(SwingConstants.CENTER);
    rightLabel.setVerticalAlignment(SwingConstants.CENTER);

    bottomLeftLabel = new JLabel("");
    bottomLeftLabel.setHorizontalAlignment(SwingConstants.CENTER);
    bottomLeftLabel.setVerticalAlignment(SwingConstants.CENTER);

    bottomLabel = new JLabel("");
    bottomLabel.setHorizontalAlignment(SwingConstants.CENTER);
    bottomLabel.setVerticalAlignment(SwingConstants.CENTER);

    bottomRightLabel = new JLabel("");
    bottomRightLabel.setHorizontalAlignment(SwingConstants.CENTER);
    bottomRightLabel.setVerticalAlignment(SwingConstants.CENTER);

    mainPanel.add(topLeftLabel);
    mainPanel.add(topLabel);
    mainPanel.add(topRightLabel);
    mainPanel.add(leftLabel);
    mainPanel.add(middleLabel);
    mainPanel.add(rightLabel);
    mainPanel.add(bottomLeftLabel);
    mainPanel.add(bottomLabel);
    mainPanel.add(bottomRightLabel);

    topLeftLabelConfiguration(topLeftLabel);
    topLabelConfiguration(topLabel);
    topRightLabelConfiguration(topRightLabel);
    leftLabelConfiguration(leftLabel);
    middleLabelConfiguration(middleLabel);
    rightLabelConfiguration(rightLabel);
    bottomLeftLabelConfiguration(bottomLeftLabel);
    bottomLabelConfiguration(bottomLabel);
    bottomRightLabelConfiguration(bottomRightLabel);

    add(mainPanel);

    isComputer = iscomputer;
    isPlayerFirst = isplayerfirst;
    isPlayer = isplayerfirst;

    numberOfExpandedNodes = 0;
    numberOfVisitedNodes = 0;
    turn = 1;
    playLeft = 9;

    currentState = new Node();
    currentState.setParent(null);
    currentState.initMatrix();
    currentState.expandNode(isPlayer);

    numberOfExpandedNodes += currentState.getChildrenList().size();

    terminate = false;
    isMinMax = isminMax;

    tempList = new ArrayList<Node>();

    minMax = new MinMax(isMinMax, this);
    computer = new Computer(this, minMax);
    if (isComputer) {
      computer.start();
    } else if (isPlayerFirst) {
      System.out.println(getTurn() + " Turn - Player 1");
      System.out.println(getPlayLeft() + " moves to end");
    } else {
      System.out.println(getTurn() + " Turn - Player 2");
      System.out.println(getPlayLeft() + " moves to end");
    }
  }

  public void setReferences(ControlBoard cb) {
    this.controlBoard = cb;
  }

  public void printGameState() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        System.out.print(currentState.getGameMatrix()[i][j]);
      }
      System.out.println("");
    }
    System.out.println("");
  }

  public void goFirstPlayer(JLabel label, int i, int j) {
    playLeft--;
    setTurn(getTurn() + 1);
    label.setIcon(playerImage);
    label.repaint();
    currentState.setParent(null);
    currentState.getGameMatrix()[i][j] = 0;
    currentState.printMatrixState();
    isPlayer = false;
    if (!isComputer) {
      generalTestWinner();
      System.out.println(getTurn() + " Turn - Player 2");
      System.out.println(getPlayLeft() + " moves to end");
    }
    System.out.println("");
  }

  public void goSecondPlayer(JLabel label, int i, int j) {
    playLeft--;
    setTurn(getTurn() + 1);
    label.setIcon(computerImage);
    label.repaint();
    currentState.setParent(null);
    currentState.getGameMatrix()[i][j] = 1;
    isPlayer = true;
    currentState.printMatrixState();
    generalTestWinner();
    System.out.println(getTurn() + " Turn - Player 1");
    System.out.println(getPlayLeft() + " moves to end");

  }

  public void generalTestWinner() {
    if (isComputer) {
      if (currentState.checkWinnerBest(playLeft) == -1) {
        displayEndingMessage("Winner", "Player wins!");
      } else if (currentState.checkWinnerBest(playLeft) == -0) {
        displayEndingMessage("Draw!", "You draw!");
      } else if (currentState.checkWinnerBest(playLeft) == 1) {
        displayEndingMessage("Winner!", "Computer wins!");
      }
    } else {
      if (currentState.checkWinnerBest(playLeft) == -1) {
        displayEndingMessage("Winner!", "Player 1 wins!");
      } else if (currentState.checkWinnerBest(playLeft) == -0) {
        displayEndingMessage("Draw!", "You draw!");
      } else if (currentState.checkWinnerBest(playLeft) == 1) {
        displayEndingMessage("Winner!", "Player 2 wins!");
      }
    }
  }

  public void goMainMenu() {
    terminate = true;
    this.controlBoard.dispose();
    MainWindow mainWindow = new MainWindow();
    mainWindow.setVisible(true);
  }

  public void displayEndingMessage(String title, String message) {
    System.out.println(message);
    JOptionPane.showMessageDialog(null, message, title,
        JOptionPane.INFORMATION_MESSAGE);
    goMainMenu();
  }

  public void setPlayLeft(int i) {
    this.playLeft = i;
  }

  public int getPlayLeft() {
    return this.playLeft;
  }

  public synchronized void setIsPlayer(boolean bool) {
    isPlayer = bool;
  }

  public synchronized boolean getIsPlayer() {
    return isPlayer;
  }

  public synchronized void setTerminate(boolean bool) {
    terminate = bool;
  }

  public synchronized boolean getTerminate() {
    return terminate;
  }

  public void setCurrentState(Node state) {
    this.currentState = state;
  }

  public Node getCurrentState() {
    return this.currentState;
  }

  public void setNumberOfExpandedNodes(int v) {
    this.numberOfExpandedNodes = v;
  }

  public int getNumberOfExpandedNodes() {
    return this.numberOfExpandedNodes;
  }

  public ArrayList<Node> getTempList() {
    return this.tempList;
  }

  public void setNumberOfVisitedNodes(int v) {
    this.numberOfVisitedNodes = v;
  }

  public int getNumberOfVisitedNodes() {
    return this.numberOfVisitedNodes;
  }

  public boolean getIsComputer() {
    return this.isComputer;
  }

  public boolean getIsMinMax() {
    return this.isMinMax;
  }

  public boolean getIsPlayerFirst() {
    return this.isPlayerFirst;
  }

  public void setTurn(int v) {
    this.turn = v;
  }

  public int getTurn() {
    return this.turn;
  }

  public void refreshPaint() {
    int[][] tempMatrix = currentState.getGameMatrix();
    // first row
    if (tempMatrix[0][0] == 0) {
      topLeftLabel.setIcon(playerImage);
      topLeftLabel.repaint();
    }

    if (tempMatrix[0][0] == 1) {
      topLeftLabel.setIcon(computerImage);
      topLeftLabel.repaint();
    }

    if (tempMatrix[0][1] == 0) {
      topLabel.setIcon(playerImage);
      topLabel.repaint();
    }

    if (tempMatrix[0][1] == 1) {
      topLabel.setIcon(computerImage);
      topLabel.repaint();
    }

    if (tempMatrix[0][2] == 0) {
      topRightLabel.setIcon(playerImage);
      topRightLabel.repaint();
    }

    if (tempMatrix[0][2] == 1) {
      topRightLabel.setIcon(computerImage);
      topRightLabel.repaint();
    }
    // second row
    if (tempMatrix[1][0] == 0) {
      leftLabel.setIcon(playerImage);
      leftLabel.repaint();
    }

    if (tempMatrix[1][0] == 1) {
      leftLabel.setIcon(computerImage);
      leftLabel.repaint();
    }

    if (tempMatrix[1][1] == 0) {
      middleLabel.setIcon(playerImage);
      middleLabel.repaint();
    }

    if (tempMatrix[1][1] == 1) {
      middleLabel.setIcon(computerImage);
      middleLabel.repaint();
    }

    if (tempMatrix[1][2] == 0) {
      rightLabel.setIcon(playerImage);
      rightLabel.repaint();
    }

    if (tempMatrix[1][2] == 1) {
      rightLabel.setIcon(computerImage);
      rightLabel.repaint();
    }
    // third row
    if (tempMatrix[2][0] == 0) {
      bottomLeftLabel.setIcon(playerImage);
      bottomLeftLabel.repaint();
    }

    if (tempMatrix[2][0] == 1) {
      bottomLeftLabel.setIcon(computerImage);
      bottomLeftLabel.repaint();
    }
    if (tempMatrix[2][1] == 0) {
      bottomLabel.setIcon(playerImage);
      bottomLabel.repaint();
    }

    if (tempMatrix[2][1] == 1) {
      bottomLabel.setIcon(computerImage);
      bottomLabel.repaint();
    }
    if (tempMatrix[2][2] == 0) {
      bottomRightLabel.setIcon(playerImage);
      bottomRightLabel.repaint();
    }

    if (tempMatrix[2][2] == 1) {
      bottomRightLabel.setIcon(computerImage);
      bottomRightLabel.repaint();
    }
  }

  public void topLeftLabelConfiguration(final JLabel topleft) {
    topleft.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent arg0) {
        if (topleft.getIcon() == null) {
          if (isPlayer) {
            goFirstPlayer(topleft, 0, 0);
          } else {
            if (!isComputer) {
              goSecondPlayer(topleft, 0, 0);
            }
          }
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

  public void topLabelConfiguration(final JLabel top) {
    top.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent arg0) {
        if (top.getIcon() == null) {
          if (isPlayer) {
            goFirstPlayer(top, 0, 1);
          } else {
            if (!isComputer) {
              goSecondPlayer(top, 0, 1);
            }
          }
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

  public void topRightLabelConfiguration(final JLabel topRight) {
    topRight.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent arg0) {
        if (topRight.getIcon() == null) {
          if (isPlayer) {
            goFirstPlayer(topRight, 0, 2);
          } else {
            if (!isComputer) {
              goSecondPlayer(topRight, 0, 2);
            }
          }
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

  public void leftLabelConfiguration(final JLabel left) {
    left.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent arg0) {
        if (left.getIcon() == null) {
          if (isPlayer) {
            goFirstPlayer(left, 1, 0);
          } else {
            if (!isComputer) {
              goSecondPlayer(left, 1, 0);
            }
          }
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

  public void middleLabelConfiguration(final JLabel middle) {
    middle.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent arg0) {
        if (middle.getIcon() == null) {
          if (isPlayer) {
            goFirstPlayer(middle, 1, 1);
          } else {
            if (!isComputer) {
              goSecondPlayer(middle, 1, 1);
            }
          }
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

  public void rightLabelConfiguration(final JLabel right) {
    right.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent arg0) {
        if (right.getIcon() == null) {
          if (isPlayer) {
            goFirstPlayer(right, 1, 2);
          } else {
            if (!isComputer) {
              goSecondPlayer(right, 1, 2);
            }
          }
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

  public void bottomLeftLabelConfiguration(final JLabel bottomLeft) {
    bottomLeft.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent arg0) {
        if (bottomLeft.getIcon() == null) {
          if (isPlayer) {
            goFirstPlayer(bottomLeft, 2, 0);
          } else {
            if (!isComputer) {
              goSecondPlayer(bottomLeft, 2, 0);
            }
          }
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

  public void bottomLabelConfiguration(final JLabel bottom) {
    bottom.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent arg0) {
        if (bottom.getIcon() == null) {
          if (isPlayer) {
            goFirstPlayer(bottom, 2, 1);
          } else {
            if (!isComputer) {
              goSecondPlayer(bottom, 2, 1);
            }
          }
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

  public void bottomRightLabelConfiguration(final JLabel bottomRight) {
    bottomRight.addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent arg0) {
        if (bottomRight.getIcon() == null) {
          if (isPlayer) {
            goFirstPlayer(bottomRight, 2, 2);
          } else {
            if (!isComputer) {
              goSecondPlayer(bottomRight, 2, 2);
            }
          }
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
}
