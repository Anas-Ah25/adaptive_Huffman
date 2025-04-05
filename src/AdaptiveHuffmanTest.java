/*  --- GENERATED --- */

public class AdaptiveHuffmanTest {
    public static void main(String[] args) {
        System.out.println("Running Adaptive Huffman Coding Tests...");
        testEmptyString(); // test case 1: empty string
        testSingleCharacter(); // test case 2: single character
        testRepeatedCharacters(); // test case 3: repeated characters
        testShortString(); // test case 4: short string "AABAC"
        testLongerString(); // test case 5: longer string "HELLO"
        System.out.println("All tests passed successfully!");
    }

    private static void testEmptyString() { // test encoding and decoding of empty string
        System.out.println("Test Case 1: Input = \"\" (Empty String)");
        AdaptiveHuffman huffman = new AdaptiveHuffman();
        String input = "";
        String encoded = huffman.encode(input);
        String compressionTrace = huffman.getStringCompression();
        String decoded = huffman.decode(encoded);

        System.out.println("Encoded String: " + encoded);
        System.out.println("Compression Trace: " + compressionTrace);
        System.out.println("Decoded String: " + decoded);
        assertEquals(input, decoded, "Test Case 1 Failed: Decoded output does not match input");
        System.out.println("Test Case 1 Passed\n");
    }

    private static void testSingleCharacter() { // test encoding and decoding of single character
        System.out.println("Test Case 2: Input = \"A\"");
        AdaptiveHuffman huffman = new AdaptiveHuffman();
        String input = "A";
        String encoded = huffman.encode(input);
        String compressionTrace = huffman.getStringCompression();
        String decoded = huffman.decode(encoded);

        System.out.println("Encoded String: " + encoded);
        System.out.println("Compression Trace: " + compressionTrace);
        System.out.println("Decoded String: " + decoded);
        assertEquals(input, decoded, "Test Case 2 Failed: Decoded output does not match input");
        System.out.println("Test Case 2 Passed\n");
    }

    private static void testRepeatedCharacters() { // test encoding and decoding of repeated characters
        System.out.println("Test Case 3: Input = \"AAAA\"");
        AdaptiveHuffman huffman = new AdaptiveHuffman();
        String input = "AAAA";
        String encoded = huffman.encode(input);
        String compressionTrace = huffman.getStringCompression();
        String decoded = huffman.decode(encoded);

        System.out.println("Encoded String: " + encoded);
        System.out.println("Compression Trace: " + compressionTrace);
        System.out.println("Decoded String: " + decoded);
        assertEquals(input, decoded, "Test Case 3 Failed: Decoded output does not match input");
        System.out.println("Test Case 3 Passed\n");
    }

    private static void testShortString() { // test encoding and decoding of "AABAC"
        System.out.println("Test Case 4: Input = \"ABCCCAAAA\"");
        AdaptiveHuffman huffman = new AdaptiveHuffman();
        String input = "ABCCCAAAA";
        String encoded = huffman.encode(input);
        String compressionTrace = huffman.getStringCompression();
        String decoded = huffman.decode(encoded);

        System.out.println("Encoded String: " + encoded);
        System.out.println("Compression Trace: " + compressionTrace);
        System.out.println("Decoded String: " + decoded);
        assertEquals(input, decoded, "Test Case 4 Failed: Decoded output does not match input");
        System.out.println("Test Case 4 Passed\n");
    }

    private static void testLongerString() { // test encoding and decoding of "HELLO"
        System.out.println("Test Case 5: Input = \"HELLO\"");
        AdaptiveHuffman huffman = new AdaptiveHuffman();
        String input = "HELLO";
        String encoded = huffman.encode(input);
        String compressionTrace = huffman.getStringCompression();
        String decoded = huffman.decode(encoded);

        System.out.println("Encoded String: " + encoded);
        System.out.println("Compression Trace: " + compressionTrace);
        System.out.println("Decoded String: " + decoded);
        assertEquals(input, decoded, "Test Case 5 Failed: Decoded output does not match input");
        System.out.println("Test Case 5 Passed\n");
    }

    private static void assertEquals(String expected, String actual, String message) { // simple assertion method
        if (!expected.equals(actual)) {
            System.out.println(message);
            System.out.println("Expected: " + expected);
            System.out.println("Actual: " + actual);
            System.exit(1);
        }
    }
}