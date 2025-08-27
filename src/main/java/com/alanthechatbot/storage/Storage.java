package com.alanthechatbot.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class to create, retrieve and write to the storage file.
 */
public class Storage {
    private static final Path filePath = Paths.get(System.getProperty("user.home")
            , "AlanTheChatBot", "data.txt");
    ;

    /**
     * Creates the file and parent directories of the file if it does not exist.
     * <P> To be used before any other method of the Storage class.
     * @throws IOException if there is an error creating the file or directories
     */
    public static void init() throws IOException {
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }
    }

    /**
     * Stores the given string into the file referenced by filePath.
     * @param line the text to be added to storage
     */
    public static void writeToFile(String line) {
        try {
            FileWriter fw = new FileWriter(filePath.toString(), true);
            fw.write(line);
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the file that filePath refers to.
     * @return the file that filePath refers to
     */
    public static File getFile() {
        return new File(filePath.toString());
    }

}
