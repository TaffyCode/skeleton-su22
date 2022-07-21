import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet implements TrieSet61BL {

    private Node root;
    private class Node {
        private boolean character;
        private HashMap<Character, Node> trieSet;

        public Node(boolean b) {
            character = b;
            trieSet = new HashMap<>();
        }
    }

    public MyTrieSet() {
        root = new Node(false);
    }

    @Override
    public void clear() {
        root = new Node(false);
    }

    @Override
    public boolean contains(String key) {
        Node current = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (!current.trieSet.containsKey(c)) {
                return false;
            }
            current = current.trieSet.get(c);
        }
        Node node = current;
        return node.character;
    }

    @Override
    public void add(String key) {
        if (key != null || key.length() < 1){
            Node current = root;
            for (int i = 0, j = key.length(); i < j; i++) {
                char character = key.charAt(i);
                if (!current.trieSet.containsKey(character)) {
                    current.trieSet.put(character, new Node(false));
                }
                current = current.trieSet.get(character);
            }
            current.character = true;
        }
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        List<String> list = new ArrayList<>();
        Node current = root;
        for (int i = 0; i < prefix.length(); i++) {
            char character = prefix.charAt(i);
            current = current.trieSet.get(character);
        }
        Node node = current;
        keysWithPrefixSupport(prefix, list, node);
        return list;
    }

    private void keysWithPrefixSupport(String prefix, List<String> list, Node node) {
        if (node == null) {
            return;
        } else if (node.character) {
            list.add(prefix);
        }
        for (char character : node.trieSet.keySet()) {
            keysWithPrefixSupport(prefix + character, list, node.trieSet.get(character));
        }
    }

    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
