//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Scanner;
//
//
//public class Main {
//
//
//
//
//    /* ====================== Read text from files function ======================= */
//    public String readFile(String path) {
//        StringBuilder content = new StringBuilder();
//        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                content.append(line).append("\n");
//            }
//        } catch (IOException e) {
//            System.err.println("cant' read file:" + e);
//        }
//        return content.toString();
//    }
//    // write to file
//    public
//
//    /* ====================== Main function ======================= */
//
//    public static void main(String[] args) {
//        String pathBase = "src/";
//        String fileName;
//        addaptiveHuffman huffman = new adaptiveHuffman();
//        while (true) {
//            System.out.println("1. Encode");
//            System.out.println("2. Decode");
//            System.out.println("3. Exit");
//            System.out.print("Enter your choice: ");
//            Scanner scanner = new Scanner(System.in);
//            int choice = scanner.nextInt();
//            switch (choice) {
//                case 1:
//                    // take file name from user
//                    System.out.print("Enter the file name to encode: ");
//                    Scanner scanner1 = new Scanner(System.in);
//                    fileName = scanner1.nextLine();
//                    // read file
//                    String content = readFile(pathBase + fileName);
//                    // Call encode function
//                    String encoded = huffman.encode(content);
//                    System.out.println("Encoded String: " + encoded);
//                    System.out.println("Compression Trace: " + huffman.getStringCompression());
//                    // write to file
//                    String encodedFileName = fileName.substring(0, fileName.lastIndexOf('.')) + "_compressed.txt";
//                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathBase + encodedFileName))) {
//                        writer.write(encoded);
//                    } catch (IOException e) {
//                        System.err.println("Error writing to file: " + e);
//                    }
//
//
//                case 2:
//                    // Call decode function
//                    break;
//                case 3:
//                    System.exit(0);
//                    break;
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
//
//        }
//    }
//}