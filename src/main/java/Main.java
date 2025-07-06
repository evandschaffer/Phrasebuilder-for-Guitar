public class Main {
  public static void main(String[] args) {
    //scale test
    System.out.println("Initializing Phrasebuilder 0.1");
    int[] intervals = {2,2,1,2,2,2,1};
    Scale cMaj = new Scale(intervals, "C Major", "c");
    for (int i = 0; i < cMaj.getScaleNotes().length; i++) {
      System.out.println(cMaj.getScaleNotes()[i]);
    } //scale test

    //logic test
    Note note = new Note("a", true, 4, false, null);

    UserInterface gui = new UserInterface();
  }
}