package stats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
  private static final int CONTEXTS_PORT_NUMBER = 25001;
  private static final int QUESTIONS_PORT_NUMBER = 25002;
  private static final int CLICKS_PORT_NUMBER = 25003;
  private static final int HELPFUL_PORT_NUMBER = 25004;

  private static final Path DATA_DIR = Paths.get("data");

  private static final Path CONTEXTS_DIR = DATA_DIR.resolve("contexts");
  private static final Path QUESTIONS_DIR = DATA_DIR.resolve("questions");
  private static final Path CLICKS_DIR = DATA_DIR.resolve("clicks");
  private static final Path HELPFUL_DIR = DATA_DIR.resolve("helpful");

  public static void main(String[] args) {
    try {
      ensureAllDirectoriesCreated();
      ContextsListener contextsListener = new ContextsListener(CONTEXTS_DIR, CONTEXTS_PORT_NUMBER);
      contextsListener.start();
    } catch (IOException e) {
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