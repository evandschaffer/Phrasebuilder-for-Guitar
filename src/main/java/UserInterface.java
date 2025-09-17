import javax.swing.*;
import java.awt.*;

public class UserInterface {
    public UserInterface() {
      JFrame frame = new JFrame();
      frame.setTitle("Phrasebuilder");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(1080, 720);
      frame.setResizable(false);
      ImageIcon image = new ImageIcon("PB.png");
      frame.setIconImage(image.getImage());
      frame.setLayout(new GridLayout(8,24));

      JLabel label = new JLabel();
      label.setText("Welcome to Phrasebuilder for Guitar!");
      label.setHorizontalAlignment(JLabel.CENTER);
      label.setVerticalAlignment(JLabel.TOP);
      frame.add(label);

      JButton start = new JButton("START");
      start.setBounds(0, 0, 0, 0);
      frame.add(start);


      frame.setVisible(true);
    }
  private void switchPage(int page) {
    switch(page) {
      case 1:
        //scale selection
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
