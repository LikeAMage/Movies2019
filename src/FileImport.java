import java.io.*;
import java.util.*;

public class FileImport {
    /*
    HashMaps for all Actors, Movies and Directors, so each value has an serialized key
     */
    static Map<Integer, Actor> actors = new HashMap<>();
    static Map<Integer, Movie> movies = new HashMap<>();
    static Map<Integer, Director> directors = new HashMap<>();

    static void importFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists() && file.isDirectory()) {
            System.out.println("File does not exist");
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

                //Filling the Hash-Maps
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
                        movies.put(id, new Movie(id, result[1], result[2], result[4], result[6], result[5], result[3]));
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
        // Regex so format of Input is correct and can be used in Switch-Case
        String comd = input.substring(2, input.indexOf("="));
        String search = input.substring(input.indexOf("=")+1);
        search = search.replaceAll( "\"", "");

        switch(comd) {
            case "filmsuche": printMovieSearch(search); break;
            case "schauspielersuche": printActorSearch(search); break;
            //case "filmnetzwerk": printMovieNetwork(search); break;
            //case "schauspielernetzwerk": printActorNetwork(search); break;
            default:
                System.out.println("No such command found."); return;
        }

    }

    //check if input is parsable into an Integer
    private static boolean checkIfParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void printMovieSearch(String input) {
        int id = -1, m = 0;
        boolean parsable = checkIfParsable(input);
        if(parsable) { id = Integer.parseInt(input); }


        for (Movie movies : movies.values()) {
            //first checks if it input is a string if it is not, will check if the id
            if(movies.title.contains(input) || movies.id == id) {
                System.out.println("Title: " + movies.title);
                System.out.println("Description: " + movies.description);
                System.out.println("Release Date: " + movies.release);
                System.out.println("IMDb Rate: " + movies.imdbRate);
                System.out.println("IMDb Votes: " + movies.imdbVote);
                System.out.println("Genre: " + movies.genre);
                System.out.println("Movie ID: " + movies.id + "\n");
                m++;
            }
        }
        if (m == 0) {
            System.out.println("Error: No result found");
        }
    }

    private static void printActorSearch(String input) {
        int id = -1, count = 0;
        boolean parsable = checkIfParsable(input);
        if(parsable) {
            id = Integer.parseInt(input);
        }

        for (Actor actors : actors.values()) {
            if (actors.name.contains(input) || actors.id == id) {
               System.out.println("Name: " + actors.name);
               System.out.println("Actor ID: " + actors.id + "\n");
               count++;
            }
        }
        if (count == 0) {
            System.out.println("Error: No result found");
        }
    }
}

