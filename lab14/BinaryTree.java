import java.util.ArrayList;

public class BinaryTree<T> {

    protected TreeNode root;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(TreeNode t) {
        root = t;
    }

    /* Print the values in the tree in preorder. */
    public void printPreorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            root.printPreorder();
            System.out.println();
        }
    }

    /* Print the values in the tree in inorder. */
    public void printInorder() {
        if (root == null) {
            System.out.println("none");
        }
        printInOrderHelper(root);
    }

    private void printInOrderHelper(TreeNode root) {
        if(root.right==null&&root.left==null){
            System.out.println(" "+root.item.toString()+" ");
        }
        else if(root.right == null){
            printInOrderHelper(root.left);
            System.out.println(" " + root.item.toString()+" ");
        }
        else if(root.left == null){
            printInOrderHelper(root.right);
            System.out.println(" " + root.item.toString()+" ");
        }
        else{
            printInOrderHelper(root.left);
            System.out.println(" " + root.item.toString()+" ");
            printInOrderHelper(root.right);
        }
    }

    /* Prints the BinaryTree in preorder or in inorder. Used for your testing. */
    protected static void print(BinaryTree t, String description) {
        System.out.println(description + " in preorder");
        t.printPreorder();
        System.out.println(description + " in inorder");
        t.printInorder();
        System.out.println();
    }

    protected class TreeNode {

        T item;
        TreeNode left;
        TreeNode right;
        int size = 0;

        public TreeNode(T item) {
            this.item = item; left = right = null;
        }

        public TreeNode(T item, TreeNode left, TreeNode right) {
            this.item = item;
            this.left = left;
            this.right = right;
        }

        /* Prints the nodes of the BinaryTree in preorder. Used for your testing. */
        private void printPreorder() {
            System.out.print(item + " ");
            if (left != null) {
                left.printPreorder();
            }
            if (right != null) {
                right.printPreorder();
            }
        }

        /* Prints the nodes of the BinaryTree in inorder. Used for your testing. */
        public void printInorder() {
            if (root == null) {
                System.out.println("none");
            }
            printInOrderHelper(root);
        }

        private void printInOrderHelper(TreeNode root) {
            if(root.right==null&&root.left==null){
                System.out.print(" "+root.item.toString()+" ");
            }
            else if(root.right == null){
                printInOrderHelper(root.left);
                System.out.print(" " + root.item.toString()+" ");
            }
            else if(root.left == null){
                printInOrderHelper(root.right);
                System.out.print(" " + root.item.toString()+" ");
            }
            else{
                printInOrderHelper(root.left);
                System.out.print(" " + root.item.toString()+" ");
                printInOrderHelper(root.right);
            }
        }
    }
}