import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.zip.*;
public class Main {

    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) {

        // For first task
        List<String> nameSubDir = Arrays.asList("src", "res", "savegames", "temp");
        List<String> nameSupDir1 = Arrays.asList("main", "test");
        List<String> nameSupDir2 = Arrays.asList("drawables", "vectors", "icons");
        List<String> nameFile1 = Arrays.asList("Main.java", "Utils.java");
        List<String> nameFile2 = Arrays.asList("temp.txt");

        makeDir(nameSubDir, nameSupDir1, nameSupDir2);
        makeFile(nameFile1, nameFile2);

        // For second task
        GameProgress gp1 = new GameProgress(100,100,100,100);
        GameProgress gp2 = new GameProgress(55,55,55,55);
        GameProgress gp3 = new GameProgress(4,4,4,4);
        String link1 = "C://Workspace_Java//Temp//Games//savegames//save1.dat";
        String link2 = "C://Workspace_Java//Temp//Games//savegames//save2.dat";
        String link3 = "C://Workspace_Java//Temp//Games//savegames//save3.dat";

        saveGame(link1, gp1);
        saveGame(link2, gp2);
        saveGame(link3, gp3);

        String linkZip = "C://Workspace_Java//Temp//Games//savegames//zip.zip";
        List<String> list = Arrays.asList(link1, link2, link3);

        zipFiles(linkZip, list);

        try (FileWriter writer = new FileWriter("C://Workspace_Java//Temp//Games//temp//temp.txt", true))
        {
            writer.write(sb.toString());
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

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

    // Создаем файлы сохранений
    static void saveGame(String link, GameProgress gp) {
        File file = new File(link);
        try(ObjectOutputStream fos = new ObjectOutputStream(Files.newOutputStream(Paths.get(link))))
        {
            file.createNewFile();
            byte[] buffer = gp.toString().getBytes();
            fos.write(buffer, 0, buffer.length);
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }

    // Архивируем файлы сохранения и удаляем исходники
    static void zipFiles(String linkZip, List<String> list) {

        for(String link : list) {
            try (ZipOutputStream zout = new ZipOutputStream(Files.newOutputStream(Paths.get(linkZip)));
                FileInputStream fis = new FileInputStream(link);)
            {
                File file = new File(link);
                ZipEntry entry1 = new ZipEntry(file.getName());
                zout.putNextEntry(entry1);

                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);

                zout.write(buffer);
                sb.append("Zip ").append(file.getName()).append(" create\n");
                zout.closeEntry();
                fis.close();

                boolean deleted = file.delete();
                if(deleted)
                    sb.append("File ").append(file.getName()).append(" has been deleted\n");

            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}