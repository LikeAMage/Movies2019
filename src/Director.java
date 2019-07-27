import java.util.ArrayList;
import java.util.List;

public class Director extends Human {
    List<Movie> movies = new ArrayList<>();

    Director(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
