import java.nio.file.*;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            return "";
        }
    }

    public static void writeFile(String path, String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AdaptiveHuffman huffman = new AdaptiveHuffman();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1) Encode");
            System.out.println("2) Decode");
            System.out.println("3) Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 3) {
                System.out.println("Bye.");
                break;
            }

            System.out.print("Enter file name: ");
            String fname = sc.nextLine();
            String content = readFile("src/" + fname);
            if (content.isEmpty()) {
                System.out.println("File not found or empty.");
                continue;
            }

            // Clear old snapshots
            SnapshotRecorder.clear();

            if (choice == 1) {
                // Encode
                String encoded = huffman.encode(content);
                System.out.println("Encoded: " + encoded);
                writeFile("src/" + fname.replace(".txt","_compressed.txt"), encoded);
            } else if (choice == 2) {
                // Decode
                String decoded = huffman.decode(content);
                System.out.println("Decoded: " + decoded);
                writeFile("src/" + fname.replace(".txt","_decompressed.txt"), decoded);
            } else {
                System.out.println("Invalid choice");
                continue;
            }

            System.out.print("Show step-by-step animation? (Y/N): ");
            String ans = sc.nextLine();
            if (ans.equalsIgnoreCase("Y")) {
                new ReplayVisualizer(SnapshotRecorder.getEvents());
            }
        }
        sc.close();
    }
}
