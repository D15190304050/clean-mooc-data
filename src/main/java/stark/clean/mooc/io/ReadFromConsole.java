package stark.clean.mooc.io;

import java.util.Scanner;

public class ReadFromConsole
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        String s = input.nextLine();
        System.out.println(s);

        s = input.nextLine();
        System.out.println(s);

        input.close();
    }
}
