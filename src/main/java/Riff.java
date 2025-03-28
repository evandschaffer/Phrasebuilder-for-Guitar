public class Riff {
  private Scale scale;
  private int[] tSig; // [12,8] = 12/8
  private String ap; //accent pattern
  private boolean isChr; //chromaticism
  private String root; //first note
  private Note[] notes;

  public Riff(Scale scale, int[] tSig, String ap, boolean isChr, String root) {
    this.scale = scale;
    this.tSig = tSig;
    this.ap = ap;
    this.isChr = isChr;
    this.root = root;
    this.notes = buildRiff();
  }

  public Note[] getNotes() {
    return this.notes;
  }

  private Note[] buildRiff() {
    return null;
  }
}