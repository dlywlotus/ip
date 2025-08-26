package com.alanthechatbot.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Storage {
    private static final Path filePath = Paths.get(System.getProperty("user.home")
            , "AlanTheChatBot", "data.txt");
    ;

    public static void init() throws IOException {
        if (!Files.exists(filePath)) {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        }
    }

    public static void writeToFile(String line) {
        try {
            FileWriter fw = new FileWriter(filePath.toString(), true);
            fw.write(line);
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static File getFile() {
        return new File(filePath.toString());
    }

}
