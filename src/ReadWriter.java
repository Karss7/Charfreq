/**
 * Created by Karss on 24.10.2016.
 */
import java.io.*;
import java.util.Scanner;

public class ReadWriter {
    public static String Read(String path){ //просто чтение файла
        String str="";

        try(Scanner scan = new Scanner(new File(path))) {
            while(scan.hasNextLine())
                str+=scan.nextLine()+"\n";
        }
        catch(FileNotFoundException e){ System.err.println("Read ||File not found.");}

        return str;
    }

    public static void Write(String path, String str){ //просто запись строки в файл
        try(FileWriter wr = new FileWriter(path, false)){
            wr.write(str);
        }
        catch(Exception ex) {
            System.err.println("Write ||Error.");
        }
    }

}