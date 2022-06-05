<?php

function extractLetters($lettersList, $duplicates) {
    // Extract all last letters from each pokemon in pokemon list
    $lastLetters = array_map(function ($pokemon) {
        return substr($pokemon, -1);
    }, $lettersList);

    // Extract all firts letters from each pokemon in pokemon list
    $firstLetters = array_map(function ($pokemon) {
        return substr($pokemon, 0, 1);
    }, $lettersList);
    
    if (!$duplicates) {
        $lastLetters = array_unique($lastLetters);
        $firstLetters = array_unique($firstLetters);
    }

    return array($firstLetters, $lastLetters);
}

$pokemonSting = "audino bagon baltoy banette bidoof braviary bronzor carracosta charmeleoncresselia croagunk darmanitan deino emboar emolga exeggcute gabite girafariggulpin haxorus heatmor heatran ivysaur jellicent jumpluff kangaskhan kricketunelandorus ledyba loudred lumineon lunatone machamp magnezone mamoswinenosepass petilil pidgeotto pikachu pinsir poliwrath poochyena porygon2 porygonzregisteel relicanth remoraid rufflet sableye scolipede scrafty seaking sealeo silcoonsimisear snivy snorlax spoink starly tirtouga trapinch treecko tyrogue vigoroth vulpixwailord wartortle whismur wingull yamask";

// Split pokemon list into array
$pokemonList = explode(" ", $pokemonSting);

// Delete pokemon with no posibilities
$firstAndLastLetters = extractLetters($pokemonList, false);

$pokemonListSimplify = array_filter($pokemonList, function ($pokemon) use ($firstAndLastLetters) {
    return !(!in_array(substr($pokemon, -1), $firstAndLastLetters[0]) && !in_array(substr($pokemon, 0, 1), $firstAndLastLetters[1]));
});

$letters = extractLetters($pokemonListSimplify, true);
$resultArrays = array();

function findAllKeys($list, $value) {
    $indices = array();
    $flag = true;
    while($flag) {
        $ndex = array_search(substr($value, -1), $list);
        if (!empty($ndex)) {
            array_push($indices, $ndex);
            unset($list[$ndex]);
        } else {
            $flag = false;
        }
    }
    return $indices;
}

function searchNextPosibility($item,$items,$itemsLetters,$localArray) {
    array_push($localArray, $item);
    unset($items[array_search($item, $items)]);
    $nextIndices = findAllKeys($itemsLetters, $item);
    if (!empty($nextIndices)){
        foreach ($nextIndices as $nextIndex) {
            unset($itemsLetters[$nextIndex]);
            if(isset($items[$nextIndex])) {
                searchNextPosibility($items[$nextIndex], $items, $itemsLetters, $localArray);
            }
        }
    } else {
        array_push($GLOBALS['resultArrays'], array($localArray, count($localArray)));
        return;
    }
}

foreach ($pokemonListSimplify as $pokemon) {
    searchNextPosibility($pokemon, $pokemonListSimplify, $letters[0], array());
}

function lengthCompare($a, $b) {
    return $b[1] - $a[1];
}

usort($resultArrays, "lengthCompare");

$maxLength = $resultArrays[0][1];

print("The maximun length is: " . $maxLength . "\n");
print("And the following arrays have the maximun length: \n");

foreach ($resultArrays as $resultArray) {
    if ($resultArray[1] == $maxLength) {
        print_r($resultArray[0]);
    } else {
        break;
    }
}

?>
