package stats.handlers;

import java.io.PrintWriter;
import java.util.Scanner;

public class MarkedStatsHandler implements StatsHandler {
  private MarkedStats stats;

  @Override
  public void sate(Scanner scanner) {
    String sessionID = scanner.nextLine();
    String questionID = scanner.nextLine();
    stats = new MarkedStats(sessionID, questionID);
  }

  @Override
  public void exhaust(PrintWriter printWriter) {
    printWriter.println(stats.sessionID);
    printWriter.println(stats.questionID);
  }

  class MarkedStats {
    final String sessionID;
    final String questionID;

    MarkedStats(String sessionID, String questionID) {
      this.sessionID = sessionID;
      this.questionID = questionID;
    }
  }
}