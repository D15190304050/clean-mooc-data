package stark.clean.mooc;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadJsonByStream
{
    public static void main(String[] args) throws IOException
    {
        // Change the file path.
//        String filePath = "D:\\DinoStark\\Projects\\IDEA\\clean-mooc-data\\data\\problem.json";
//        String filePath = "D:\\DinoStark\\Projects\\IDEA\\clean-mooc-data\\data\\comment.json";
        String filePath = "D:\\DinoStark\\Projects\\IDEA\\clean-mooc-data\\data\\user-video.json";

        FileInputStream fileInputStream = new FileInputStream(filePath);

        // 10 * 1024
        // Read 10 KB text.
        StringBuilder contentCache = new StringBuilder();
        byte[] buffer = new byte[1024];
        while (contentCache.length() < 10240)
        {
            fileInputStream.read(buffer);
            contentCache.append(new String(buffer));
        }

        System.out.println(contentCache);

        fileInputStream.close();
    }
}
