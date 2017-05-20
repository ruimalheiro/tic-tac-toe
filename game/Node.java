package game;

import java.util.ArrayList;

/**
 * Node that represents the sate of the game.
 */

public class Node {
  private int[][] gameMatrix;
  private Node parent;
  private ArrayList<Node> childrenList;
  private int value;

  public Node() {
    value = -10;
    childrenList = new ArrayList<Node>();
    gameMatrix = new int[3][3];
  }

  public Node(int[][] currentStateMatrix, int I, int J, boolean isPlayer) {
    value = -10;
    childrenList = new ArrayList<Node>();
    gameMatrix = new int[3][3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        gameMatrix[i][j] = currentStateMatrix[i][j];
        if (i == I && j == J && isPlayer) {
          gameMatrix[i][j] = 0;
        }
        if (i == I && j == J && !isPlayer) {
          gameMatrix[i][j] = 1;
        }
      }
    }
  }

  public int getValue() {
    return this.value;
  }

  public void setValue(int i) {
    this.value = i;
  }

  public void setParent(Node node) {
    parent = node;
  }

  public Node getParent() {
    return parent;
  }

  public void addChildren(Node node) {
    childrenList.add(node);
  }

  public ArrayList<Node> getChildrenList() {
    return childrenList;
  }

  public int[][] getGameMatrix() {
    return this.gameMatrix;
  }

  public void initMatrix() {
    gameMatrix = new int[3][3];
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        gameMatrix[i][j] = -9;
      }
    }
  }

  public void expandNode(boolean isPlayer) {
    childrenList.clear();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (gameMatrix[i][j] == -9) {
          Node n = new Node(gameMatrix, i, j, isPlayer);
          n.setParent(this);
          childrenList.add(n);
        }
      }
    }
  }

  public void printExpandedNodes() {
    System.out.println("Next possible moves:");
    System.out.println("*************************");
    System.out.println("");
    for (Node n : childrenList) {
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          System.out.print(n.getGameMatrix()[i][j] + " ");
        }
        System.out.println("");
      }
      System.out.println("");
    }
    System.out.println("*************************");
    System.out.println("Number of possible moves: " + childrenList.size());
  }

  public void printMatrixState() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        System.out.print(gameMatrix[i][j]);
      }
      System.out.println("");
    }
    System.out.println("");
  }

  public int checkWinner(boolean isPlayer, int playLeft) {
    int test;
    if (isPlayer) {
      test = 0;
      // Line check
      for (int i = 0; i < 3; i++) {
        test = 0;
        for (int j = 0; j < 3; j++) {
          if (gameMatrix[i][j] == 0) {
            test++;
          }
          if (test == 3) {
            return -1;
          }
        }
      }
      test = 0;
      // Column check
      for (int i = 0; i < 3; i++) {
        test = 0;
        for (int j = 0; j < 3; j++) {
          if (gameMatrix[j][i] == 0) {
            test++;
          }
          if (test == 3) {
            return -1;
          }
        }
      }
      test = 0;
      // Diagonal down check
      if (gameMatrix[0][0] == 0 && gameMatrix[1][1] == 0
          && gameMatrix[2][2] == 0) {
        return -1;
      }

      // Diagonal up check
      if (gameMatrix[2][0] == 0 && gameMatrix[1][1] == 0
          && gameMatrix[0][2] == 0) {
        return -1;
      }

    } else if (!isPlayer) {

      test = 0;
      // Line check
      for (int i = 0; i < 3; i++) {
        test = 0;
        for (int j = 0; j < 3; j++) {
          if (gameMatrix[i][j] == 1) {
            test++;
          }
          if (test == 3) {
            return 1;
          }
        }
      }
      test = 0;
      // Column check
      for (int i = 0; i < 3; i++) {
        test = 0;
        for (int j = 0; j < 3; j++) {
          if (gameMatrix[j][i] == 1) {
            test++;
          }
          if (test == 3) {
            return 1;
          }
        }
      }
      test = 0;
      // Diagonal down check
      if (gameMatrix[0][0] == 1 && gameMatrix[1][1] == 1
          && gameMatrix[2][2] == 1) {
        return 1;
      }

      // Diagonal up check
      if (gameMatrix[2][0] == 1 && gameMatrix[1][1] == 1
          && gameMatrix[0][2] == 1) {
        return 1;
      }
    }

    if (playLeft == 0) {
      return 0;
    }
    return -2;
  }

  public int checkWinnerBest(int playLeft) {
    int test;
    test = 0;
    // Line check
    for (int i = 0; i < 3; i++) {
      test = 0;
      for (int j = 0; j < 3; j++) {
        if (gameMatrix[i][j] == 0) {
          test++;
        }
        if (test == 3) {
          return -1;
        }
      }
    }
    test = 0;
    // Column check
    for (int i = 0; i < 3; i++) {
      test = 0;
      for (int j = 0; j < 3; j++) {
        if (gameMatrix[j][i] == 0) {
          test++;
        }
        if (test == 3) {
          return -1;
        }
      }
    }
    test = 0;
    // Diagonal down check
    if (gameMatrix[0][0] == 0 && gameMatrix[1][1] == 0
        && gameMatrix[2][2] == 0) {
      return -1;
    }

    // Diagonal up check
    if (gameMatrix[2][0] == 0 && gameMatrix[1][1] == 0
        && gameMatrix[0][2] == 0) {
      return -1;
    }

    test = 0;
    // Line check
    for (int i = 0; i < 3; i++) {
      test = 0;
      for (int j = 0; j < 3; j++) {
        if (gameMatrix[i][j] == 1) {
          test++;
        }
        if (test == 3) {
          return 1;
        }
      }
    }
    test = 0;
    // Column check
    for (int i = 0; i < 3; i++) {
      test = 0;
      for (int j = 0; j < 3; j++) {
        if (gameMatrix[j][i] == 1) {
          test++;
        }
        if (test == 3) {
          return 1;
        }
      }
    }
    test = 0;
    // Diagonal down check
    if (gameMatrix[0][0] == 1 && gameMatrix[1][1] == 1
        && gameMatrix[2][2] == 1) {
      return 1;
    }

    // Diagonal up check
    if (gameMatrix[2][0] == 1 && gameMatrix[1][1] == 1
        && gameMatrix[0][2] == 1) {
      return 1;
    }

    if (playLeft == 0) {
      return 0;
    }
    return -2;
  }
}
