import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Riff {
  private Scale scale;
  private int[] tSig; // {12,8} = 12/8
  private Note[] notes;
  private ArrayList<Integer[]> noteLengthOrder;

  public Riff(Scale scale, int[] tSig) {
    this.scale = scale;
    this.tSig = tSig;
    this.noteLengthOrder = determineOrder();
    this.notes = buildRiff();
  }

  public Note[] getNotes() {
    return this.notes;
  }

  private String chordInterval(int degree) {
    int rand = ThreadLocalRandom.current().nextInt(0,8); //random number 0-7
    int[] chordDegrees = {2,2,3,3,4,4,5,6}; //make common intervals (third, fourth, fifth) more likely to be selected
    if (degree + chordDegrees[rand] >= scale.getScaleNotes().length) { //prevent going out of bounds
      return scale.getScaleNotes()[degree + chordDegrees[rand] - scale.getScaleNotes().length];
    } else {
      return scale.getScaleNotes()[degree + chordDegrees[rand]];
    }
  }

  private ArrayList<Integer[]> determineOrder() {
    int defaultNoteLength = tSig[1];
    float beats = 0; //increments with each note
    float goalBeats = (float) tSig[0] / defaultNoteLength * 4; //refactor to put time signature over 4
    ArrayList<Integer[]> noteLengthOrder = new ArrayList<Integer[]>(); //[length, pm]
    while (beats < goalBeats) {
      if (goalBeats - beats <= 4.0f / defaultNoteLength) { //finish phrase cleanly
        noteLengthOrder.add(new Integer[]{(int) ((goalBeats - beats) * 4), 0});
        beats = goalBeats; //reach loop condition
      } else if ((tSig[0] % 2 == 0 && beats % (goalBeats / (defaultNoteLength / 2)) == 0) || beats == 0) { //accented notes
        //if time signature can be reduced, and the current beat fits within it, treat it as an accent
        int rand = ThreadLocalRandom.current().nextInt(1, 5); //random number 1-4
        if (rand == 1) { //add longer note
          noteLengthOrder.add(new Integer[]{defaultNoteLength/2, 0});
          beats += 4.0f / (defaultNoteLength/2);
        } else if (rand == 2) { //add gallop
          noteLengthOrder.add(new Integer[]{defaultNoteLength*2, 1});
          noteLengthOrder.add(new Integer[]{defaultNoteLength*2, 1});
          noteLengthOrder.add(new Integer[]{defaultNoteLength, 1}); 
          beats += 4.0f / (defaultNoteLength/2); //0.5 + 0.5 + 1 = 2
        } else if (rand == 3) { //add reverse gallop
          noteLengthOrder.add(new Integer[]{defaultNoteLength, 0});
          noteLengthOrder.add(new Integer[]{defaultNoteLength*2, 1});
          noteLengthOrder.add(new Integer[]{defaultNoteLength*2, 1}); 
          beats += 4.0f / (defaultNoteLength/2); //1 + 0.5 + 0.5 = 2
        } else { //add normal note
          noteLengthOrder.add(new Integer[]{defaultNoteLength, 0});
          beats += 4.0f / defaultNoteLength;
        }
      } else { //non-accented notes
        int rand = ThreadLocalRandom.current().nextInt(1, 4); //random number 1-3
        if (rand == 1) { //add normal note
          noteLengthOrder.add(new Integer[]{defaultNoteLength, 1});
          beats += 4.0f / defaultNoteLength;
        } else if (rand == 2) { //add 2 shorter notes
          noteLengthOrder.add(new Integer[]{defaultNoteLength*2, 1});
          noteLengthOrder.add(new Integer[]{defaultNoteLength*2, 1});
          beats += 4.0f / defaultNoteLength;
        } else { //add 1 shorter note
          noteLengthOrder.add(new Integer[]{defaultNoteLength*2, 0});
          beats += 4.0f / (defaultNoteLength*2);
        }
      }
    }
    return noteLengthOrder;
  }

  private Note[] buildRiff() {
    Note[] notes = new Note[noteLengthOrder.size()];
    if (noteLengthOrder.get(0)[1] > 0) { //first note
      notes[0] = new Note(scale.getScaleNotes()[0], true, noteLengthOrder.get(0)[0], false, null);
    } else {
      notes[0] = new Note(scale.getScaleNotes()[0], false, noteLengthOrder.get(0)[0], false, chordInterval(0));
    }
    for (int i = 1; i < notes.length; i++) {
      int rand = ThreadLocalRandom.current().nextInt(0, scale.getScaleNotes().length);
      int rand2 = ThreadLocalRandom.current().nextInt(0, 2); //random number 0-1
      String chordNote;
      if (rand2 > 0) { //allow chords
        chordNote = chordInterval(rand);
      } else {
        chordNote = null;
      }
      if (noteLengthOrder.get(i)[1] > 0) {
        if (rand2 == 0) {
          notes[i] = new Note(scale.getScaleNotes()[0], true, noteLengthOrder.get(i)[0], false, null); //make palm muted notes more likely to be the root
        } else {
          notes[i] = new Note(scale.getScaleNotes()[i], true, noteLengthOrder.get(i)[0], false, null);
        }
      } else {
        notes[i] = new Note(scale.getScaleNotes()[i], false, noteLengthOrder.get(i)[0], false, chordNote);
      }
    }
    return notes;
  }

  public void regenerate() {
    this.noteLengthOrder = determineOrder();
    this.notes = buildRiff();
  }
}