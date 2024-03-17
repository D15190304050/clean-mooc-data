package stark.clean.mooc.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ConvertObjectToJson
{
    public static void main(String[] args) throws JsonProcessingException
    {
        Point p1 = new Point(1, 2);
        ObjectMapper mapper = new ObjectMapper();

        String s = mapper.writeValueAsString(p1);
        System.out.println(s);

        List<Point> points = new ArrayList<>();
        points.add(p1);
        points.add(new Point(3.14, 12.3));
        s = mapper.writeValueAsString(points);
        System.out.println(s);

        System.out.println("-----------------------");
        Point[] points2 = mapper.readValue("[{\"x\":22.0,\"y\":33.0},{\"x\":113.14,\"y\":3312.3}]", Point[].class);
        for (Point p : points2)
        {
            System.out.println(p.getX() + ", " + p.getY());
        }
    }
}
