package stark.clean.mooc.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile
{
    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner input = new Scanner(new File("filePath"));

        while (input.hasNext())
        {
            // Process line by line.
            System.out.println(input.nextLine());
        }

        input.close();
    }
}
