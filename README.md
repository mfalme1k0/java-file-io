# java-file-io

Hands-on exercises for **Java I/O streams**: how bytes move between a Java program and the keyboard, screen, memory and files.

Each Java file in this repo is a small, runnable answer to one "live challenge" from a six-part lesson on streams. The lesson itself is included in the repo:

- [`Java I_O Streams.md`](<Java I_O Streams.md>): the lesson text, with the six ideas and the challenge brief for each
- [`media/java-io-streams.pptx`](media/java-io-streams.pptx): the slide version of the same lesson

Read the idea in the lesson, then open the matching file below.

---

## Contents

1. [Requirements](#requirements)
2. [Quick start](#quick-start)
3. [What's in the repo](#whats-in-the-repo)
4. [The core idea](#the-core-idea)
5. [Walkthrough: the six exercises](#walkthrough-the-six-exercises)
6. [Extra: text files with `PrintWriter` and `Scanner`](#extra-text-files-with-printwriter-and-scanner)
7. [Generated files](#generated-files)
8. [Known issues](#known-issues)
9. [Next steps](#next-steps)

---

## Requirements

**JDK 25 or newer.** Several classes declare `static void main(...)` without `public`, and two (`ShoutingInputStream`, `SpeedRace`) use `static void main()` with no parameters. Those entry points only work in JDK 25+. The IntelliJ project in this repo is configured for `openjdk-26`.

On an older JDK, change those signatures to the classic form:

```java
public static void main(String[] args) throws IOException { ... }
```

Check your version with `java -version`.

## Quick start

All paths in the code are relative (`media/bright.jpg`, `big.bin`, `text.txt`), so **run everything from the repository root**.

```bash
git clone https://github.com/mfalme1k0/java-file-io.git
cd java-file-io

# compile every class into build/
javac -d build *.java

# run any exercise by class name
java -cp build ByteEcho
java -cp build AlphabetPrinter
java -cp build ChunkReader
java -cp build CopyMachine
java -cp build ShoutingInputStream
java -cp build SpeedRace
java -cp build Demo
```

Or open the folder in IntelliJ IDEA and run the classes from the gutter icon.

## What's in the repo

| File | Lesson idea | What it does |
|------|-------------|--------------|
| `ByteEcho.java` | 1. Overview | Reads `System.in` one byte at a time and prints each byte's number and character |
| `AlphabetPrinter.java` | 2. Output streams | Writes A–Z to `System.out` as raw bytes, then `"JAVA"` as a byte array |
| `ChunkReader.java` | 3. Input streams | Reads an in-memory string 5 bytes at a time |
| `CopyMachine.java` | 4. File streams | Copies `media/bright.jpg` to `photocopy.jpg` byte by byte |
| `ShoutingInputStream.java` | 5. Filter streams | A custom `FilterInputStream` that uppercases letters as they're read |
| `SpeedRace.java` | 6. Buffered streams | Two copy methods: plain vs. buffered |
| `Main.java` | 6. Buffered streams | Times both copy methods and prints the speed-up |
| `MakeBigFile.java` | 6. Buffered streams | Generates a 10 MB random `big.bin` to copy |
| `Demo.java` | Extra | Writes and reads `text.txt` using `PrintWriter` and `Scanner` |
| `Java I_O Streams.md` | Lesson | Lesson text and challenge briefs |
| `media/` | Lesson | `bright.jpg` (the copy source) and the slide deck |

## The core idea

A **stream** is a one-way pipe of bytes. Data flows *into* your program through an input stream, or *out of* it through an output stream. A byte is just a number from 0 to 255, and everything, including text and images, is bytes underneath.

```
 keyboard / file / memory ──► InputStream ──► your program
 your program ──► OutputStream ──► screen / file / memory
```

Java provides two ready-made pipes: `System.in` (keyboard) and `System.out` (screen).

Four rules come up in every exercise:

| Rule | Why it matters |
|------|----------------|
| `read()` returns **-1** when there's nothing left | It's how every read loop knows to stop. It returns an `int`, not a `byte`, so that -1 can't be confused with a real byte value. |
| `read(byte[])` returns **how many bytes were filled** | The last chunk is usually shorter than the buffer. Only use the first `count` bytes. |
| **Close** what you open | Open files hold OS resources. `try-with-resources` closes them automatically, even on exceptions. |
| **Wrap** a stream to change its behaviour | Filter and buffered streams take another stream in their constructor and add behaviour on top. |

## Walkthrough: the six exercises

### 1. Byte Echo: `ByteEcho.java`

Reads from the keyboard one byte at a time until it sees byte `10` (newline).

```java
int value;
while ((value = System.in.read()) != 10) {
    System.out.println(value + "->" + (char) value);
}
```

```
$ java -cp build ByteEcho
Hi
72->H
105->i
```

**Takeaway:** input is just numbers; `(char)` is what turns a byte into a letter. Note that this only handles single-byte (ASCII) characters correctly. On Windows, Enter sends bytes `13, 10`, so a `13->` line appears before the loop stops.

### 2. Alphabet Printer: `AlphabetPrinter.java`

Prints A–Z without `println`, using `System.out` purely as an output stream.

```java
for (int i = 65; i <= 90; i++) {
    System.out.write(i);          // single-byte write
}
System.out.write(10);             // newline byte

byte[] data = "JAVA".getBytes(StandardCharsets.UTF_8);
System.out.write(data);           // many bytes at once
```

```
ABCDEFGHIJKLMNOPQRSTUVWXYZ
JAVA
```

**Takeaway:** `write(int)` sends one byte, `write(byte[])` sends a batch, and strings convert to bytes with `getBytes(...)`. Always name the charset explicitly.

### 3. Chunk Reader: `ChunkReader.java`

Wraps the bytes of `"Hello, Java streams!"` in a `ByteArrayInputStream` and reads them into a 5-byte bucket until `read` returns -1.

```java
while ((count = input.read(buffer)) != -1) {
    System.out.write(buffer, 0, count);   // only the bytes actually filled
    System.out.println();
}
```

```
Hello
, Jav
a str
eams!
```

**Takeaway:** `ByteArrayInputStream` shows that a stream's source doesn't have to be a file; memory works the same way. The `0, count` arguments matter because the final chunk may be partial (not in this 20-byte example, but it will be for any length that isn't a multiple of 5).

### 4. Copy Machine: `CopyMachine.java`

Copies `media/bright.jpg` to `photocopy.jpg` one byte at a time using `FileInputStream` and `FileOutputStream`, both declared in a `try-with-resources` block.

```java
try (FileInputStream input = new FileInputStream(source);
     FileOutputStream output = new FileOutputStream(target)) {
    int value;
    while ((value = input.read()) != -1) {
        output.write(value);
        total++;
    }
}
```

```
Copied:636500bytes
```

**Takeaway:** file streams don't care whether the file is text or an image; they move bytes. `FileOutputStream` **overwrites** the target by default; pass `true` as a second argument to append instead.

### 5. Shouting Filter: `ShoutingInputStream.java`

A custom filter stream. It extends `FilterInputStream`, wraps any other `InputStream`, and uppercases lowercase letters as they pass through.

```java
@Override
public int read() throws IOException {
    int value = super.read();
    if (value == -1) return -1;                  // preserve the end signal
    if (value >= 'a' && value <= 'z') value = value - 'a' + 'A';
    return value;
}
```

```
QUIET TEXT, 123!
```

**Takeaway:** a filter is a wrapper. The `-1` must be passed through untouched, or the reader loop never ends.

> **Gotcha:** only the single-byte `read()` is overridden. `FilterInputStream.read(byte[], int, int)` delegates straight to the wrapped stream, so code that reads in chunks would bypass the uppercasing. Override that method too if you want the filter to work with array reads.

### 6. Speed Race: `SpeedRace.java`, `Main.java`, `MakeBigFile.java`

Copies a large file twice with identical byte-by-byte loops. The only difference is that the second copy wraps both streams in `BufferedInputStream` / `BufferedOutputStream`.

```java
// plain: every read()/write() is a call to the OS
FileInputStream in = new FileInputStream(source);
FileOutputStream out = new FileOutputStream(target);

// buffered: reads and writes go through an in-memory buffer
BufferedInputStream in = new BufferedInputStream(new FileInputStream(source));
BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(target));
```

- `MakeBigFile` creates `big.bin`: 10 MB of random bytes via `Files.write`.
- `SpeedRace` has the two copy methods (`copyPlain`, `copyBuffered`).
- `Main` times each with `System.nanoTime()` and prints:

```
Plain: <n> ms
Buffered: <n> ms
Buffered was <x>x faster
```

Your numbers depend on your disk and OS. The size of the gap is the point.

**Takeaway:** talking to the disk is slow; a buffer batches many small operations into few large ones. Buffered output only reaches the disk on `flush()` or `close()`, which `try-with-resources` does for you. Skip both and the last bytes can go missing.

## Extra: text files with `PrintWriter` and `Scanner`

`Demo.java` isn't one of the six challenges. It shows the higher-level, text-oriented tools that sit on top of streams:

```java
File file = new File("text.txt");

PrintWriter output = new PrintWriter(file);   // write
output.println("Kood is cool");
output.println(2026);
output.close();

Scanner input = new Scanner(file);            // read
String name = input.nextLine();
int year = input.nextInt();
System.out.printf("Name: %s year: %d\n", name, year);
input.close();
```

```
Name: Kood is cool year: 2026
```

`PrintWriter` gives you `print`/`println`/`printf` for files, and `Scanner` parses lines and typed values (`nextLine`, `nextInt`). Unlike the byte streams above, these work with text and characters. Both are closed manually here; wrapping them in `try-with-resources` would be the safer pattern.

## Generated files

Running the exercises produces output files. These are not source code:

| File | Created by |
|------|-----------|
| `photocopy.jpg` | `CopyMachine` |
| `plaincopy.bin`, `bufferedcopy.bin` | `SpeedRace.main` |
| `plain-copy.bin`, `buffered-copy.bin` | `Main` |
| `big.bin` | `MakeBigFile` (10 MB; a copy is already committed) |
| `text.txt` | `Demo` |
| `out/` | IntelliJ's compiled output |

## Known issues

These come from reading the code against the lesson's challenge briefs.


- **`CopyMachine` implements only part of its brief.** The lesson also asks for a polite "Source file not found" message and for both file sizes to be printed so they can be compared. It currently prints a single total (`Copied:636500bytes`, with no space) and a generic error message.
- **`big.bin` is 10 MB.** The lesson suggests 50 MB or more to make the timing gap obvious. Change the size in `MakeBigFile` to try it.

## Next steps

Ideas that build directly on what's already here:

- Fix the known issues above, then re-run `Main` to see the real buffered vs. plain gap.The 50mb file is too big though, you might want to keep big.bin at 10mb.
- Replace the one-byte loops in `CopyMachine` with `read(byte[])` / `write(byte[], 0, count)` and add a third timing to the race.
- Add the **ROT13** output filter from the lesson's bonus for challenge 5.
- Add the **append-mode** bonus from challenge 4: log each copy to `copy-log.txt` using `new FileOutputStream("copy-log.txt", true)`.
- Add a `.gitignore` for `out/`, `build/`, `*.bin` and `photocopy.jpg`, so generated files stop being committed.