package lab9;

public class BinarySearchTree {
    private static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(int data) {
        root = insertRecursive(root, data);
    }

    private Node insertRecursive(Node node, int data) {
        if (node == null) {
            return new Node(data);
        }

        if (data < node.data) {
            node.left = insertRecursive(node.left, data);
        } else if (data > node.data) {
            node.right = insertRecursive(node.right, data);
        }

        return node;
    }

    public int maxDepth(Node node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = maxDepth(node.left);
        int rightDepth = maxDepth(node.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    public int sizeRecursive(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + sizeRecursive(node.left) + sizeRecursive(node.right);
    }

    public int sizeIterative(Node node) {
        if (node == null) {
            return 0;
        }
        int size = 0;
        java.util.Queue<Node> queue = new java.util.LinkedList<>();
        queue.offer(node);
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            size++;
            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }
        return size;
    }

    public void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrder(Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.print(node.data + " ");
        inOrder(node.right);
    }

    public void postOrder(Node node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.data + " ");
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(25);
        bst.insert(15);
        bst.insert(50);
        bst.insert(10);
        bst.insert(22);
        bst.insert(35);
        bst.insert(70);
        bst.insert(4);
        bst.insert(12);
        bst.insert(18);
        bst.insert(24);
        bst.insert(31);
        bst.insert(44);
        bst.insert(66);
        bst.insert(90);

        System.out.println("Maximum Depth: " + bst.maxDepth(bst.root));
        System.out.println("Size (Recursive): " + bst.sizeRecursive(bst.root));
        System.out.println("Size (Iterative): " + bst.sizeIterative(bst.root));

        System.out.println("Pre-order Traversal:");
        bst.preOrder(bst.root);
        System.out.println();
        
        System.out.println("In-order Traversal:");
        bst.inOrder(bst.root);
        System.out.println();
        
        System.out.println("Post-order Traversal:");
        bst.postOrder(bst.root);
        System.out.println();
    }
}