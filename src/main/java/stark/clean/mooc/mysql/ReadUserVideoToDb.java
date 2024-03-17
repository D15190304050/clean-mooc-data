package stark.clean.mooc.mysql;

import com.fasterxml.jackson.databind.ObjectMapper;
import stark.clean.mooc.Commons;
import stark.clean.mooc.entities.StudentAnswer;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Wait for implementation.
public class ReadUserVideoToDb
{
    private ReadUserVideoToDb()
    {
    }

    public static void run() throws IOException, SQLException
    {
        String cmdInsertPrefix = "INSERT INTO `student_answer`\n" +
            "(`user_id`, `log_id`, `problem_id`, `is_correct`, `attempts`, `score`, `submit_time`)\n" +
            "VALUES\n";

        ObjectMapper mapper = new ObjectMapper();

        String userProblemFilePath = getUserProblemFilePath();
        FileInputStream fileInputStream = new FileInputStream(userProblemFilePath);

        Scanner input = new Scanner(fileInputStream, Commons.CHARSET);

        int i = 0;
        int parameterCount = 7;
        int step = 500000;
        String placeholder = "(?,?,?,?,?,?,?),";

        System.out.println("Start time: " + Commons.getCurrentTime());
        while (input.hasNext())
        {
            List<StudentAnswer> studentAnswersBuffer = new ArrayList<>();
            while (input.hasNext() && studentAnswersBuffer.size() < step)
            {
                String studentAnswerJsonText = input.nextLine();
                StudentAnswer studentAnswer = mapper.readValue(studentAnswerJsonText, StudentAnswer.class);
                studentAnswersBuffer.add(studentAnswer);

                i++;
            }

            StringBuilder cmdInsertBuilder = new StringBuilder(cmdInsertPrefix);
            for (int j = 0; j < studentAnswersBuffer.size(); j++)
                cmdInsertBuilder.append(placeholder);

            // Remove the ending ",".
            String cmdInsert = cmdInsertBuilder.substring(0, cmdInsertBuilder.length() - 1);

            PreparedStatement cmdInsertStudentAnswer = ConnectionManager.prepareStatement(cmdInsert);

            for (int rowInCmdInsert = 0; rowInCmdInsert < studentAnswersBuffer.size(); rowInCmdInsert++)
            {
                StudentAnswer studentAnswer = studentAnswersBuffer.get(rowInCmdInsert);

                cmdInsertStudentAnswer.setString(rowInCmdInsert * parameterCount + 1, studentAnswer.getUserId());
                cmdInsertStudentAnswer.setString(rowInCmdInsert * parameterCount + 2, studentAnswer.getLogId());
                cmdInsertStudentAnswer.setString(rowInCmdInsert * parameterCount + 3, studentAnswer.getProblemId());
                cmdInsertStudentAnswer.setInt(rowInCmdInsert * parameterCount + 4, studentAnswer.getIsCorrect());
                cmdInsertStudentAnswer.setInt(rowInCmdInsert * parameterCount + 5, studentAnswer.getAttempts());
                cmdInsertStudentAnswer.setBigDecimal(rowInCmdInsert * parameterCount + 6, studentAnswer.getScore());
                cmdInsertStudentAnswer.setString(rowInCmdInsert * parameterCount + 7, studentAnswer.getSubmitTime());
            }

            cmdInsertStudentAnswer.executeUpdate();
            cmdInsertStudentAnswer.close();

            System.out.println(Commons.getCurrentTime() + ": inserted line count: " + i);

            System.gc();
//            break;
        }

        input.close();
        fileInputStream.close();
    }

    private static String getUserProblemFilePath()
    {
        return Commons.getDataDirectoryPath() + "/user-problem.json";
    }
}
