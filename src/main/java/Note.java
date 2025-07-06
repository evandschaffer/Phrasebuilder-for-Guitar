public class Note {
  private String note;
  private boolean pm; //palm mute
  private float beats; //4.0 for whole, 0.5 for eigth, 1*(2/3) for quarter triplet
  private boolean triplet;
  private String chordNote; //allows double stops

  public Note (String note, boolean pm, int noteType, boolean triplet, String chordNote) {
    this.note = note.toLowerCase();
    this.pm = pm;
    this.triplet = triplet;
    if (chordNote == null) {
      this.chordNote = chordNote;
    } else {
      this.chordNote = chordNote.toLowerCase();
    }
    if (triplet) {
      this.beats = 4.0f/noteType*(2.0f/3.0f);
    } else {
      this.beats = 4.0f/noteType;
    }
  }
  
  public String getNote() {
	 return note;
  }
  
  public boolean isPm() {
  	return this.pm;
  }
  
  public float getBeats() {
	 return this.beats;
  }
  

  public boolean isTriplet() {
  	return this.triplet;
  }
  

  public String getChordNote() {
	 return this.chordNote;
  }

  public void setNote(String note) {
   this.note = note.toLowerCase();
  }

  public void setPm(boolean pm) {
    this.pm = pm;
  }

  public void setBeats(int noteType, boolean triplet) {
    if (triplet) {
      this.beats = 4.0f/noteType*(2.0f/3.0f);
    } else {
      this.beats = 4.0f/noteType;
    } this.triplet = triplet;
  }

  public void setChordnote(String chordNote) {
    this.chordNote = chordNote;
  }
}