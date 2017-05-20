package gui;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * This is used to map a stream to a JTextArea Object.
 */

public class TextAreaOutputStream extends OutputStream {

  private final JTextArea textArea;
  private final StringBuilder sb = new StringBuilder();
  private String title;

  public TextAreaOutputStream(final JTextArea textArea, String title) {
    this.textArea = textArea;
    this.title = title;
    sb.append(title + "");
  }

  @Override
  public void flush() {
  }

  @Override
  public void close() {
  }

  @Override
  public void write(int b) throws IOException {

    if (b == '\r')
      return;

    if (b == '\n') {
      final String text = sb.toString();
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          textArea.append(text);
        }
      });
      sb.setLength(0);
      sb.append(title).append("");
    }

    sb.append((char) b);
  }
}
