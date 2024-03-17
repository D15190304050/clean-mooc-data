package stark.clean.mooc;

import com.fasterxml.jackson.databind.ObjectMapper;
import stark.clean.mooc.entities.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class GetStudentProblemRelation
{
    public static final String CHARSET = "utf-8";

    public static void main(String[] args) throws IOException
    {
//        getCourseExerciseRelation();
//        getCourseVideoRelation();
//        getStringExerciseRelation();
//        getDistinctCourseIds();


    }

    private static void getStringExerciseRelation() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();

        String userProblemFilePath = getUserProblemFilePath();
        FileInputStream fileInputStream = new FileInputStream(userProblemFilePath);

        Scanner input = new Scanner(fileInputStream, CHARSET);
        BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(Paths.get(getUserProblemRelationFilePath())));

        int i = 1;

        while (input.hasNext())
        {
            System.out.println("getCourseExerciseRelation, Processing line " + i);

            String StudentAnswerJsonText = input.nextLine();
            StudentAnswer courseInfo = mapper.readValue(StudentAnswerJsonText, StudentAnswer.class);

            StudentProblemRelation studentProblemRelation = new StudentProblemRelation();
            studentProblemRelation.setStudentId(courseInfo.getUserId());
            studentProblemRelation.setProblemId(courseInfo.getProblemId());

            String studentProblemRelationJsonText = mapper.writeValueAsString(studentProblemRelation);
            outputStream.write(studentProblemRelationJsonText.getBytes(StandardCharsets.UTF_8));

            outputStream.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));

            i++;
        }

        input.close();
        fileInputStream.close();

        outputStream.close();
    }

    private static void getCourseExerciseRelation() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();

        List<CourseExerciseRelation> courseExerciseRelations = new ArrayList<>();

        String courseInfoFilePath = getCourseInfoFilePath();
        FileInputStream fileInputStream = new FileInputStream(courseInfoFilePath);

        Scanner input = new Scanner(fileInputStream, CHARSET);

        int i = 1;

        while (input.hasNext())
        {
            System.out.println("getCourseExerciseRelation, Processing line " + i);

            String courseInfoJsonText = input.nextLine();
            CourseInfo courseInfo = mapper.readValue(courseInfoJsonText, CourseInfo.class);

            String courseId = courseInfo.getId();

            for (CourseResource courseResource : courseInfo.getResources())
            {
                String resourceId = courseResource.getResourceId();

                // If it is an exercise, then its id starts with "Ex_".
                if (resourceId.startsWith("Ex_"))
                {
                    CourseExerciseRelation courseExerciseRelation = new CourseExerciseRelation();
                    courseExerciseRelation.setCourseId(courseId);
                    courseExerciseRelation.setExerciseId(resourceId);
                    courseExerciseRelations.add(courseExerciseRelation);
                }
            }

            i++;
        }

        input.close();
        fileInputStream.close();

        String courseExerciseRelationFilePath = getCourseExerciseRelationFilePath();
        PrintWriter writer = new PrintWriter(courseExerciseRelationFilePath, CHARSET);

        writer.println(mapper.writeValueAsString(courseExerciseRelations));

        writer.close();
    }

    private static void getCourseVideoRelation() throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();

        List<CourseVideoRelation> courseExerciseRelations = new ArrayList<>();

        String courseInfoFilePath = getCourseInfoFilePath();
        FileInputStream fileInputStream = new FileInputStream(courseInfoFilePath);

        Scanner input = new Scanner(fileInputStream, CHARSET);

        int i = 1;

        while (input.hasNext())
        {
            System.out.println("getCourseExerciseRelation, Processing line " + i);

            String courseInfoJsonText = input.nextLine();
            CourseInfo courseInfo = mapper.readValue(courseInfoJsonText, CourseInfo.class);

            String courseId = courseInfo.getId();

            for (CourseResource courseResource : courseInfo.getResources())
            {
                String resourceId = courseResource.getResourceId();

                // If it is a video, then its id starts with "V_".
                if (resourceId.startsWith("V_"))
                {
                    CourseVideoRelation courseExerciseRelation = new CourseVideoRelation();
                    courseExerciseRelation.setCourseId(courseId);
                    courseExerciseRelation.setVideoId(resourceId);
                    courseExerciseRelations.add(courseExerciseRelation);
                }
            }

            i++;
        }

        input.close();
        fileInputStream.close();

        String courseExerciseRelationFilePath = getCourseVideoRelationFilePath();
        PrintWriter writer = new PrintWriter(courseExerciseRelationFilePath, CHARSET);

        writer.println(mapper.writeValueAsString(courseExerciseRelations));

        writer.close();
    }

    public static void getDistinctCourseIds() throws IOException
    {
        String courseExerciseRelationFilePath = getCourseExerciseRelationFilePath();
        ObjectMapper mapper = new ObjectMapper();

        CourseExerciseRelation[] courseExerciseRelations = mapper.readValue(new File(courseExerciseRelationFilePath), CourseExerciseRelation[].class);

        HashSet<String> courseIds = new HashSet<>();
        // Do your operation here.
        for (CourseExerciseRelation courseExerciseRelation : courseExerciseRelations)
            courseIds.add(courseExerciseRelation.getCourseId());

        String courseIdsFilePath = getCourseIdsFilePath();
        PrintWriter writer = new PrintWriter(new File(courseIdsFilePath));
        writer.println(mapper.writeValueAsString(courseIds));

        writer.close();
    }

    private static String getCourseInfoFilePath()
    {
        return Commons.getDataDirectoryPath() + "/course.json";
    }

    private static String getCourseExerciseRelationFilePath()
    {
        return Commons.getOutputDirectoryPath() + "/course-exercise.json";
    }

    private static String getCourseVideoRelationFilePath()
    {
        return Commons.getOutputDirectoryPath() + "/course-video.json";
    }

    private static String getUserProblemFilePath()
    {
        return Commons.getDataDirectoryPath() + "/user-problem.json";
    }

    private static String getUserProblemRelationFilePath()
    {
        return Commons.getOutputDirectoryPath() + "/user-problem.json";
    }

    private static String getCourseIdsFilePath()
    {
        return Commons.getOutputDirectoryPath() + "/courseIds.json";
    }
}
