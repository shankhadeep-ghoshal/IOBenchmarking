package benchmark;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

/**
 * @author <a href="mailto:shankhadeepghoshal1996@gmail.com">Shankhadeep Ghoshal</a>
 * @since 1.0
 */
@State(Scope.Benchmark)
public class IOBenchmark {
  private static final int FORK_COUNT = 1;
  private static final int WARMUP_ITERATIONS = 1;
  private static final File SMALL_FILE = /*new File(smallFile)*/
      new File("C:\\java\\benchmarking\\IOBenchmarking\\src\\main\\resources\\small-file.txt");
  private static final File FILE = /*new File(file)*/
      new File("C:\\java\\benchmarking\\IOBenchmarking\\src\\main\\resources\\file.txt");
  private static final File VERY_LARGE_FILE = /*new File(veryLargeFile)*/
      new File("C:\\java\\benchmarking\\IOBenchmarking\\src\\main\\resources\\very-large-file.txt");

  public static void main(String[] args) throws Exception {
    org.openjdk.jmh.Main.main(args);
  }

  private char readUsingFileInputStream(File file) throws IOException {
    char singleChar = 0;
    int singleCharInt;
    try (FileInputStream fileInputStream = new FileInputStream(file)) {
      while ((singleCharInt = fileInputStream.read()) != -1) {
        // comment out the following lines when running performance tests
        singleChar = (char) singleCharInt;
      }
    }

    return singleChar;
  }

  private int readUsingBufferedReader(File file) throws IOException {
    FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());

    // specify UTF-8 encoding explicitly
    InputStreamReader inputStreamReader =
        new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);

    int len = -1;
    try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        // comment out next line for performance tests
        len = line.length();
      }
    }

    return len;
  }

  private String readUsingScanner(File file) throws IOException {
    String line = null;
    try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8)) {
      boolean hasNextLine = false;
      while (hasNextLine = scanner.hasNextLine()) {
        line = scanner.nextLine();
      }
    }
    return line;
  }

  private List<String> readUsingFileReadAllLines(File file) throws IOException {
    return Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
  }

  private long readUsingFilesLines(File file) throws IOException {
    long cn = -1L;
    try (Stream<String> linesStream = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
      cn = linesStream.count();
    }
    return cn;
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingFileInputStreamVeryLargeFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingFileInputStream(VERY_LARGE_FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingFileInputStreamFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingFileInputStream(FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingFileInputStreamSmallFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingFileInputStream(SMALL_FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingBufferedReaderVeryLargeFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingBufferedReader(VERY_LARGE_FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingBufferedReaderFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingBufferedReader(FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingBufferedReaderSmallFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingBufferedReader(SMALL_FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingScannerVeryLargeFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingScanner(VERY_LARGE_FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingScannerFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingScanner(FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingScannerSmallFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingScanner(SMALL_FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingFileReadAllLinesVeryLargeFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingFileReadAllLines(VERY_LARGE_FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingFileReadAllLinesFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingFileReadAllLines(FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingFileReadAllLinesSmallFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingFileReadAllLines(FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingFilesLinesVeryLargeFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingFilesLines(VERY_LARGE_FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingFilesLinesFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingFilesLines(FILE));
  }

  @Benchmark
  @Warmup(iterations = WARMUP_ITERATIONS, time = 1)
  @Measurement(iterations = 1, time = 100, timeUnit = TimeUnit.MILLISECONDS)
  @Fork(value = FORK_COUNT, warmups = WARMUP_ITERATIONS)
  @OutputTimeUnit(value = TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.AverageTime)
  public void readUsingFilesLinesSmallFile(Blackhole blackhole) throws IOException {
    blackhole.consume(readUsingFilesLines(SMALL_FILE));
  }
}
