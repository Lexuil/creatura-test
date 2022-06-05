<?php
for ($i = 0; $i < 100; $i++) {
    $number = $i + 1;
    print(($number) . " " . ($number % 2 == 0 ? "buzz " : "") . ($number % 5 == 0 ? "bazz" : "") . "\n");
}
?>
