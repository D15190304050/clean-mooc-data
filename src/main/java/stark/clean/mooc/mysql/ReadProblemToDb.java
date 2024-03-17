package stark.clean.mooc.mysql;

import com.fasterxml.jackson.databind.ObjectMapper;
import stark.clean.mooc.Commons;
import stark.clean.mooc.entities.Problem;
import stark.clean.mooc.entities.StudentAnswer;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadProblemToDb
{
    private ReadProblemToDb()
    {
    }

    public static void run() throws IOException, SQLException
    {
        String cmdInsertPrefix = "INSERT INTO `problem`\n" +
            "(`exercise_id`, `problem_id`, `title`, `content`, `answer`, `score`, `type`, `type_text`, `location`, `language`)\n" +
            "VALUES";

        ObjectMapper mapper = new ObjectMapper();

        String problemFilePath = getProblemFilePath();
        FileInputStream fileInputStream = new FileInputStream(problemFilePath);

        Scanner input = new Scanner(fileInputStream, Commons.CHARSET);

        int i = 0;
        int parameterCount = 10;
        int step = 250000;
//        int step = 10;
        String placeholder = "(?,?,?,?,?,?,?,?,?,?),";

        System.out.println("Start time: " + Commons.getCurrentTime());
        while (input.hasNext())
        {
            List<Problem> problemBuffer = new ArrayList<>();
            while (input.hasNext() && problemBuffer.size() < step)
            {
                String problemText = input.nextLine();
                Problem problem = mapper.readValue(problemText, Problem.class);
                problemBuffer.add(problem);

                i++;
            }

            StringBuilder cmdInsertBuilder = new StringBuilder(cmdInsertPrefix);
            for (int j = 0; j < problemBuffer.size(); j++)
                cmdInsertBuilder.append(placeholder);

            // Remove the ending ",".
            String cmdInsertSql = cmdInsertBuilder.substring(0, cmdInsertBuilder.length() - 1);

            PreparedStatement cmdInsert = ConnectionManager.prepareStatement(cmdInsertSql);

            for (int rowInCmdInsert = 0; rowInCmdInsert < problemBuffer.size(); rowInCmdInsert++)
            {
                Problem problem = problemBuffer.get(rowInCmdInsert);

                cmdInsert.setString(rowInCmdInsert * parameterCount + 1, problem.getExerciseId());
                cmdInsert.setLong(rowInCmdInsert * parameterCount + 2, problem.getProblemId());
                cmdInsert.setString(rowInCmdInsert * parameterCount + 3, problem.getTitle());
                cmdInsert.setString(rowInCmdInsert * parameterCount + 4, problem.getContent());
                cmdInsert.setString(rowInCmdInsert * parameterCount + 5, problem.getAnswer());
                cmdInsert.setBigDecimal(rowInCmdInsert * parameterCount + 6, problem.getScore());
                cmdInsert.setInt(rowInCmdInsert * parameterCount + 7, problem.getType());
                cmdInsert.setString(rowInCmdInsert * parameterCount + 8, problem.getTypeText());
                cmdInsert.setString(rowInCmdInsert * parameterCount + 9, problem.getLocation());
                cmdInsert.setString(rowInCmdInsert * parameterCount + 10, problem.getLanguage());
            }

            cmdInsert.executeUpdate();
            cmdInsert.close();

            System.out.println(Commons.getCurrentTime() + ": inserted line count: " + i);

            System.gc();
//            break;
        }

        input.close();
        fileInputStream.close();
    }

    private static String getProblemFilePath()
    {
        return Commons.getDataDirectoryPath() + "/problem.json";
    }
}
