package stark.clean.mooc.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CourseInfo
{
    private String id;
    private String name;
    private String prerequisites;
    private String about;

    @JsonProperty("field")
    private List<String> fields;

    @JsonProperty("resource")
    private List<CourseResource> resources;
}
