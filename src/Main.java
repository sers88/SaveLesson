import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static final String PATH = "E:\\Games\\savegames\\";
    private static final String FILE_NAME_1 = "save1.dat";
    private static final String FILE_NAME_2 = "save2.dat";
    private static final String FILE_NAME_3 = "save3.dat";

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(100, 1, 1, 10.0);
        GameProgress gameProgress2 = new GameProgress(90, 2, 2, 20.0);
        GameProgress gameProgress3 = new GameProgress(80, 3, 3, 30.0);

        List<String> files = new ArrayList<>();
        files.add(PATH + FILE_NAME_1);
        files.add(PATH + FILE_NAME_2);
        files.add(PATH + FILE_NAME_3);
        saveGame(gameProgress1, files.get(0));
        saveGame(gameProgress2, files.get(1));
        saveGame(gameProgress3, files.get(2));

        zipFiles(files, PATH + "save.zip");
        deleteFiles(files);

    }

    public static void saveGame(GameProgress gameProgress, String path) {

        try (FileOutputStream fos = new FileOutputStream(path)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameProgress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(List<String> files, String zipName) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipName))) {
            for (String file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    zout.putNextEntry(new ZipEntry(file));
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (FileNotFoundException e) {
                    System.out.println("File not found: " + e.getMessage());
                } catch (IOException e) {
                    System.out.println("IO error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteFiles(List<String> files) {
        for (String file : files) {
            File f = new File(file);
            f.delete();
        }
    }
}