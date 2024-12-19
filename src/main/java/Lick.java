public class Lick {
  private Scale scale;
  private int[] tSig; // [12,8] = 12/8
  private String ap; //accent pattern
  private boolean isChr; //chromaticism
  private String root; //first note

  public Lick(Scale scale, int[] tSig, String ap, boolean isChr, String root) {
    this.scale = scale;
    this.tSig = tSig;
    this.ap = ap;
    this.isChr = isChr;
    this.root = root;
  }
}