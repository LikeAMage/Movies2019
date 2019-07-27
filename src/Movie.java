import java.util.ArrayList;
import java.util.List;

public class Movie {
    String title;
    String description;
    String release;
    String imdbRate;
    String imdbVote;
    String genre;
    int id;

    List<Actor> actors = new ArrayList<>();
    List<Director> directors = new ArrayList<>();

    Movie(int id, String title, String description, String release, String imdbRate, String imdbVote, String genre) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.release = release;
        this.imdbRate = imdbRate;
        this.imdbVote = imdbVote;
        this.genre = genre;
    }
}
