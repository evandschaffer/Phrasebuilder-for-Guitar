import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Lick {
  private Scale scale;
  private int[] tSig; // [12,8] = 12/8
  private boolean isChr; // chromaticism
  private String root; // first note
  private Note[] notes;
  private ArrayList<Integer> noteLengthOrder;

  public Lick(Scale scale, int[] tSig, boolean isChr, String root) {
    this.scale = scale;
    this.tSig = tSig;
    this.isChr = isChr;
    this.root = root;
    this.noteLengthOrder = determineOrder();
    this.notes = buildLick();
  }

  public Note[] getNotes() {
    return this.notes;
  }

  private ArrayList<Integer> determineOrder() {
    int defaultNoteLength = tSig[1];
    float beats = 0;
    float goalBeats = (float) tSig[0] / defaultNoteLength * 4; // refactor to put time signature over 4
    ArrayList<Integer> noteLengthOrder = new ArrayList<Integer>();
    while (beats < goalBeats) {
      if (goalBeats - beats <= 4.0f / defaultNoteLength) {
        noteLengthOrder.add((int) ((goalBeats - beats) * 4));
      } else if ((tSig[0] % 2 == 0 && beats % (goalBeats / (defaultNoteLength / 2)) == 0) || beats == 0) {
        int rand = ThreadLocalRandom.current().nextInt(1, 4);
        if (rand == 1) {

        } else if (rand == 2) {

        } else {
          noteLengthOrder.add(defaultNoteLength);
          beats = beats + 4.0f / defaultNoteLength;
        }
      }
    }
    return noteLengthOrder;
  }

  private Note[] buildLick() {
    Note[] notes = new Note[noteLengthOrder.size()];
    for (int i = 0; i < notes.length; i++) {
      notes[i] = null;
    }
    return null;
  }

  public void regenerate() {
    this.noteLengthOrder = determineOrder();
    this.notes = buildLick();
  }
}