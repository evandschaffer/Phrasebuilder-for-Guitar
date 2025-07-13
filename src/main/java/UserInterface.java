import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class UserInterface {
    public UserInterface() {
      JFrame frame = new JFrame();
      frame.setTitle("Phrasebuilder");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(1080, 720);
      frame.setResizable(false);
      frame.setVisible(true);
      ImageIcon image = new ImageIcon("PB.png");
      frame.setIconImage(image.getImage());
    }
}
