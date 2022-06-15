import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;


public class Main {
    List<List<String>> posibilities = new ArrayList<List<String>>();

    public static void main(String[] args) {
        // List of Pokemon
        String pokemonString = "audino bagon baltoy banette bidoof braviary bronzor carracosta charmeleoncresselia croagunk darmanitan deino emboar emolga exeggcute gabite girafariggulpin haxorus heatmor heatran ivysaur jellicent jumpluff kangaskhan kricketunelandorus ledyba loudred lumineon lunatone machamp magnezone mamoswinenosepass petilil pidgeotto pikachu pinsir poliwrath poochyena porygon2 porygonzregisteel relicanth remoraid rufflet sableye scolipede scrafty seaking sealeo silcoonsimisear snivy snorlax spoink starly tirtouga trapinch treecko tyrogue vigoroth vulpixwailord wartortle whismur wingull yamask";
        
        // Split pokemon list into array
        String[] pokemonArray = pokemonString.split(" ");
        
        Main main = new Main();

        // Delete pokemon with no posibilities
        String[][] firstAndLastLetters = main.getFirstAndLast(pokemonArray, false);
        // Check each pokemon with first and last letter
        Set<String> pokemonArraySimplified = new HashSet<String>();
        for (int i = 0; i < pokemonArray.length; i++) {
            if (!(!Arrays.asList(firstAndLastLetters[1]).contains(pokemonArray[i].substring(0, 1)) && !Arrays.asList(firstAndLastLetters[0]).contains(pokemonArray[i].substring(pokemonArray[i].length() - 1)))) {
                pokemonArraySimplified.add(pokemonArray[i]);
            }
        }
        
        String[][] firstAndLastLettersSimplified = main.getFirstAndLast(pokemonArraySimplified.toArray(new String[pokemonArraySimplified.size()]), true);

        // Convert firstAndLastLettersSimplified to List
        List<String> letters = new ArrayList<String>(Arrays.asList(firstAndLastLettersSimplified[0]));

        // Print posibilities =======================================

        List<List<String>> posibilities = main.getPosibilities(pokemonArraySimplified, letters);

        // Sort posibilities by length
        posibilities.sort((a, b) -> b.size() - a.size());
        // Print posibilities sorted by length
        System.out.println("The maximun length is: " + posibilities.get(0).size());
        System.out.println("And one of those posibilities is: ");
        System.out.println(posibilities.get(0));

        // ==========================================================


    }

    // Function that return first and last letter of each pokemon
    public String[][] getFirstAndLast(String[] pokemon, boolean duplicates) {
        // Extract first letter of each pokemon
        String[] firstLetter = new String[pokemon.length];
        for (int i = 0; i < pokemon.length; i++) {
            firstLetter[i] = pokemon[i].substring(0, 1);
        }
        // Extract last letter of each pokemon
        String[] lastLetter = new String[pokemon.length];
        for (int i = 0; i < pokemon.length; i++) {
            lastLetter[i] = pokemon[i].substring(pokemon[i].length() - 1);
        }

        // Delete duplicates from first and last letter
        if (!duplicates) {
            firstLetter = Arrays.stream(firstLetter).distinct().toArray(String[]::new);
            lastLetter = Arrays.stream(lastLetter).distinct().toArray(String[]::new);
        }
        
        return new String[][]{firstLetter, lastLetter};
    }

    public void searchNextPosibility(String item, Set<String> items, List<String> letters, List<String> localList) {
        Set<String> itemsn = new HashSet<String>(items);
        List<String> lettersn = new ArrayList<String>(letters);
        List<String> localListn = new ArrayList<String>(localList);
        localListn.add(item);
        itemsn.remove(item);

        while(lettersn.contains(item.substring(item.length() - 1))) {
            lettersn.remove(item.substring(item.length() - 1));
            try {
                // get the string from items where the firs letters is equal to item.substring(item.length() - 1)
                String nextItem = itemsn.stream().filter(s -> s.substring(0, 1).equals(item.substring(item.length() - 1))).findFirst().get();
                itemsn.remove(nextItem);
                searchNextPosibility(nextItem, itemsn, lettersn, localListn);
            } catch (Exception  e) {
            }
        }

        posibilities.add(localListn);
    }

    public List<List<String>> getPosibilities(Set<String> pokemonSet, List<String> letters) {
        // For each element on pokemonSet, search for next posibility
        for (int i = 0; i < pokemonSet.size(); i++) {
            searchNextPosibility(pokemonSet.toArray()[i].toString(), pokemonSet, letters, new ArrayList<String>());
        }
        return posibilities;
    }
}
