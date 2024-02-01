package AVLTree;

class Node {
    int key, height;
    Node left, right;

    public Node(int key) {
        this.key = key;
        this.height = 1;
    }
}
