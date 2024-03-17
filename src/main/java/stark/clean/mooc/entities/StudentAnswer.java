package stark.clean.mooc.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudentAnswer
{
    @JsonProperty("log_id")
    private String logId;

    @JsonProperty("problem_id")
    private String problemId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("is_correct")
    private int isCorrect;

    private int attempts;

    private BigDecimal score;

    @JsonProperty("submit_time")
    private String submitTime;
}
