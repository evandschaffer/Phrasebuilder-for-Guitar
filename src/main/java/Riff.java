import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Riff {
  private Scale scale;
  private int[] tSig; // {12,8} = 12/8
  private Note[] notes;

  public Riff(Scale scale, int[] tSig, String ap) {
    this.scale = scale;
    this.tSig = tSig;
    this.ap = ap;
    this.notes = buildRiff();
  }

  public Note[] getNotes() {
    return this.notes;
  }

  private ArrayList<Integer> determineOrder() {
    int defaultNoteLength = tSig[1];
    float beats = 0; //increments with each note
    float goalBeats = (float) tSig[0] / defaultNoteLength * 4; //refactor to put time signature over 4
    ArrayList<Integer[2]> noteLengthOrder = new ArrayList<Integer[2]>(); //[length, pm]
    while (beats < goalBeats) {
      if (goalBeats - beats <= 4.0f / defaultNoteLength) { //finish phrase cleanly
        noteLengthOrder.add([(int) ((goalBeats - beats) * 4), 0]);
        beats = goalBeats; //reach loop condition
      } else if ((tSig[0] % 2 == 0 && beats % (goalBeats / (defaultNoteLength / 2)) == 0) || beats == 0) { //accented notes
        //if time signature can be reduced, and the current beat fits within it, treat it as an accent
        int rand = ThreadLocalRandom.current().nextInt(1, 4); //random number 1-3
        if (rand == 1) { //add longer note
          noteLengthOrder.add([defaultNoteLength/2, 0]);
          beats += 4.0f / (defaultNoteLength/2);
        } else if (rand == 2) { //add gallop
          noteLengthOrder.add([defaultNoteLength*2, 1]);
          noteLengthOrder.add([defaultNoteLength*2, 1]);
          noteLengthOrder.add([defaultNoteLength, 1]); 
          beats += 4.0f / (defaultNoteLength/2); //0.5 + 0.5 + 1 = 2
        } else { //add normal note
          noteLengthOrder.add([defaultNoteLength, 0]);
          beats += 4.0f / defaultNoteLength;
        }
      } else { //non-accented notes
        int rand = ThreadLocalRandom.current().nextInt(1, 4); //random number 1-3
        if (rand == 1) { //add normal note
          noteLengthOrder.add([defaultNoteLength, 1]);
          beats += 4.0f / defaultNoteLength;
        } else if (rand == 2) { //add 2 shorter notes
          noteLengthOrder.add([defaultNoteLength*2, 1]);
          noteLengthOrder.add([defaultNoteLength*2, 1]);
          beats += 4.0f / (defaultNoteLength);
        } else { //add 1 shorter note
          noteLengthOrder.add([defaultNoteLength*2, 0]);
          beats += 4.0f / (defaultNoteLength);
        }
      }
    }
    return noteLengthOrder;
  }

  private Note[] buildRiff() {
    boolean newNote; //ensure no repeat notes on non-palm muted notes
    Note[] notes = new Note[noteLengthOrder.size()];
    return notes;
  }
}