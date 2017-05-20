package minmax;

import java.util.ArrayList;

import game.Board;
import game.Node;

/**
 * MinMax class.
 */

public class MinMax {

  private boolean isMinMax;
  private Board board;

  public MinMax(boolean isMinMax, Board board) {
    this.isMinMax = isMinMax;
    this.board = board;
  }

  public void printResults(long start) {
    System.out.println("Number of expanded nodes: "
        + board.getNumberOfExpandedNodes());
    System.out.println("Number of visited nodes: "
        + (board.getNumberOfVisitedNodes() - 1));

    final long totalTime = System.currentTimeMillis() - start;
    System.out.println("Computing time");
    System.out.println("In milliseconds: " + totalTime + " ms.");
    System.out.println("Rounded to seconds: " + (totalTime / 1000) + "s.");
    System.out.println("");
  }

  /******************************************************************************************
   * Decision function. *
   ******************************************************************************************/
  public Node MINMAX_DECISION(Node currentstate) {

    Node result;
    board.setNumberOfExpandedNodes(0);
    board.setNumberOfVisitedNodes(0);
    final long start = System.currentTimeMillis();

    if (isMinMax) {
      int v = MAX_VALUE(currentstate);
      printResults(start);
      result = getBest(currentstate);
    } else {
      int alpha = Integer.MIN_VALUE;
      int beta = Integer.MAX_VALUE;
      int v = MAX_VALUE_ALFA_BETA(currentstate, alpha, beta);
      printResults(start);
      result = getBest(currentstate);
    }

    return result;
  }

  /******************************************************************************************
   * Simple Min Max algorithm. *
   ******************************************************************************************/
  public int MAX_VALUE(Node currentstate) {

    board.setNumberOfVisitedNodes(board.getNumberOfVisitedNodes() + 1);

    if (currentstate.checkWinnerBest(board.getPlayLeft()) != -2) {
      return currentstate.checkWinnerBest(board.getPlayLeft());
    }

    int v = Integer.MIN_VALUE;
    currentstate.expandNode(false);

    board.setNumberOfExpandedNodes(board.getNumberOfExpandedNodes()
        + currentstate.getChildrenList().size());

    for (Node s : currentstate.getChildrenList()) {
      v = Math.max(v, MIN_VALUE(s));
      if (s.getParent().getParent() == null) {
        s.setValue(v);
        board.getTempList().add(s);
      }
    }
    return v;
  }

  public int MIN_VALUE(Node currentstate) {

    board.setNumberOfVisitedNodes(board.getNumberOfVisitedNodes() + 1);

    if (currentstate.checkWinnerBest(board.getPlayLeft()) != -2) {
      return currentstate.checkWinnerBest(board.getPlayLeft());
    }
    int v = Integer.MAX_VALUE;
    currentstate.expandNode(true);

    board.setNumberOfExpandedNodes(board.getNumberOfExpandedNodes()
        + currentstate.getChildrenList().size());

    for (Node s : currentstate.getChildrenList()) {
      v = Math.min(v, MAX_VALUE(s));
      if (s.getParent().getParent() == null) {
        s.setValue(v);
        board.getTempList().add(s);
      }
    }
    return v;
  }

  /******************************************************************************************
   * Min Max algorithm with Alfa Beta pruning. *
   ******************************************************************************************/
  public int MAX_VALUE_ALFA_BETA(Node currentstate, int alpha, int beta) {

    board.setNumberOfVisitedNodes(board.getNumberOfVisitedNodes() + 1);

    if (currentstate.checkWinnerBest(board.getPlayLeft()) != -2) {
      return currentstate.checkWinnerBest(board.getPlayLeft());
    }

    int v = alpha;
    currentstate.expandNode(false);

    board.setNumberOfExpandedNodes(board.getNumberOfExpandedNodes()
        + currentstate.getChildrenList().size());

    for (Node s : currentstate.getChildrenList()) {
      v = Math.max(alpha, MIN_VALUE_ALFA_BETA(s, alpha, beta));
      if (v >= beta) {
        v = beta;
      }
      if (s.getParent().getParent() == null) {
        s.setValue(v);
        board.getTempList().add(s);
      }
      if (v >= beta) {
        return beta;
      }
    }
    return v;
  }

  public int MIN_VALUE_ALFA_BETA(Node currentstate, int alpha, int beta) {

    board.setNumberOfVisitedNodes(board.getNumberOfVisitedNodes() + 1);

    if (currentstate.checkWinnerBest(board.getPlayLeft()) != -2) {
      return currentstate.checkWinnerBest(board.getPlayLeft());
    }
    int v = beta;
    currentstate.expandNode(true);

    board.setNumberOfExpandedNodes(board.getNumberOfExpandedNodes()
        + currentstate.getChildrenList().size());

    for (Node s : currentstate.getChildrenList()) {
      v = Math.min(beta, MAX_VALUE_ALFA_BETA(s, alpha, beta));
      if (v <= alpha) {
        v = alpha;
      }
      if (s.getParent().getParent() == null) {
        s.setValue(v);
        board.getTempList().add(s);
      }
      if (v <= alpha) {
        return alpha;
      }
    }
    return v;
  }


  /******************************************************************************************
   * Get the best option. *
   ******************************************************************************************/
  public Node getBest(Node currentstate) {
    Node computertest = new Node();
    if (board.getTempList().size() == 0)
      return null;

    if (!isMinMax && board.getPlayLeft() == 9) {
      computertest.initMatrix();
      computertest.getGameMatrix()[1][1] = 1;
      board.getTempList().clear();
      return computertest;
    }

    computertest = board.getTempList().get(0);
    for (Node n : board.getTempList()) {
      if (n.checkWinnerBest(board.getPlayLeft()) == 1) {
        return n;
      }

      if (n.getValue() > computertest.getValue()) {
        computertest = n;
      }

      if (board.getPlayLeft() == 8) {
        if (n.getGameMatrix()[0][0] == 0
            && n.getGameMatrix()[1][1] == 1
            || n.getGameMatrix()[0][2] == 0
            && n.getGameMatrix()[1][1] == 1
            || n.getGameMatrix()[2][0] == 0
            && n.getGameMatrix()[1][1] == 1
            || n.getGameMatrix()[2][2] == 0
            && n.getGameMatrix()[1][1] == 1
            || n.getGameMatrix()[0][1] == 0
            && n.getGameMatrix()[1][1] == 1
            || n.getGameMatrix()[1][0] == 0
            && n.getGameMatrix()[1][1] == 1
            || n.getGameMatrix()[1][2] == 0
            && n.getGameMatrix()[1][1] == 1
            || n.getGameMatrix()[2][1] == 0
            && n.getGameMatrix()[1][1] == 1) {
          computertest = n;
          board.getTempList().clear();
          return computertest;
        }
      }

      if (board.getPlayLeft() == 9) {
        if (n.getGameMatrix()[1][1] == 1) {
          computertest = n;
          board.getTempList().clear();
          return computertest;
        }
      }

      if (board.getPlayLeft() == 7) {
        if (n.getGameMatrix()[0][0] == 0
            && n.getGameMatrix()[2][2] == 1
            || n.getGameMatrix()[0][2] == 0
            && n.getGameMatrix()[2][0] == 1
            || n.getGameMatrix()[2][0] == 0
            && n.getGameMatrix()[0][2] == 1
            || n.getGameMatrix()[2][2] == 0
            && n.getGameMatrix()[0][0] == 1) {
          computertest = n;
          board.getTempList().clear();
          return computertest;
        }
      }

      if (board.getPlayLeft() == 6 && n.getGameMatrix()[1][1] == 0) {
        if (n.getGameMatrix()[0][0] == 1
            && n.getGameMatrix()[2][2] == 0
            && (n.getGameMatrix()[0][2] == 1 || n.getGameMatrix()[2][0] == 1)
            || n.getGameMatrix()[2][0] == 1
            && n.getGameMatrix()[0][2] == 0
            && (n.getGameMatrix()[0][0] == 1 || n.getGameMatrix()[2][2] == 1)
            || n.getGameMatrix()[0][2] == 1
            && n.getGameMatrix()[2][0] == 0
            && (n.getGameMatrix()[0][0] == 1 || n.getGameMatrix()[2][2] == 1)
            || n.getGameMatrix()[2][2] == 1
            && n.getGameMatrix()[0][0] == 0
            && (n.getGameMatrix()[2][0] == 1 || n.getGameMatrix()[0][2] == 1)) {
          computertest = n;
          board.getTempList().clear();
          return computertest;
        }
      }

      if (board.getPlayLeft() == 6 && n.getGameMatrix()[1][1] == 1) {
        if (n.getGameMatrix()[0][0] == 0
            && (n.getGameMatrix()[1][2] == 0 || n.getGameMatrix()[2][1] == 0)
            && n.getGameMatrix()[2][2] == 1
            || n.getGameMatrix()[0][2] == 0
            && (n.getGameMatrix()[2][1] == 0 || n.getGameMatrix()[1][0] == 0)
            && n.getGameMatrix()[2][0] == 1
            || n.getGameMatrix()[2][0] == 0
            && (n.getGameMatrix()[0][1] == 0 || n.getGameMatrix()[1][2] == 0)
            && n.getGameMatrix()[0][2] == 1
            || n.getGameMatrix()[2][2] == 0
            && (n.getGameMatrix()[0][1] == 0 || n.getGameMatrix()[1][0] == 0)
            && n.getGameMatrix()[0][0] == 1) {
          computertest = n;
          board.getTempList().clear();
          return computertest;
        }
      }

      if (board.getPlayLeft() == 6 && n.getGameMatrix()[1][1] == 1) {
        if (n.getGameMatrix()[0][2] == 0
            && n.getGameMatrix()[2][0] == 0
            && (n.getGameMatrix()[0][1] == 1
                || n.getGameMatrix()[1][0] == 1
                || n.getGameMatrix()[1][2] == 1 || n
                .getGameMatrix()[2][1] == 1)) {
          computertest = n;
          board.getTempList().clear();
          return computertest;
        }
      }

      if (board.getPlayLeft() == 6 && n.getGameMatrix()[1][1] == 1) {
        if (n.getGameMatrix()[2][1] == 0
            && n.getGameMatrix()[1][2] == 0
            && (n.getGameMatrix()[2][0] == 1 || n.getGameMatrix()[1][2] == 1)) {
          computertest = n;
          board.getTempList().clear();
          return computertest;
        }
      }

      if (board.getPlayLeft() == 5) {
        if (n.getGameMatrix()[0][1] == 0
            && n.getGameMatrix()[1][1] == 1) {
          if (n.getGameMatrix()[0][0] == 1
              && n.getGameMatrix()[1][0] == 1) {
            computertest = n;
            board.getTempList().clear();
            return computertest;
          }
        }
      }

      if (board.getPlayLeft() == 5 && n.getGameMatrix()[1][1] == 1) {
        if (n.getGameMatrix()[0][0] == 1
            && n.getGameMatrix()[0][1] == 1
            && n.getGameMatrix()[1][0] == 0
            || n.getGameMatrix()[0][0] == 1
            && n.getGameMatrix()[0][1] == 0
            && n.getGameMatrix()[1][0] == 1
            || n.getGameMatrix()[0][2] == 1
            && n.getGameMatrix()[0][1] == 1
            && n.getGameMatrix()[1][2] == 0
            || n.getGameMatrix()[0][2] == 1
            && n.getGameMatrix()[0][1] == 0
            && n.getGameMatrix()[1][2] == 1
            || n.getGameMatrix()[2][0] == 1
            && n.getGameMatrix()[1][0] == 1
            && n.getGameMatrix()[2][1] == 0
            || n.getGameMatrix()[2][0] == 1
            && n.getGameMatrix()[1][0] == 0
            && n.getGameMatrix()[2][1] == 1
            || n.getGameMatrix()[2][2] == 1
            && n.getGameMatrix()[2][1] == 1
            && n.getGameMatrix()[1][2] == 0
            || n.getGameMatrix()[2][2] == 1
            && n.getGameMatrix()[2][1] == 0
            && n.getGameMatrix()[1][2] == 1) {
          computertest = n;
          board.getTempList().clear();
          return computertest;
        }
      }
    }

    computertest = board.getTempList().get(0);

    int[][] playertest = getPlayerBest(computertest);
    currentstate.expandNode(board.getIsPlayer());
    for (Node n : currentstate.getChildrenList()) {

      if (n.getValue() > computertest.getValue()) {
        computertest = n;
      }

      if (n.checkWinnerBest(board.getPlayLeft()) == 1) {
        computertest = n;
        break;
      }

      if (isTheOne(n.getGameMatrix(), playertest)) {
        computertest = n;
        break;
      }

    }
    board.getTempList().clear();
    return computertest;
  }

  /******************************************************************************************
   * Choose the best option to block the opponent. *
   ******************************************************************************************/
  public boolean isTheOne(int[][] computer, int[][] player) {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (computer[i][j] == 1 && player[i][j] == 0) {
          return true;
        }
      }
    }
    return false;
  }

  /******************************************************************************************
   * Get best node. *
   ******************************************************************************************/
  public int[][] getPlayerBest(Node current) {
    Node test = new Node();

    current.expandNode(!board.getIsPlayer());
    ArrayList<Node> toCheckList = current.getChildrenList();
    for (Node n : toCheckList) {
      if (n.checkWinnerBest(board.getPlayLeft()) == -1) {
        test = n;
      }
    }
    return test.getGameMatrix();
  }
}
