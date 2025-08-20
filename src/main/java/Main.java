public class Main {
  public static void main(String[] args) {
    //tab test
    Scale scale = new Scale(new int[]{2,1,2,2,1,2,2},"D minor","D");
    Phrase phrase = new Phrase(scale, new int[]{12,8}, true);
    Tab[] tabs = new Tab[phrase.getNotes().length];
    tabs[0] = new Tab(phrase.getNotes()[0], new int[]{7, 19, 3, 6}, null);
    for (int i = 1; i < tabs.length; i++) {
      tabs[i] = new Tab(phrase.getNotes()[i], new int[]{7, 19, 3, 6}, tabs[i-1].getTabNote());
    }
    for (Tab tab : tabs) {
      tab.print();
      System.out.println();
    }
    //gui test
    UserInterface gui = new UserInterface();
  }
}