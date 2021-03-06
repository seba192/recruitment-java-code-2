package pl.sparkbit;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CodeImpl implements Code {
    /**
     Your task is to encode the alphabet as a binary tree using the frequencies of letters in the given text. You need
     to perform the following steps:

     * for each character in the text calculate its number of occurrences / frequency, e.g. for string `aba` it would
     be `a -> 2`, `b -> 1`
     * for each character and its frequency create a one-node tree
     * take two trees `T1` and `T2` with lowest frequencies and merge them into a larger tree `T1-2` (`T1` should
     become the left sub-tree and `T2` the right subtree)
     * repeat the previous step until there is only 1 tree left

     That last tree represents the created encoding.
     For example, given text `abacaca` you should get:
     a+b+c(7)
     /  \
     /    \
     /      \
     /        \
     b+c(3)     a(4)
     /   \
     /     \
     b(1)   c(2)
     All the auxiliary classes for building the tree are provided and should not be modified.
     */
    @Override
    public Node createCode(String text) {
        Set<Node> nodes = text.codePoints()
                .mapToObj(ch -> (Node) new Leaf((char) ch, getCount(text, ch)))
                .sorted()
                .collect(Collectors.toCollection(TreeSet::new));

        while (nodes.size() > 1){
            Iterator<Node> iterator = nodes.iterator();
            Node node1 = iterator.next();
            Node node2 = iterator.next();
            nodes.remove(node1);
            nodes.remove(node2);
            nodes.add(new InnerNode(node1, node2));
        }

        return nodes.stream().findFirst().orElse(null);
    }

    private int getCount(String text, int ch) {
        return (int) text.chars().filter(c -> c == ch).count();
    }
}
