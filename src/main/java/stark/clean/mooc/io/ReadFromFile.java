package stark.clean.mooc.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFromFile
{
    // Replace to your own text file path.
    public static final String FILE_PATH = "D:\\DinoStark\\Temp\\test data.txt";

    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner input = new Scanner(new File(FILE_PATH));

        int i = 1;
        while (input.hasNext())
        {
            String s = input.nextLine();
            System.out.println("Current line number " + i + ": " + s);
            i++;
        }

        input.close();
    }
}
