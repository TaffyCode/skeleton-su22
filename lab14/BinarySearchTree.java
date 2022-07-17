public class BinarySearchTree<T extends Comparable<T>> extends BinaryTree<T> {

    /* Creates an empty BST. Super() calls the constructor for BinaryTree (not in scope). */
    public BinarySearchTree() {
        super();
    }

    /* Creates a BST with root as ROOT. */
    public BinarySearchTree(TreeNode root) {
        super(root);
    }

    /* Returns true if the BST contains the given KEY. */
    public boolean contains(T key) {
        return containsHelper(root, key);
    }

    private boolean containsHelper(TreeNode root, T key) {
        if (root == null) {
            return false;
        }
        if (key.compareTo(root.item) < 0) {
            return containsHelper(root.left, key);
        }
        if (key.compareTo(root.item) > 0) {
            return containsHelper(root.right, key);
        }
        return true;
    }

    /* Adds a node for KEY iff KEY isn't in the BST already. */
    public void add(T key) {
        root = addHelper(root, key);
    }

    private TreeNode addHelper(TreeNode root, T key) {
        if (root == null) {
            return new TreeNode(key);
        }

        if (key.compareTo(root.item) < 0) {
            root.left = addHelper(root.left, key);
        }
        else if (key.compareTo(root.item) > 0) {
            root.right = addHelper(root.right, key);
        }
        int right = 0;
        int left = 0;
        if (root.right != null) {
            right = root.right.size;
        }
        if (root.left != null) {
            left = root.left.size;
        }
        root.size = right + left + 1;
        return root;
    }

    /* Deletes a node from the BST. 
     * Even though you do not have to implement delete, you 
     * should read through and understand the basic steps.
    */
    public T delete(T key) {
        TreeNode parent = null;
        TreeNode curr = root;
        TreeNode delNode = null;
        TreeNode replacement = null;
        boolean rightSide = false;

        while (curr != null && !curr.item.equals(key)) {
            if (curr.item.compareTo(key) > 0) {
                parent = curr;
                curr = curr.left;
                rightSide = false;
            } else {
                parent = curr;
                curr = curr.right;
                rightSide = true;
            }
        }
        delNode = curr;
        if (curr == null) {
            return null;
        }

        if (delNode.right == null) {
            if (root == delNode) {
                root = root.left;
            } else {
                if (rightSide) {
                    parent.right = delNode.left;
                } else {
                    parent.left = delNode.left;
                }
            }
        } else {
            curr = delNode.right;
            replacement = curr.left;
            if (replacement == null) {
                replacement = curr;
            } else {
                while (replacement.left != null) {
                    curr = replacement;
                    replacement = replacement.left;
                }
                curr.left = replacement.right;
                replacement.right = delNode.right;
            }
            replacement.left = delNode.left;
            if (root == delNode) {
                root = replacement;
            } else {
                if (rightSide) {
                    parent.right = replacement;
                } else {
                    parent.left = replacement;
                }
            }
        }
        return delNode.item;
    }
}
