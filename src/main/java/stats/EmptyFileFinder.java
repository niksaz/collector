package stats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

public class EmptyFileFinder {
  public static int findFreeFileNumberInDirectory(Path directory) throws IOException {
    return Files.list(directory)
        .map(path -> Integer.parseInt(path.getFileName().toString()))
        .max(Comparator.naturalOrder())
        .orElse(-1) + 1;
  }
}