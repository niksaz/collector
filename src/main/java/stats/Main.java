package stats;

import stats.data.ContextsStatsHandler;
import stats.data.MarkedStatsHandler;
import stats.data.QuestionsStatsHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
  private static final Path DATA_DIR = Paths.get("data");

  private static final Path CONTEXTS_DIR = DATA_DIR.resolve("contexts");
  private static final Path QUESTIONS_DIR = DATA_DIR.resolve("questions");
  private static final Path CLICKS_DIR = DATA_DIR.resolve("clicks");
  private static final Path HELPFUL_DIR = DATA_DIR.resolve("helpful");

  private static final int CONTEXTS_PORT_NUMBER = 25001;
  private static final int QUESTIONS_PORT_NUMBER = 25002;
  private static final int CLICKS_PORT_NUMBER = 25003;
  private static final int HELPFUL_PORT_NUMBER = 25004;

  public static void main(String[] args) {
    try {
      ensureAllDirectoriesCreated();

      ContextsStatsHandler contextsStatsHandler = new ContextsStatsHandler();
      StatsPortListener contextsPortListener = new StatsPortListener(
          CONTEXTS_DIR, CONTEXTS_PORT_NUMBER, contextsStatsHandler);
      Thread contextsThread = new Thread(contextsPortListener);
      contextsThread.start();

      QuestionsStatsHandler questionsStatsHandler = new QuestionsStatsHandler();
      StatsPortListener questionsPortListener = new StatsPortListener(
          QUESTIONS_DIR, QUESTIONS_PORT_NUMBER, questionsStatsHandler);
      Thread questionsThread = new Thread(questionsPortListener);
      questionsThread.start();

      MarkedStatsHandler clicksStatsHandler = new MarkedStatsHandler();
      StatsPortListener clicksPortListener = new StatsPortListener(
          CLICKS_DIR, CLICKS_PORT_NUMBER, clicksStatsHandler);
      Thread clicksThread = new Thread(clicksPortListener);
      clicksThread.start();

      MarkedStatsHandler helpfulStatsHandler = new MarkedStatsHandler();
      StatsPortListener helpfulPortListener = new StatsPortListener(
          HELPFUL_DIR, HELPFUL_PORT_NUMBER, helpfulStatsHandler);
      Thread helpfulThread = new Thread(helpfulPortListener);
      helpfulThread.start();

      contextsThread.join();
      questionsThread.join();
      clicksThread.join();
      helpfulThread.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void ensureAllDirectoriesCreated() throws IOException {
    if (!Files.exists(DATA_DIR)) {
      Files.createDirectory(DATA_DIR);
    }
    if (!Files.exists(CONTEXTS_DIR)) {
      Files.createDirectory(CONTEXTS_DIR);
    }
    if (!Files.exists(QUESTIONS_DIR)) {
      Files.createDirectory(QUESTIONS_DIR);
    }
    if (!Files.exists(CLICKS_DIR)) {
      Files.createDirectory(CLICKS_DIR);
    }
    if (!Files.exists(HELPFUL_DIR)) {
      Files.createDirectory(HELPFUL_DIR);
    }
  }
}