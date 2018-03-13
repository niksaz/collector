package stats;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ContextsListener {
  private final Path directory;
  private final int portNumber;
  private volatile boolean shouldFinish = false;

  ContextsListener(Path directory, int portNumber) {
    this.directory = directory;
    this.portNumber = portNumber;
  }

  public void start() throws IOException {
    try (
        ServerSocket serverSocket = new ServerSocket(portNumber)
    ) {
      System.out.println("ContextsListener's server socket is opened");
      int fileNumber = EmptyFileFinder.findFreeFileNumberInDirectory(directory);
      while (!shouldFinish) {
        try (
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            Scanner scanner = new Scanner(in)
        ) {
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
          System.out.println("ContextsListener collected: " + fileNumber);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  private int writeStatsToFile(ContextsStats stats, int fileNumber) throws IOException {
    Path file = directory.resolve(Integer.toString(fileNumber));
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

  class ContextsStats {
    final String installationID;
    final String sessionID;
    final int caretOffset;
    final String documentText;

    ContextsStats(String installationID, String sessionID, int caretOffset, String documentText) {
      this.installationID = installationID;
      this.sessionID = sessionID;
      this.caretOffset = caretOffset;
      this.documentText = documentText;
    }
  }
}