import java.util.ArrayList;

public class Lick {
  private Scale scale;
  private int[] tSig; // [12,8] = 12/8
  private String ap; //accent pattern
  private boolean isChr; //chromaticism
  private String root; //first note
  private Note[] notes;
  private ArrayList<Integer> noteLengthOrder;

  public Lick(Scale scale, int[] tSig, String ap, boolean isChr, String root) {
    this.scale = scale;
    this.tSig = tSig;
    this.ap = ap;
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
    float goalBeats = (float)tSig[0]/tSig[1] * 4; //refactor to put time signature over 4
    ArrayList<Integer> noteLengthOrder = new ArrayList<Integer>();
    while (beats < goalBeats) {
      if (goalBeats - beats <= 4.0/defaultNoteLength) {
        noteLengthOrder.add((int)(4/(goalBeats - beats)));
        break;
      }
    } return noteLengthOrder;
  }

  private Note[] buildLick() {
    Note[] notes = new Note[noteLengthOrder.size()];
    for (int i = 0; i < notes.length; i++) {
      notes[i] = null;
    }
    return null;
  }
}