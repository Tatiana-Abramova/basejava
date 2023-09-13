package webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dir = new File("./src");
        listFiles("", dir);
    }

    public static void listFiles(String indent, File directory) {
        if (directory.isDirectory()) {
            indent += "--";
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                System.out.print(indent);
                if (file.isFile()) {
                    System.out.println(file.getName());
                } else {
                    System.out.println("dir: " + file.getName());
                    listFiles(indent, file);
                }
            }
        }
    }
}
