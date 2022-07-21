package de.michm.vin.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class NbBufferedReader {
    private final BlockingQueue<String> lines = new LinkedBlockingQueue<>();
    private volatile boolean closed = false;
    private volatile boolean ready = false;
    private Thread backgroundThread;

    /**
     * Creates a new NbBufferedReader object.
     *
     * @param in InputStream
     */
    public NbBufferedReader(final InputStream in) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        backgroundThread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    String line = reader.readLine();
                    ready = reader.ready();

                    if (line == null)
                        break;

                    lines.add(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                closed = true;
            }
        });

        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }

    /**
     * Retrieves and removes the first line of
     * the internal queue. Returns null if the
     * queue is empty or the reader is closed.
     *
     * @return String first line of the queue
     * @throws IOException
     */
    public String readLine() throws IOException {
        try {
            return closed && lines.isEmpty() ? null : lines.poll(100L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new IOException("The BackgroundReaderThread was interrupted!", e);
        }
    }

    /**
     * Closes the BufferedReader
     *
     */
    public void close() {
        if (backgroundThread != null) {
            backgroundThread.interrupt();
            backgroundThread = null;
        }
    }

    /**
     * Returns ready state
     *
     * @return boolean ready
     */
    public boolean isReady() {
        return ready;
    }
}
