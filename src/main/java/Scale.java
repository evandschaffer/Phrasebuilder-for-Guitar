import java.util.Arrays;

public class Scale {
  private int[] intervals; //number of semitones between notes
  private String name;
  private String key;
  private String[] scaleNotes;
  public static final String[] notes = {"a", "a#", "b", "c", "c#", "d", "d#", "e", "f", "f#", "g", "g#"};
  
  public Scale(int[] intervals, String name, String key) {
    this.intervals = intervals;
    this.name = name;
    this.key = key;
    setNotes();
  }

  public int[] getIntervals() {
    return this.intervals;
  }

  public String getName() {
    return this.name;
  }

  public String getKey() {
    return this.key;
  }

  public String[] getScaleNotes() {
    return this.scaleNotes;
  }

  public void setIntervals(int[] intervals) {
    this.intervals = intervals;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setKey(String key) {
    this.key = key;
  }

  private void setNotes() {
    this.scaleNotes = new String[this.intervals.length];
    int index = Arrays.asList(Scale.notes).indexOf(this.key);
    this.scaleNotes[0] = this.key;
    for (int i = 1; i < this.intervals.length; i++) {
      index += this.intervals[i-1];
      if (index > 11) {
        index -= 12;
      }
      this.scaleNotes[i] = Scale.notes[index];
    }
  }
}