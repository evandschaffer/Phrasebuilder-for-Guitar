import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Lick {
  private Scale scale;
  private int[] tSig; // {[12,8} = 12/8
  private Note[] notes;
  private ArrayList<Integer> noteLengthOrder;

  public Lick(Scale scale, int[] tSig) {
    this.scale = scale;
    this.tSig = tSig;
    this.noteLengthOrder = determineOrder();
    this.notes = buildLick();
  }

  public Note[] getNotes() {
    return this.notes;
  }

  private ArrayList<Integer> determineOrder() {
    int defaultNoteLength = tSig[1];
    float beats = 0; //increments with each note
    float goalBeats = (float) tSig[0] / defaultNoteLength * 4; //refactor to put time signature over 4
    ArrayList<Integer> noteLengthOrder = new ArrayList<Integer>();
    while (beats < goalBeats) {
      if (goalBeats - beats <= 4.0f / defaultNoteLength) { //finish phrase cleanly
        noteLengthOrder.add((int) ((goalBeats - beats) * 4));
        beats = goalBeats; //reach loop condition
      } else if ((tSig[0] % 2 == 0 && beats % (goalBeats / (defaultNoteLength / 2)) == 0) || beats == 0) { //accented notes
        //if time signature can be reduced, and the current beat fits within it, treat it as an accent
        int rand = ThreadLocalRandom.current().nextInt(1, 4); //random number 1-3
        if (rand == 1) { //add longer note
          noteLengthOrder.add(defaultNoteLength/2);
          beats += 4.0f / (defaultNoteLength/2);
        } else if (rand == 2) { //add triplets
          noteLengthOrder.add(defaultNoteLength*10); //divisible by 10 indicates a triplet
          noteLengthOrder.add(defaultNoteLength*10); //add 3 in order to achieve round number of beats and a more coherent user experience
          noteLengthOrder.add(defaultNoteLength*10); 
          beats += 4.0f / (defaultNoteLength/2); //add value of 2 normal notes (triplets play 3 notes in the space of 2)
        } else { //add normal note
          noteLengthOrder.add(defaultNoteLength);
          beats += 4.0f / defaultNoteLength;
        }
      } else { //non-accented notes
        int rand = ThreadLocalRandom.current().nextInt(1, 4); //random number 1-3
        if (rand == 1) { //add normal note
          noteLengthOrder.add(defaultNoteLength);
          beats += 4.0f / defaultNoteLength;
        } else if (rand == 2) { //add 2 shorter notes
          noteLengthOrder.add(defaultNoteLength*2);
          noteLengthOrder.add(defaultNoteLength*2);
          beats += 4.0f / (defaultNoteLength);
        } else { //add 1 shorter note
          noteLengthOrder.add(defaultNoteLength*2);
          beats += 4.0f / (defaultNoteLength);
        }
      }
    }
    return noteLengthOrder;
  }

  private Note[] buildLick() {
    boolean newNote; //ensure no repeat notes
    Note[] notes = new Note[noteLengthOrder.size()];
    if (noteLengthOrder.get(0) % 10 == 0) { //first note must be root
      notes[0] = new Note(scale.getScaleNotes()[0], false, noteLengthOrder.get(0)/10, true, null); 
    } else {
      notes[0] = new Note(scale.getScaleNotes()[0], false, noteLengthOrder.get(0), false, null);
    }
    for (int i = 1; i < notes.length; i++) {
      newNote = false;
      while (!newNote) {
        int rand = ThreadLocalRandom.current().nextInt(1, scale.getScaleNotes().length); //random note within scale
        if (noteLengthOrder.get(i) % 10 == 0) {
          notes[i] = new Note(scale.getScaleNotes()[rand], false, noteLengthOrder.get(i)/10, true, null);
        } else {
          notes[i] = new Note(scale.getScaleNotes()[rand], false, noteLengthOrder.get(i), false, null);
        }
        if (notes[i-1].getNote() != notes[i].getNote()) {
          newNote = true;
        } 
      }
    }
    return null;
  }


  public void regenerate() {
    this.noteLengthOrder = determineOrder();
    this.notes = buildLick();
  }
}