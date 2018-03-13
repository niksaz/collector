package stats;

public class ContextsStats {
  public final String installationID;
  public final String sessionID;
  public final int caretOffset;
  public final String documentText;

  public ContextsStats(
          String installationID, String sessionID, int caretOffset, String documentText) {
    this.installationID = installationID;
    this.sessionID = sessionID;
    this.caretOffset = caretOffset;
    this.documentText = documentText;
  }
}
