# Charfreq

Solution of the assignment:

Statement
Implement a balanced search tree as a Map ADT. Any of {RB, AVL, B}-tree will work. 
Ensure that your tree has following methods implemented:
    public V delete(K key)
    private Node[K] predecessor(Node n [, K k])  // K - for B-tree
    private Node[K] successor(Node n [, K k])    // K - for B-tree
    public void traverse(java.util.function.BiConsumer visitor)
    public void traverse(java.util.function.BiConsumer visitor, K from, K to)
For a text in input.txt count character frequency distribution (case insensitive) using Map<Character, Integer> dictionary. 
Remove following characters from your map: 
:;<=>?@[\]^_`        //These are ASCII (0x3A-0x40, 0x5B-0x60).

Print ordered statistics as [letter]:[count] to output.txt for a range [0-z]. Skip 0-elements. Separate by spaces. Use traverse(...) method for this. 
Example #1
input.txt
Lazy fox Jack.
output.txt
a:2 c:1 f:1 j:1 k:1 l:1 o:1 x:1 y:1 z:1
