package stats.handlers;

import java.io.PrintWriter;
import java.util.Scanner;

public interface StatsHandler {
  void sate(Scanner scanner);

  void exhaust(PrintWriter printWriter);
}