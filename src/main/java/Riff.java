public class Riff {
  private Scale scale;
  private int[] tSig; // {12,8} = 12/8
  private String ap; //accent pattern
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

  private Note[] buildRiff() {
    return null;
  }
}