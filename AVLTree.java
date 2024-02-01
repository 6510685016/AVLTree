package AVLTree;

public class AVLTree {
    Node root;

    public AVLTree() {
        this.root = null;
    }

    private int height(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int getBalance(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.right) - height(node.left);
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    private Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            // Key ซ้ำ
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        if (balance < -1 && key < node.left.key) {
            return rightRotate(node);
        }
        if (balance > 1 && key > node.right.key) {
            return leftRotate(node);
        }
        if (balance < -1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance > 1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void delete(int key) {
        root = delete(root, key);
    }

    private Node delete(Node root, int key) {
        if (root == null) {
            return root;
        }
        if (key < root.key) {
            root.left = delete(root.left, key);
        } else if (key > root.key) {
            root.right = delete(root.right, key);
        } else { // เจอโหนดที่จะลบ
            if ((root.left == null) || (root.right == null)) {
                Node temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }
                if (temp == null) { // ไร้ลูก
                    temp = root;
                    root = null;
                } else { // ลูก1
                    root = temp;
                }
            } else { // ลูกสอง
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = delete(root.right, temp.key);
            }
        }

        if (root == null) {
            return root;
        }
        root.height = 1 + Math.max(height(root.left), height(root.right));
        int balance = getBalance(root); // ปรับปรุงความสมดุล

        if (balance < -1 && getBalance(root.left) < 0) {// เจอ Left Left
            return rightRotate(root);
        }
        if (balance < -1 && getBalance(root.left) >= 0) {// เจอ Left Right
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        if (balance > 1 && getBalance(root.right) > 0) { // เจอ Right Right
            return leftRotate(root);
        }
        if (balance > 1 && getBalance(root.right) <= 0) { // เจอ Right Left
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();

        // เพิ่มโหนดลงใน AVL Tree
        avlTree.root = avlTree.insert(avlTree.root, 10);
        avlTree.root = avlTree.insert(avlTree.root, 20);
        avlTree.root = avlTree.insert(avlTree.root, 30);
        avlTree.root = avlTree.insert(avlTree.root, 40);
        avlTree.root = avlTree.insert(avlTree.root, 50);
        avlTree.root = avlTree.insert(avlTree.root, 25);

        // แสดงผลลัพธ์หลังจากการเพิ่มโหนด
        System.out.println("AVL Tree After insert Node:");
        avlTree.printTree(avlTree.root);

        // ลบโหนด
        avlTree.root = avlTree.delete(avlTree.root, 30);

        // แสดงผลลัพธ์หลังจากการลบโหนด
        System.out.println("\nAVL Tree After Del Node-30:");
        avlTree.printTree(avlTree.root);
    }

    public void printTree(Node root) {
        if (root != null) {
            // แสดงโหนดทางซ้าย
            printTree(root.left);

            // แสดงโหนดปัจจุบัน
            System.out.print(root.key + " ");

            // แสดงโหนดทางขวา
            printTree(root.right);
        }
    }
}