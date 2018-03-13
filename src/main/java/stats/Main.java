package stats;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
  private static final int PORT_NUMBER = 25000;

  private static final Path DATA_DIR = Paths.get("data");
  private static final Path CONTEXTS_DIR = DATA_DIR.resolve("contexts");
  private static final Path QUESTIONS_DIR = DATA_DIR.resolve("questions");
  private static final Path CLICKS_DIR = DATA_DIR.resolve("clicks");
  private static final Path HELPFUL_DIR = DATA_DIR.resolve("helpful");

  private static int writeStatsToFile(ContextsStats stats, int fileNumber) throws IOException {
    Path file = DATA_DIR.resolve(Integer.toString(fileNumber));
    try (
      OutputStream outputStream = Files.newOutputStream(file);
      PrintWriter printWriter = new PrintWriter(outputStream)
    ){
      printWriter.println(stats.installationID);
      printWriter.println(stats.sessionID);
      printWriter.println(stats.caretOffset);
      printWriter.println(stats.documentText);
      fileNumber += 1;
    }
    return fileNumber;
  }

  private static int findFreeFileNumberInDirectory(Path directory) throws IOException {
    return Files.list(directory)
      .map(path -> Integer.parseInt(path.getFileName().toString()))
      .max(Comparator.naturalOrder())
      .orElse(-1) + 1;
  }

  public static void main(String[] args) {
    ensureAllDirectoriesCreated();
    try (
      ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)
    ) {
      System.out.println("Server socket is opened");
      int fileNumber = findFreeFileNumberInDirectory(CONTEXTS_DIR);
      while (true) {
        try (
          Socket clientSocket = serverSocket.accept();
          BufferedReader in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));
          Scanner scanner = new Scanner(in)
        ) {
          System.out.println("New connection");
          String installationID = scanner.nextLine();
          String sessionID = scanner.nextLine();
          int caretOffset = Integer.parseInt(scanner.nextLine());
          StringBuilder documentText = new StringBuilder();
          while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            documentText.append(line);
            documentText.append('\n');
          }
          ContextsStats stats = new ContextsStats(
              installationID, sessionID, caretOffset, documentText.toString());
          fileNumber = writeStatsToFile(stats, fileNumber);
          System.out.println("Files collected " + fileNumber);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void ensureAllDirectoriesCreated() {
    try {
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
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}