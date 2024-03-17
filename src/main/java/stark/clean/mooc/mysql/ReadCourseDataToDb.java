package stark.clean.mooc.mysql;

import com.fasterxml.jackson.databind.ObjectMapper;
import stark.clean.mooc.Commons;
import stark.clean.mooc.entities.CourseInfo;
import stark.clean.mooc.entities.CourseResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadCourseDataToDb
{
    private ReadCourseDataToDb()
    {}

    public static void run() throws IOException, SQLException
    {
        ObjectMapper mapper = new ObjectMapper();

        String courseInfoFilePath = getCourseInfoFilePath();
        FileInputStream fileInputStream = new FileInputStream(courseInfoFilePath);

        Scanner input = new Scanner(fileInputStream, Commons.CHARSET);

        List<CourseInfo> courseInfos = new ArrayList<>();

        int i = 1;
        while (input.hasNext())
        {
            System.out.println("ReadCourseDataToDb, Processing line " + i);

            String courseInfoJsonText = input.nextLine();
            CourseInfo courseInfo = mapper.readValue(courseInfoJsonText, CourseInfo.class);
            courseInfos.add(courseInfo);

            i++;
        }

        input.close();
        fileInputStream.close();

        i = 1;
        for (CourseInfo courseInfo : courseInfos)
        {
            System.out.println("Inserting course: " + i);

            String courseId = courseInfo.getId();

            PreparedStatement cmdInsertCourseInfo = ConnectionManager.prepareStatement("INSERT INTO `course_info` (`course_id`, `name`) VALUES (?, ?)");
            cmdInsertCourseInfo.setString(1, courseId);
            cmdInsertCourseInfo.setString(2, courseInfo.getName());
            cmdInsertCourseInfo.executeUpdate();
            cmdInsertCourseInfo.close();

            for (CourseResource courseResource : courseInfo.getResources())
            {
                PreparedStatement cmdInsertCourseResource = ConnectionManager.prepareStatement("INSERT INTO `course_resource` (`resource_id`, `chapter`, `course_id`) VALUES (?, ?, ?)");
                cmdInsertCourseResource.setString(1, courseResource.getResourceId());
                cmdInsertCourseResource.setString(2, courseResource.getChapter());
                cmdInsertCourseResource.setString(3, courseId);
                cmdInsertCourseResource.executeUpdate();
                cmdInsertCourseResource.close();
            }

            if (i % 10000 == 0)
                System.gc();

            i++;
        }
    }

    private static String getCourseInfoFilePath()
    {
        return Commons.getDataDirectoryPath() + "/course.json";
    }
}
