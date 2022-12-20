import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.*;
public class Main {
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) {

        // Первая задача
        List<String> nameSubDir = Arrays.asList("src", "res", "savegames", "temp");
        List<String> nameSupDir1 = Arrays.asList("main", "test");
        List<String> nameSupDir2 = Arrays.asList("drawables", "vectors", "icons");
        List<String> nameFile1 = Arrays.asList("Main.java", "Utils.java");
        List<String> nameFile2 = Arrays.asList("temp.txt");

        makeDir(nameSubDir, nameSupDir1, nameSupDir2); // Создаем каталоги
        makeFile(nameFile1, nameFile2); // Создаем файлы

        // Вторая задача
        GameProgress gp1 = new GameProgress(100,100,100,100);
        GameProgress gp2 = new GameProgress(55,55,55,55);
        GameProgress gp3 = new GameProgress(4,4,4,4);
        String link1 = "C://Workspace_Java//Temp//Games//savegames//save1.dat";
        String link2 = "C://Workspace_Java//Temp//Games//savegames//save2.dat";
        String link3 = "C://Workspace_Java//Temp//Games//savegames//save3.dat";

        saveGame(link1, gp1); // Сохраняем первую игру
        saveGame(link2, gp2); // Сохраняем вторую игру
        saveGame(link3, gp3); // Сохраняем третью игру

        String linkZip = "C://Workspace_Java//Temp//Games//savegames//zip.zip";
        List<String> list = Arrays.asList(link1, link2, link3);

        zipFiles(linkZip, list); // Добавляем в архив сохранения

        delFile(); // Удаляем файлы

        log(); // Записываем логи
    }
    // Создаем каталоги
    static void makeDir(List<String> list1, List<String> list2, List<String> list3) {

        for (String s : list1) {
            File dir = new File("C://Workspace_Java//Temp//Games//" + s);
            if (dir.mkdir()) {
                sb.append("Direct ").append(s).append(" create\n");
            } else {
                sb.append("Direct ").append(s).append(" don't create\n");
            }
        }
        for (String s : list2) {
            File dir = new File("C://Workspace_Java//Temp//Games//src//" + s);
            if (dir.mkdir()) {
                sb.append("Direct ").append(s).append(" create\n");
            } else {
                sb.append("Direct ").append(s).append(" don't create\n");
            }
        }
        for (String s : list3) {
            File dir = new File("C://Workspace_Java//Temp//Games//res//" + s);
            if (dir.mkdir()) {
                sb.append("Direct ").append(s).append(" create\n");
            } else {
                sb.append("Direct ").append(s).append(" don't create\n");
            }
        }
    }
    // Создаем файлы
    static void makeFile(List<String> list1, List<String> list2) {

        try
        {
            for (String s : list1) {
                File file = new File("C://Workspace_Java//Temp//Games//src//main//", s);
                if (file.createNewFile()) {
                    sb.append("File ").append(s).append(" create\n");
                } else {
                    sb.append("File ").append(s).append(" don't create\n");
                }
            }
            for (String s : list2) {
                File file = new File("C://Workspace_Java//Temp//Games//temp//", s);
                if (file.createNewFile()) {
                    sb.append("File ").append(s).append(" create\n");
                } else {
                    sb.append("File ").append(s).append(" don't create\n");
                }
            }
        }
        catch (IOException ex) {System.out.println(ex.getMessage());}
    }
    // Записываем сохранения
    static void saveGame(String link, GameProgress gp) {

        File file = new File(link);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(link)))
        {
            file.createNewFile();
            byte[] buffer = gp.toString().getBytes();
            oos.writeObject(buffer);
            oos.close();
        }
        catch(IOException ex){System.out.println(ex.getMessage());}
    }
    // Архивируем файлы сохранения и удаляем исходники
    static void zipFiles(String linkZip, List<String> list) {

            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(linkZip)))
            {
                for(String link : list) {
                    FileInputStream fis = new FileInputStream(link);
                    File file = new File(link);
                    zout.putNextEntry(new ZipEntry(file.getName()));
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer, 0, buffer.length);
                    zout.write(buffer);
                    sb.append("Zip ").append(file.getName()).append(" create\n");
                    zout.closeEntry();
                    fis.close();
                }

            }
            catch (Exception ex) {System.out.println(ex.getMessage());}
    }
    // Удаляем файлы
    static void delFile() {
        File dir = new File("C://Workspace_Java//Temp//Games//savegames");
        List<File> files= Arrays.asList(dir.listFiles());
        for (File file : files) {
            if(!file.getName().equals("zip.zip")) {
                file.delete();
                sb.append("File ").append(file.getName()).append(" has been deleted\n");
            }
        }
    }
    // Запись логов
    static void log() {
        try (FileWriter writer = new FileWriter("C://Workspace_Java//Temp//Games//temp//temp.txt", true))
        {
            writer.write(sb.toString());
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
