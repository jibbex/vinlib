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

    public String readLine() throws IOException {
        try {
            return closed && lines.isEmpty() ? null : lines.poll(500L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new IOException("The BackgroundReaderThread was interrupted!", e);
        }
    }

    public void close() {
        if (backgroundThread != null) {
            backgroundThread.interrupt();
            backgroundThread = null;
        }
    }

    public boolean isReady() {
        return ready;
    }
}
