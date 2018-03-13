package stats.data;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionsStatsHandler implements StatsHandler {
  private QuestionsStats stats;

  @Override
  public void sate(Scanner scanner) {
    String sessionID = scanner.nextLine();
    String request = scanner.nextLine();
    List<String> stackoverflowThreadIDs = new ArrayList<>();
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      stackoverflowThreadIDs.add(line);
    }
    stats = new QuestionsStats(sessionID, request, stackoverflowThreadIDs);
  }

  @Override
  public void exhaust(PrintWriter printWriter) {
    printWriter.println(stats.sessionID);
    printWriter.println(stats.request);
    for (String stackoverflowThreadID: stats.stackoverflowThreadIDs) {
      printWriter.println(stackoverflowThreadID);
    }
  }

  class QuestionsStats {
    final String sessionID;
    final String request;
    final List<String> stackoverflowThreadIDs;

    QuestionsStats(String sessionID, String request, List<String> stackoverflowThreadIDs) {
      this.sessionID = sessionID;
      this.request = request;
      this.stackoverflowThreadIDs = stackoverflowThreadIDs;
    }
  }
}