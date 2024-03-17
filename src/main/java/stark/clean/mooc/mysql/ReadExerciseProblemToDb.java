package stark.clean.mooc.mysql;

import stark.clean.mooc.Commons;
import stark.clean.mooc.entities.ExerciseProblemRelation;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadExerciseProblemToDb
{
    public static void run() throws IOException, SQLException
    {
        String cmdInsertPrefix = "INSERT INTO `exercise_problem_relation`\n" +
            "(`exercise_id`, `problem_id`)\n" +
            "VALUES\n";

        String exerciseProblemFilePath = getExerciseProblemFilePath();
        FileInputStream fileInputStream = new FileInputStream(exerciseProblemFilePath);

        Scanner input = new Scanner(fileInputStream, Commons.CHARSET);

        int i = 0;
        int parameterCount = 2;
        int step = 500000;
        String placeholder = "(?,?),";

        System.out.println("Start time: " + Commons.getCurrentTime());
        while (input.hasNext())
        {
            List<ExerciseProblemRelation> exerciseProblemRelationsBuffer = new ArrayList<>();
            while (input.hasNext() && exerciseProblemRelationsBuffer.size() < step)
            {
                String exerciseProblemRelationText = input.nextLine();
                String[] exerciseProblemRelationData = exerciseProblemRelationText.split("\t");
                ExerciseProblemRelation exerciseProblemRelation = new ExerciseProblemRelation();
                exerciseProblemRelation.setExerciseId(exerciseProblemRelationData[0]);
                exerciseProblemRelation.setProblemId(exerciseProblemRelationData[1]);
                exerciseProblemRelationsBuffer.add(exerciseProblemRelation);

                i++;
            }

            StringBuilder cmdInsertBuilder = new StringBuilder(cmdInsertPrefix);
            for (int j = 0; j < exerciseProblemRelationsBuffer.size(); j++)
                cmdInsertBuilder.append(placeholder);

            // Remove the ending ",".
            String cmdInsert = cmdInsertBuilder.substring(0, cmdInsertBuilder.length() - 1);

            PreparedStatement cmdInsertExerciseProblemRelation = ConnectionManager.prepareStatement(cmdInsert);

            for (int rowInCmdInsert = 0; rowInCmdInsert < exerciseProblemRelationsBuffer.size(); rowInCmdInsert++)
            {
                ExerciseProblemRelation exerciseProblemRelation = exerciseProblemRelationsBuffer.get(rowInCmdInsert);

                cmdInsertExerciseProblemRelation.setString(rowInCmdInsert * parameterCount + 1, exerciseProblemRelation.getExerciseId());
                cmdInsertExerciseProblemRelation.setString(rowInCmdInsert * parameterCount + 2, exerciseProblemRelation.getProblemId());
            }

            cmdInsertExerciseProblemRelation.executeUpdate();
            cmdInsertExerciseProblemRelation.close();

            System.out.println(Commons.getCurrentTime() + ": inserted line count: " + i);

            System.gc();
        }

        input.close();
        fileInputStream.close();
    }

    private static String getExerciseProblemFilePath()
    {
        return Commons.getDataDirectoryPath() + "/exercise-problem.txt";
    }
}
