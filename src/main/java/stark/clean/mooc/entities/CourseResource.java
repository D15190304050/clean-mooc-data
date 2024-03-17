package stark.clean.mooc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CourseResource
{
    private List<String> titles;

    @JsonProperty("resource_id")
    private String resourceId;
    private String chapter;
}
