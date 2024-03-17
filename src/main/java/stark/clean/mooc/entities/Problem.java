package stark.clean.mooc.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class Problem
{
    @JsonProperty("problem_id")
    private long problemId;
    private String title;
    private String content;
    private Map<String, String> option;
    private String answer;
    private BigDecimal score;
    private int type;

    @JsonProperty("typetext")
    private String typeText;
    private String location;

    @JsonProperty("context_id")
    private List<Long> contextIds;

    @JsonProperty("exercise_id")
    private String exerciseId;

    private String language;
}
