package stark.clean.mooc;

import stark.clean.mooc.mysql.ReadCourseDataToDb;
import stark.clean.mooc.mysql.ReadExerciseProblemToDb;
import stark.clean.mooc.mysql.ReadProblemToDb;
import stark.clean.mooc.mysql.ReadStudentProblemToDb;

import java.io.FileNotFoundException;

public class Workflow
{
    public static void main(String[] args) throws Exception
    {
        // Step 1, execute the SQL in the /resources/db_sql/init.sql
        // Dependencies: MySQL installed.

        // Step 2, read course.json and then write course info + course resource info into database.
        // Required file: course.json.
        // Dependency of previous steps: step 1.
//        ReadCourseDataToDb.run();

        // Step 3, write student problem info into database.
        // Required file: user-problem.json (Is generated: No).
        // Dependency of previous steps: step 1.
//        ReadStudentProblemToDb.run();

        // Step 4, write exercise + problem relation to database.
        // Required file: exercise-problem.txt (Is generated: No).
        // Dependency of previous steps: step 1.
//        ReadExerciseProblemToDb.run();

        // Step 5, write problem info into database.
        // Required file: problem.json (Is generated: No).
        // Dependencies:
        //     - Step 1.
        //     - Remove all invalid line terminator in the required file. (Can be finished by VS code.)
        ReadProblemToDb.run();
    }
}
