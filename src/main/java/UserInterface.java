import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface {
  private JFrame frame;
  private JLabel pageLabel;
  private JLabel introLabel;
    public UserInterface() {
      this.frame = new JFrame();
      frame.setTitle("Phrasebuilder");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(1080, 720);
      frame.setResizable(false);
      ImageIcon image = new ImageIcon("PB.png");
      frame.setIconImage(image.getImage());

      this.pageLabel = new JLabel();
      pageLabel.setText("Welcome to Phrasebuilder for Guitar!");
      pageLabel.setHorizontalAlignment(JLabel.CENTER);
      pageLabel.setVerticalAlignment(JLabel.TOP);
      frame.add(pageLabel);

      JButton start = new JButton("START");
      frame.add(start);
      Box leftSpace = new Box(1); leftSpace.createHorizontalStrut(50);

      start.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          switchPage(1);
          System.out.println("Start clicked");
        }
      });

      this.introLabel = new JLabel();
      introLabel.setText("This program uses procedural generation to create guitar phrases based on user settings. Select start to begin.");
      introLabel.setHorizontalAlignment(JLabel.CENTER);
      introLabel.setVerticalAlignment(JLabel.BOTTOM);
      frame.add(introLabel);

      frame.setVisible(true);
    }

  private void switchPage(int page) {
    switch(page) {
      case 1:
        pageLabel.setText("Scale settings");
        break;
      case 2:
        //phrase settings
        break;
      case 3:
        //tab settings
        break;
      case 4:
        //final output
        break;
      default:
        break;
    }
  }
}
