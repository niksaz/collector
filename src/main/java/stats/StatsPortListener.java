package stats;

import stats.handlers.StatsHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class StatsPortListener implements Runnable {
  private final Path directory;
  private final int portNumber;
  private final StatsHandler statsHandler;

  StatsPortListener(Path directory, int portNumber, StatsHandler statsHandler) {
    this.directory = directory;
    this.portNumber = portNumber;
    this.statsHandler = statsHandler;
  }

  public void run() {
    try (
        ServerSocket serverSocket = new ServerSocket(portNumber)
    ) {
      System.out.println("Server socket for " + directory + " is opened");
      int fileNumber = FreeFileFinder.findFreeFileNumberInDirectory(directory);
      while (!Thread.interrupted()) {
        try (
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            Scanner scanner = new Scanner(in)
        ) {
          statsHandler.sate(scanner);
          fileNumber = writeStatsToFile(statsHandler, fileNumber);
          System.out.println("Collected " + fileNumber + " files in " + directory);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private int writeStatsToFile(StatsHandler statsHandler, int fileNumber) throws IOException {
    Path file = directory.resolve(Integer.toString(fileNumber));
    try (
        OutputStream outputStream = Files.newOutputStream(file);
        PrintWriter printWriter = new PrintWriter(outputStream)
    ) {
      statsHandler.exhaust(printWriter);
      fileNumber += 1;
    }
    return fileNumber;
  }
}