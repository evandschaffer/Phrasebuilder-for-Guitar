public class Phrase {
  private Scale scale;
  private int[] tSig; // [12,8] = 12/8
  private String ap; //accent pattern
  private boolean isChr; //chromaticism
  private boolean isLead; //riff vs lick
  private Note[] notes;

  public Phrase(Scale scale, int[] tSig, String ap, boolean isChr, boolean isLead) {
    this.scale = scale;
    this.tSig = tSig;
    this.ap = ap;
    this.isChr = isChr;
    this.isLead = isLead;
    this.notes = this.buildPhrase();
  }

  public Scale getScale() {
    return this.scale;
  }

  public int[] getTSig() {
    return this.tSig;
  }

  public String getAp() {
    return this.ap;
  }

  public boolean getIsChr() {
    return this.isChr;
  }

  public boolean getIsLead() {
    return this.isLead;
  }

  public Note[] getNotes() {
    return this.notes;
  }

  public void setScale(Scale scale) {
    this.scale = scale;
  }

  public void setTSig(int[] tSig) {
    this.tSig = tSig;
  }

  public void getAp(String ap) {
    this.ap = ap;
  }

  public void setIsChr(boolean isChr) {
    this.isChr = isChr;
  }

  public void setIsLead(boolean isLead) {
    this.isChr = isLead;
  }

  public void setNotes(Note[] notes) {
    this.notes = notes;
  }
  
  private Note[] buildPhrase() {
    if (isLead) {
      Lick phrase = new Lick(this.scale, this.tSig, this.isChr);
      return phrase.getNotes();
    } else {
      Riff phrase = new Riff(this.scale, this.tSig, this.ap, this.isChr);
      return phrase.getNotes();
    }
  }
}