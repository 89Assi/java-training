package it.ecubit.java.training.collections.test;

import it.ecubit.java.training.beans.Actor;
import it.ecubit.java.training.beans.Director;
import it.ecubit.java.training.beans.Movie;
import it.ecubit.java.training.loader.tmdb.ImdbLoader;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class JavaCollectionsTest {

    public static void main(String[] args) {
        // Loading of all the top 1000 movies can take up to 10 minutes (needs to call the TMDB APIs for retrieving all the data)
        List<Movie> top1000Movies = ImdbLoader.loadMovies();


        // Exercise 1: Sort the movies by release year (from the most recent to the less recent)
        // and print the results with a counter before the movie info, one for each row
        // (i. e. '1) <MOVIE INFO>'\n'2) <MOVIE INFO>', ...)

        /*
         *      List<Movie> movies = new ArrayList<>();
         *      Collections.sort(movies, (movie1, movie2) -> String.compare(movie2.getYear(), movie1.getYear()));
         *
         *      int counter =1;
         *      for (Movie movie : film) {
         *      
         */

        AtomicInteger counter = new AtomicInteger(1);

        top1000Movies.stream().sorted(Comparator.comparingInt(Movie::getYear).reversed())
                .forEachOrdered(movie -> System.out.println(counter.getAndIncrement() + " " + movie));

        System.out.println("##################################################################");
        // Exercise 2: Sort the movies lexicographically by title
        // and print the results with a counter before the movie info, one for each row
        // (i. e. '1) <MOVIE INFO>'\n'2) <MOVIE INFO>', ...)
        counter.set(1);

        top1000Movies.stream().sorted(Comparator.comparingInt(Movie::getYear).reversed())
                .forEachOrdered(movie -> {
                    System.out.println(counter.getAndIncrement() + " " + movie);
                });


        System.out.println("##################################################################");
        // Exercise 3: How many movies has been directed by 'Peter Jackson'? Print all of them, one by line.

        top1000Movies.stream().filter(movie -> movie.getDirectors().stream().anyMatch(director -> director.getName().equals("Peter Jackson")))
                .forEachOrdered(movie -> System.out.println((counter.getAndIncrement() + " " + movie)));


        System.out.println("##################################################################");
        // Exercise 4: How many movies did 'Orlando Bloom' star in as an actor? Print all of them, one by line.

        var listFilder = top1000Movies.stream().filter(movie -> movie.getActors().stream().anyMatch(actors -> actors.getName().equals("Orlando Bloom"))).toList();
        System.out.println(listFilder.size() + listFilder.toString());

        System.out.println("##################################################################");
        // Exercise 5: Sort the movies by rating (ascending, from the less rated to the most rated)
        // and by movie title (lexicographically) as a secondary sort criterion
        // and print the results with a counter before the movie info, one for each row

        counter.set(1);

        top1000Movies.stream().sorted(Comparator.comparing(Movie::getRating)
                        .thenComparing(Comparator.comparing(Movie::getTitle)))
                .forEachOrdered(movie -> {
                    System.out.println(counter.getAndIncrement() + " " + movie);
                });

        System.out.println("##################################################################");

        // Exercise 6: Sort the movies by duration (ascending, from the shortest to the longest oned)
        // and by release year (ascending, from the less recent to the most recent one) as a secondary sort criterion
        // and print the results with a counter before the movie info, one for each row


        counter.set(1);

        top1000Movies.stream().sorted(Comparator.comparing(Movie::getDuration)
                        .thenComparing(Comparator.comparing(Movie::getYear)))
                .forEachOrdered(movie -> {
                    System.out.println(counter.getAndIncrement() + " " + movie);
                });

        System.out.println("##################################################################");
        // Exercise 7: Group movies by actor, i.e. produce a map with actor name as key and a list of movies as values;
        // the list should contain the films in which the actor starred in (no duplicates)
        // and print the map with a counter before the map entry, one for each row

        Map<String, List<Movie>> actorsMovieMap = new HashMap<>();

        counter.set(1);

        top1000Movies.forEach(movie -> {
            movie.getActors().forEach(actor -> actorsMovieMap.computeIfAbsent(actor.getName(), k -> new ArrayList<>()).add(movie));

            actorsMovieMap.forEach((key, value) ->
                    System.out.println(counter.getAndIncrement() + ") Attori: " + key + " " + value));
        });

        System.out.println("##################################################################");
        // Exercise 8: Group movies by director, i.e. produce a map with director name as key and a list of movies as values;
        // the list should contain the films in which the director took care of the direction (no duplicates)
        // and print the map with a counter before the map entry, one for each row


        Map<String, List<Movie>> directorsMovieMap = new HashMap<>();

        counter.set(1);

        top1000Movies.forEach(movie -> {
            movie.getDirectors().forEach(director ->
                    directorsMovieMap.computeIfAbsent(director.getName(), k ->
                            new ArrayList<>()).add(movie));
        });

        directorsMovieMap.forEach((key, value) ->
                System.out.println(counter.getAndIncrement() + ") Regista: " + key + " " + value));



        System.out.println("##################################################################");
        // Exercise 9: Add the film's box office total income to the movie loading process (field 'Gross' in the CSV)
        // and print the first 20 films who earned most money ever, one for each row, from the first to the 20th

        List<Movie> top20Movies = top1000Movies.stream()
                        .sorted(Comparator.comparing(Movie::getGross).reversed()).limit(20).toList();

        top20Movies.forEach(System.out::println);



        System.out.println("##################################################################");
        // Exercise 10: Add the number of votes received on the Social Media for each film (field 'No_of_Votes' in the CSV)
        // and print the first 20 films who received most votes, one for each row, from the first to the 20th

        List<Movie> top20Votes = top1000Movies.stream()
                        .sorted(Comparator.comparing(Movie::getVotes).reversed()).limit(20).toList();

        top20Votes.forEach(System.out::println);

        System.out.println("########FINISH########");
    }
}