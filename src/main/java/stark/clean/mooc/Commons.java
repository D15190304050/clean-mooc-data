package stark.clean.mooc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Commons
{
    public static final String DATA_DIRECTORY = "D:\\DinoStark\\Projects\\IDEA\\clean-mooc-data\\data";
    public static final String CHARSET = "utf-8";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private Commons()
    {}

    public static String getDataDirectoryPath()
    {
        return DATA_DIRECTORY;
    }

    public static String getOutputDirectoryPath()
    {
        return getDataDirectoryPath();
    }

    public static String getCurrentTime()
    {
        return DATE_FORMAT.format(new Date());
    }
}
