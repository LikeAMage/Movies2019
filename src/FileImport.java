import java.io.*;
import java.util.*;

public class FileImport {
    static Map<Integer, Actor> actors = new HashMap<>();
    static Map<Integer, Movie> movies = new HashMap<>();
    static Map<Integer, Director> directors = new HashMap<>();

    static void importFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists() && file.isDirectory()) {
            System.out.println("Datei existiert nicht");
            System.exit(0);
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String row;
            int i = 0;
            String[] result;
            while ((row = br.readLine()) != null) {

                if (row.startsWith("New_Entity:")) {
                    i++;
                    row = br.readLine();
                }

                result = row.split("\",\"");
                result[0] = result[0].replaceFirst("\"", "");
                int id = Integer.parseInt(result[0]);

                switch (i) {
                    case 1:
                        result[1] = result[1].replace("\"", "");
                        if (result[1].startsWith(" ")) {
                            result[1] = result[1].replaceFirst(" ", "");
                        }
                        actors.put(id, new Actor(id, result[1]));
                        break;
                    case 2:
                        result[6] = result[6].replace("\"", "");
                        movies.put(id, new Movie(id, result[1], result[2], result[3], result[4], result[5], result[6]));
                        break;
                    case 3:
                        result[1] = result[1].replace("\"", "");
                        directors.put(id, new Director(id, result[1]));
                        break;
                    case 4:
                        result[1] = result[1].replace("\"", "");
                        Actor a = actors.get(id);
                        Movie m = movies.get(Integer.parseInt(result[1]));
                        a.movies.add(m);
                        m.actors.add(a);
                        break;
                    case 5:
                        result[1] = result[1].replace("\"", "");
                        Director d = directors.get(id);
                        Movie n = movies.get(Integer.parseInt(result[1]));
                        d.movies.add(n);
                        n.directors.add(d);
                        break;
                    default:
                        System.out.println("Error while loading file");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) try {
                br.close();
            } catch (IOException e) {
            }
        }
    }

    static void inputCheck(String input) {
        String comd = input.substring(2, input.indexOf("="));
        System.out.println(comd);
        String search = input.substring(input.indexOf("=")+1);
        search = search.replaceAll( "\"", "");
        System.out.println(search);

        boolean parsable = checkIfParsable(input);

        switch(comd) {
            case "filmsuche": printMovieSearch(search, parsable);
        }

    }

    private static boolean checkIfParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void printMovieSearch(String input, boolean parsable) {
        int id = -1, m = 0;
        if(parsable) { id = Integer.parseInt(input); }

        for (Movie movies : movies.values()) {
            if(movies.title.contains(input) || movies.id == id) {
                System.out.println("Title: " + movies.title);
                System.out.println("Description: " + movies.description);
                System.out.println("Release Date: " + movies.release);
                System.out.println("IMDb Rate: " + movies.imdbRate);
                System.out.println("IMDb Vote: " + movies.imdbVote);
                System.out.println("Genre: " + movies.genre);
                System.out.println("Movie ID: " + movies.id);
                m++;
            }
        }
        if (m == 0) {
            System.out.println("Error: No result found");
        }
    }

    private static void printActorSearch(String input, boolean parsable) {
        int id = -1, count = 0;
        if(parsable) { id = Integer.parseInt(input); }

        for (Actor actors : actors.values()) {
            if (actors.name.contains(input) || actors.id == id) {
               System.out.println("Name: " + actors.name);
                System.out.println("Actor ID " + actors.id);
               count++;
            }
        }
        if (count == 0) {
            System.out.println("Error: No result found");
        }
    }

    static Set<String> searchMovieNetwork(Movie movies1) {
        Set<String> movies = new HashSet<>();
        for (Actor actors : movies1.actors) {
            for (Movie movie : actors.movies) {
                movies.add("'" + movie.title + "'");
            }
        }
        movies.remove("'" + movies1.title + "'");
        return movies;
    }


    private static void printMovieNetwork(String input) {
        System.out.println("Search: " + input);
        for (Movie movies : movies.values()) {
            if(movies.title.contains(input) ||  movies.id.equals(input)) {
               System.out.println(movies.title);
               System.out.println("Actors: " + movies.actors.toString().replace("[","").replace("]",""));
//                System.out.print("Movies: ");
//                String separator = "";
//                for (String movie : searchMovieNetwork(movies)) {
//                    System.out.print(separator + movie);
//                    separator = ", ";
//                }
//            }
        }
    }

    static Set<String> searchActorNetwork(Actor actors1) {
        Set<String> actors = new HashSet<>();
        for (Movie movies : actors1.movies) {
            for (Actor actor : movies.actors) {
                actors.add(actor.name);
            }
        }
        actors.remove(actors1.name);
        return actors;
    }

    private static void printActorNetwork(String input) {
        System.out.println("Search: " + input);
        for (Actor actors : actors.values()) {
//            if(actors.name.contains(input) || actors.id.equals(input)) {
//                System.out.println(actors.name);
//                System.out.println("Movies: " + actors.movies.toString().replace("[","").replace("]",""));
//                System.out.print("Actors: ");
//                String separator = "";
//                for(String actor : searchActorNetwork(actors)) {
//                    System.out.print(separator + actor);
//                    separator = ", ";
//                }
        }
    }
}

