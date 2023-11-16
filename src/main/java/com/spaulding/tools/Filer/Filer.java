package com.spaulding.tools.Filer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Filer {
    public static List<String> createFile(String fileName, List<String> toWrite) {
        try {
            File file = new File(fileName);
            if (file.createNewFile()) {
                toWrite = write(fileName, toWrite);
                if (toWrite != null && !toWrite.isEmpty()) {
                    if (toWrite.get(0).startsWith("Error:") && !file.delete()) {
                        toWrite.add("Error: There was an issue trying to delete the file, \"" + fileName + "\", after a failure to write!");
                    }
                }

                return toWrite;
            } else {
                return read(fileName);
            }
        } catch (IOException e) {
            toWrite = new ArrayList<>();
            toWrite.add("Error: There was an issue trying to load the file \"" + fileName + "\"");
            return toWrite;
        }
    }

    public static List<String> write(String fileName, List<String> toWrite) {
        if (toWrite != null && !toWrite.isEmpty()) {
            try {
                FileWriter writer = new FileWriter(fileName);
                for (String write : toWrite) {
                    writer.append(write);
                    writer.append(System.lineSeparator());
                }
                writer.close();
                return toWrite;
            }
            catch (IOException e) {
                toWrite = new ArrayList<>();
                toWrite.add("Error: There was an issue trying to write to file \"" + fileName + "\"");
                return toWrite;
            }
        }

        return new ArrayList<>();
    }

    public static List<String> read(String fileName) {
        List<String> toRead = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                toRead.add(reader.nextLine());
            }
            reader.close();
        }
        catch (IOException e) {
            toRead.add("Error: There was an issue trying to read the file \"" + fileName + "\"");
        }

        return toRead;
    }
}
