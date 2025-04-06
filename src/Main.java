import java.nio.file.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static String readFile(String path){
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            System.err.println("Can't read file: " + e);
            return "";
        }
    }


    public static void writeFile(String path, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e);
        }
    }

    public static void main(String[] args) {
        String pathBase = "src/";
        String fileName;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Encode");
            System.out.println("2. Decode");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter the file name to encode: ");
                    fileName = scanner.nextLine();
                    String content = readFile(pathBase + fileName);
                    if (content.isEmpty()) {
                        System.out.println("No content to encode.");
                        break;
                    }
                    AdaptiveHuffman huffman = new AdaptiveHuffman();
                    String encoded = huffman.encode(content);
                    System.out.println("Encoded String: " + encoded);
                    System.out.println("Compression Trace: " + huffman.getStringCompression());
                    String encodedFileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_compressed.txt";
                    writeFile(pathBase + encodedFileName, encoded);
                    System.out.println("Encoded content written to: " + encodedFileName);
                    break;

                case 2:
                    System.out.print("Enter the file name to decode: ");
                    fileName = scanner.nextLine();
                    String encodedContent = readFile(pathBase + fileName);
                    if (encodedContent.isEmpty()) {
                        System.out.println("No content to decode.");
                        break;
                    }
                    huffman = new AdaptiveHuffman();
                    String decoded = huffman.decode(encodedContent);
                    System.out.println("Decoded String: " + decoded);
                    String decodedFileName = fileName.substring(0, fileName.lastIndexOf('_')) + "_decompressed.txt";
                    writeFile(pathBase + decodedFileName, decoded);
                    System.out.println("Decoded content written to: " + decodedFileName);
                    break;

                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}