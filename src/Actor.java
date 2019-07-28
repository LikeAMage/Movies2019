import java.util.*;

public class Actor extends Human {
    List<Movie> movies = new ArrayList<>();

    Actor(int id, String name) {
        this.id = id;
        this.name = name;
    }
}