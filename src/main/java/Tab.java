import java.util.Arrays;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Tab {
  private class TabNote {
    private int str;
    private int fret;

    public TabNote(int str, int fret) {
      this.str = str;
      this.fret = fret;
    }

    public int getStr() {
      return str;
    }

    public int getFret() {
      return fret;
    }

    public void setStr(int str) {
      this.str = str;
    }
    
    public void setFret(int fret) {
      this.fret = fret;
    }
  }

  private Note note;
  private int[] range; //[lower fret, upper fret, lower string, upper string]
  private TabNote[] tabNote; //current note fingering
  private TabNote[] beforeTabNote; //previous note fingering
  private static String[][] fb = {
    {"e", "f", "f#", "g", "g#", "a", "a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b", "c", "c#", "d", "d#", "e"},
    {"a", "a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a"},
    {"d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b", "c", "c#", "d"},
    {"g", "g#", "a", "a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g"},
    {"b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b"},
    {"e", "f", "f#", "g", "g#", "a", "a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#", "a", "a#", "b", "c", "c#", "d", "d#", "e"}
  };

  public Tab(Note note, int[] range, TabNote[] beforeTabNote) {
    this.note = note;
    this.range = range;
    this.tabNote = tabOut();
    this.beforeTabNote = beforeTabNote;
  }

  public TabNote[] getTabNote() {
    return tabNote;
  }

  private ArrayList<TabNote> getOptions(ArrayList<String[]> trueFb, String noteName) {
    ArrayList<TabNote> potentialTabs = new ArrayList<TabNote>();
    for (int i = 0; i < trueFb.size(); i++) {
      for (int j = 0; j < trueFb.get(i).length; j++) {
        if (trueFb.get(i)[j].equals(noteName)) {
          potentialTabs.add(new TabNote(i + range[2], j + range[0]));
        }
      }
    }
    return potentialTabs;
  }

  public TabNote[] tabOut() {
    ArrayList<String[]> trueFb = new ArrayList<String[]>();
    for(int i = range[2] - 1; i < range[3]; i++) {
      trueFb.add(Arrays.copyOfRange(fb[i], range[0], range[1]));
    }
    ArrayList<TabNote> potentialPrimary = getOptions(trueFb, note.getNote());
    TabNote primary = null;
    if (beforeTabNote == null) { //first note
      primary = potentialPrimary.get(0);
    } else {
      int[] scores = new int[potentialPrimary.size()];
      for (int i = 0; i < potentialPrimary.size(); i++) { 
        //one point for being less than four frets away, two points for being less than three frets away, three points for being less than two frets away
        if (beforeTabNote[0].getFret() - potentialPrimary.get(i).getFret() > -4 && beforeTabNote[0].getFret() - potentialPrimary.get(i).getFret() < 4) {
          scores[i] ++;
          if (beforeTabNote[0].getFret() - potentialPrimary.get(i).getFret() > -3 && beforeTabNote[0].getFret() - potentialPrimary.get(i).getFret() < 3) {
            scores[i] ++;
            if (beforeTabNote[0].getFret() - potentialPrimary.get(i).getFret() > -2 && beforeTabNote[0].getFret() - potentialPrimary.get(i).getFret() < 2) {
              scores[i] ++;
            }
          }
        } else {
          scores[i] = 0; //if not within four frets, score is zero
        }
        //one point for being less than 3 strings away, two points for less than 2 strings away
        if (beforeTabNote[0].getStr() - potentialPrimary.get(i).getStr() > -3 && beforeTabNote[0].getStr() - potentialPrimary.get(i).getStr() < 3) {
          scores[i] ++;
          if (beforeTabNote[0].getStr() - potentialPrimary.get(i).getStr() > -2 && beforeTabNote[0].getStr() - potentialPrimary.get(i).getStr() < 2) {
            scores[i] ++;
          }
        }
      }
      int maxScore = 0;
      ArrayList<Integer> highScorers = new ArrayList<Integer>();
      for (int i = 0; i < scores.length; i++) {
        if (scores[i] > maxScore) {
          maxScore = scores[i];
        }
      }
      for (int i = 0; i < scores.length; i++) {
        if (scores[i] == maxScore) {
          highScorers.add(i);
        }
      }
      int rand = ThreadLocalRandom.current().nextInt(0, highScorers.size());
      primary = potentialPrimary.get(highScorers.get(rand));
    }
    if (note.getChordNote() != null) { //if this is a double stop
      ArrayList<TabNote> potentialSecondary = getOptions(trueFb, note.getChordNote());
      for (int i = 0; i < potentialSecondary.size(); i++) {
        if (potentialSecondary.get(i).getStr() == primary.getStr()) {
          potentialSecondary.remove(i); //remove same string options
        }
      }
      int[] scores = new int[potentialSecondary.size()];
      for (int i = 0; i < potentialSecondary.size(); i++) { 
        //one point for being less than four frets away, two points for being less than three frets away
        if (primary.getFret() - potentialSecondary.get(i).getFret() > -4 && primary.getFret() - potentialSecondary.get(i).getFret() < 4) {
          scores[i] ++;
          if (primary.getFret() - potentialSecondary.get(i).getFret() > -3 && primary.getFret() - potentialSecondary.get(i).getFret() < 3) {
            scores[i] ++;
          }
        }
        //one point for being less than 3 strings away, two points for being one string away
        if (primary.getStr() - potentialSecondary.get(i).getStr() > -3 && primary.getStr() - potentialSecondary.get(i).getStr() < 3) {
          scores[i] ++;
          if (primary.getStr() - potentialSecondary.get(i).getStr() > -2 && primary.getStr() - potentialSecondary.get(i).getStr() < 2) {
            scores[i] ++;
          }
        }
      }
      int maxScore = 0;
      ArrayList<Integer> highScorers = new ArrayList<Integer>();
      for (int i = 0; i < scores.length; i++) {
        if (scores[i] > maxScore) {
          maxScore = scores[i];
        }
      }
      for (int i = 0; i < scores.length; i++) {
        if (scores[i] == maxScore) {
          highScorers.add(i);
        }
      }
      int rand = ThreadLocalRandom.current().nextInt(0, highScorers.size());
      return new TabNote[] {primary, potentialSecondary.get(highScorers.get(rand))};
    } else {
      return new TabNote[] {primary};
    }
  }

  public void print() {
    for (int i = 0; i < tabNote.length; i++) {
      System.out.println(tabNote[i].getStr() + ", " + tabNote[i].getFret());
    }
  }
}