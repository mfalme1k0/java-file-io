Today's session

# Java I/O Streams

How data flows in and out of a Java program.

Six ideas. After each one, a live challenge.

1 · Overview

## Data flows like water in a pipe

A **stream** is a one-way pipe carrying data between your program and somewhere else: keyboard, screen, file, network.

**Input stream**\
Data flows *into* your program (reading)

**Output stream**\
Data flows *out of* your program (writing)

**Bytes**\
The raw unit: a number from 0 to 255. Everything is bytes underneath, even text.

Java already gives you two pipes: `System.in` (keyboard) and `System.out` (screen).

1 · Live challenge

## The Byte Echo

The user types a short word and presses Enter. Your program reads from the keyboard **one byte at a time** and, for every byte, prints the number it received and the character it represents. It stops when it reads the Enter key.

1. Read from `System.in` using the single-byte read method.
2. Print each byte as a number, then convert it to a character.
3. Stop on the newline (byte 10).

Type: Hi 72 -> H 105 -> i Done (3 bytes incl. Enter)

**Shows:** input streams, bytes are just numbers, `System.in`

2 · Output streams

## Writing: push bytes out

Every output stream shares the same few moves, whatever the destination:

**write(one byte)**\
Send a single value

**write(many bytes)**\
Send a whole batch at once

**flush()**\
Push out anything waiting

**close()**\
Finish and release the pipe

Same methods for screen, file, or memory. That is the power of `OutputStream`.

2 · Live challenge

## The Alphabet Printer

Print the capital letters A to Z on the screen **without using `println`**. Use `System.out` purely as an output stream and send raw byte values.

1. Loop over the numbers 65 to 90 and write each as a byte.
2. Write a final newline byte (10) and flush.
3. Then write the word `"JAVA"` using the *many bytes at once* method (hint: strings can give you their bytes).

ABCDEFGHIJKLMNOPQRSTUVWXYZ JAVA

**Shows:** single vs batch writes, `flush()`, strings are bytes

3 · Input streams

## Reading: pull bytes in

**read()**\
Returns one byte (0-255) as a number

**The end signal**\
When nothing is left, `read()` returns **-1**

**read(many)**\
Fills a bucket (array) and tells you how many bytes arrived

Like drinking from a tap: keep going until it runs dry. The -1 is the sound of the dry tap.

3 · Live challenge

## The Chunk Reader

Data lives in memory as the text `"Hello, Java streams!"`. Wrap it in an input stream and read it in **chunks of 5 bytes**, printing each chunk. Then report how many bytes there were in total.

1. Turn the string into bytes and wrap them in a `ByteArrayInputStream`.
2. Read repeatedly into a 5-byte bucket until `read` returns -1.
3. Print only the bytes actually filled (the last chunk is shorter!).

Hello , Jav a str eams! Total: 20 bytes

**Shows:** read-into-array, the -1 rule, partial last chunk

4 · File streams

## Connect the pipe to a file

**FileInputStream**\
Reads bytes from a file

**FileOutputStream**\
Writes bytes to a file. Overwrites by default; append mode keeps old content.

**Things go wrong**\
Missing file, no permission. Java makes you handle it, and close the file when done.

Any file works, because bytes don't care if it's text, a photo, or a song.

4 · Live challenge

## The Copy Machine

Write a program that copies `photo.jpg` to `photo_copy.jpg`. The copy must open perfectly in an image viewer.

1. Open an input stream on the source and an output stream on the target.
2. Read a byte, write a byte, repeat until -1.
3. Always close both, even if something fails.
4. If `photo.jpg` doesn't exist, print "Source file not found" and exit politely.
5. Finish by printing both file sizes. They must match.

Copied 482113 bytes Original: 482113 | Copy: 482113

**Shows:** file streams, binary data, closing, error handling. **Bonus:** append a line to `copy-log.txt` using append mode.

5 · Filter streams

## Wrap a pipe to change what flows through

A filter stream wraps another stream and adds behaviour, like a coffee filter on a mug: same liquid, cleaned on the way through.

**Wrapping**\
Pass one stream into another's constructor

**Stacking**\
Wrap as many layers as you like

**Customising**\
Extend `FilterInputStream` and change `read()` to transform data

5 · Live challenge

## The Shouting Filter

Create your own filter stream, `ShoutingInputStream`, that wraps any input stream and turns every lowercase letter into uppercase as it is read. Everything else passes through unchanged.

1. Extend `FilterInputStream`.
2. Override the single-byte `read()`: get the byte from the wrapped stream, convert if it is a lowercase letter, keep -1 as -1.
3. Test it by wrapping a stream over `"quiet text, 123!"` and printing what comes out.

QUIET TEXT, 123!

**Shows:** wrapping, extending a filter, why -1 must be preserved. **Bonus:** a ROT13 secret-code output filter.

6 · Buffered streams

## Carry a bag, not one item per trip

Talking to a disk is slow. A buffered stream keeps a small holding area in memory and talks to the disk in big batches.

**BufferedInputStream**\
Reads a big chunk ahead, hands bytes out from memory

**BufferedOutputStream**\
Collects bytes, writes them out together

**Don't forget**\
`flush()` or `close()` sends the last bytes. Skip it and data can go missing.

It's a filter stream (idea 5), so you wrap your file stream with it.

6 · Live challenge

## The Speed Race

You have a large file, `big.bin` (50 MB or more). Copy it twice and find out how much faster buffering makes it.

1. Copy 1: plain file streams, one byte at a time.
2. Copy 2: the same loop, but wrap both streams in buffered streams.
3. Time each copy with `System.nanoTime()` and print milliseconds.
4. Print how many times faster the buffered copy was.

Plain: 41200 ms Buffered: 260 ms Buffered was \~158x faster

**Shows:** buffering, wrapping streams, why `close()` matters. Your numbers will differ; the gap is the point.

Wrap-up

## The whole picture

- A stream is a **one-way pipe of bytes**: input or output.
- **File streams** connect the pipe to a file.
- **Filter and buffered streams** wrap a pipe to add behaviour or speed.
- Always **close** what you open, and expect things to fail.

Questions?