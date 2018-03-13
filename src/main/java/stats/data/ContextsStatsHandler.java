package stats.data;

import java.io.PrintWriter;
import java.util.Scanner;

public class ContextsStatsHandler implements StatsHandler {
  private ContextsStats stats;

  @Override
  public void sate(Scanner scanner) {
    String installationID = scanner.nextLine();
    String sessionID = scanner.nextLine();
    String caretOffset = scanner.nextLine();
    StringBuilder documentText = new StringBuilder();
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      documentText.append(line);
      documentText.append('\n');
    }
    stats = new ContextsStats(installationID, sessionID, caretOffset, documentText.toString());
  }

  @Override
  public void exhaust(PrintWriter printWriter) {
    printWriter.println(stats.installationID);
    printWriter.println(stats.sessionID);
    printWriter.println(stats.caretOffset);
    printWriter.println(stats.documentText);
  }

  class ContextsStats {
    final String installationID;
    final String sessionID;
    final String caretOffset;
    final String documentText;

    ContextsStats(
        String installationID, String sessionID, String caretOffset, String documentText) {
      this.installationID = installationID;
      this.sessionID = sessionID;
      this.caretOffset = caretOffset;
      this.documentText = documentText;
    }
  }
}