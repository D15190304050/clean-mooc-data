SELECT course_id, user_id, SUM(IFNULL(score, 0)) total_score
FROM
    (
        SELECT DISTINCT course_id, user_id, student_ans.problem_id problem_id, score
        FROM
            (
                SELECT DISTINCT course_id, problem_id
                FROM
                    (
                        SELECT DISTINCT course_id, resource_id
                        FROM course_resource
                        WHERE resource_id LIKE 'Ex_%'
                    ) course_exercises
                        LEFT JOIN problem p
                                  ON course_exercises.resource_id = p.exercise_id
            ) course_problem
                INNER JOIN
            (
                SELECT * FROM student_answer LIMIT 100000 -- Remove the limit clause to get full data.
            ) student_ans
            ON course_problem.problem_id = CAST(SUBSTR(student_ans.problem_id, 4) AS SIGNED)
    ) course_student_problem_score
GROUP BY course_id, user_id
ORDER BY course_id, total_score DESC;