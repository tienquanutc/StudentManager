package io.txt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface TxtMapper<T> {

    String toText(T o);

    T toModel(String text);

    default void trimParam(String[] param) {
        for (int i = 0; i < param.length; ++i) {
            param[i] = param[i].trim();
        }
    }

    default List<T> read(String path) throws FileNotFoundException {
        File file = new File(path);
        List<T> result = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            result.add(toModel(line));
        }
        scanner.close();
        return result;
    }

    default void saveToFile(List<T> models, String path) throws IOException {
        FileWriter writer = new FileWriter(path, false);
        for (T o : models) {
            writer.append(toText(o));
            writer.append("\n");
        }
        writer.flush();
        writer.close();
    }
}
