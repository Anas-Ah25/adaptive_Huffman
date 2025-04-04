public class Main {
    public static void main(String[] args) {


    // take the input from the user

    int choice = JOptionPane.showInputDialog("For encoding press 1, for decoding press 2: ");
    if (choice == 1) {
        // take the input from the user
        String input = JOptionPane.showInputDialog("Enter the string to be encoded: ");
        // create a new instance of the Huffman class
        AdaptiveHuffman huffman = new AdaptiveHuffman(input);
        // encode the string
        huffman.encode();
    } else if (choice == 2) {
        // take the input from the user
        String input = JOptionPane.showInputDialog("Enter the string to be decoded: ");
        // create a new instance of the Huffman class
        AdaptiveHuffman huffman = new AdaptiveHuffman(input);
        // decode the string
        huffman.decode();
    } else {
        System.out.println("Invalid choice");
    }













    }
}