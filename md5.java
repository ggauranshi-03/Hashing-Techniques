public class md5 {

    public static void main(String[] args) {
        String input = "abcdefghij";
        long startTime = System.nanoTime();
        // Convert input string to bytes and calculate MD5 hash
        byte[] hash = MD5(input.getBytes());

        long endTime = System.nanoTime();
        // Convert hash bytes to hexadecimal representation
        String hashHex = bytesToHex(hash);
        // Print the MD5 hash
        System.out.println("MD5 Hash: " + hashHex);
        System.out.println("Execution Time: " + (endTime - startTime) + " nanoseconds");
    }

    public static byte[] MD5(byte[] message) {
        int messageLength = message.length * 8;
        // Calculate the number of 512-bit blocks needed to store the message
        int numBlocks = ((messageLength + 64) >>> 9) + 1; // Dividing by 512 bits (64 bytes)
        int totalLength = numBlocks * 64; // Total length in bytes for padding
        byte[] paddedMessage = new byte[totalLength];
        // Copy the original message to the paddedMessage array
        System.arraycopy(message, 0, paddedMessage, 0, message.length);
        // Append a 1-bit to the message followed by zeros
        paddedMessage[message.length] = (byte) 0x80;
        // Append the original message length (in bits) as a 64-bit little-endian integer
        long originalLengthBits = (long) messageLength;
        for (int i = 0; i < 8; i++) {
            paddedMessage[totalLength - 8 + i] = (byte) (originalLengthBits >>> (8 * i));
        }

        // Initialize MD5 hash values
        int a = 0x67452301;
        int b = (int) 0xEFCDAB89L;
        int c = (int) 0x98BADCFEL;
        int d = 0x10325476;

        // Process each 512-bit block of the padded message
        for (int i = 0; i < paddedMessage.length; i += 64) {
            int[] words = new int[16];
            // Divide each 512-bit block into 32-bit words
            for (int j = 0; j < 16; j++) {
                int word = 0;
                for (int k = 0; k < 4; k++) {
                    word |= (paddedMessage[i + j * 4 + k] & 0xFF) << (8 * k);
                }
                words[j] = word;
            }

            // Save current hash values for later use in calculations
            int aa = a;
            int bb = b;
            int cc = c;
            int dd = d;

            a = FF(a, b, c, d, words[0], 7, 0xD76AA478);
            d = FF(d, a, b, c, words[1], 12, 0xE8C7B756);
            c = FF(c, d, a, b, words[2], 17, 0x242070DB);
            b = FF(b, c, d, a, words[3], 22, 0xC1BDCEEE);
            a = FF(a, b, c, d, words[4], 7, 0xF57C0FAF);
            d = FF(d, a, b, c, words[5], 12, 0x4787C62A);
            c = FF(c, d, a, b, words[6], 17, 0xA8304613);
            b = FF(b, c, d, a, words[7], 22, 0xFD469501);
            a = FF(a, b, c, d, words[8], 7, 0x698098D8);
            d = FF(d, a, b, c, words[9], 12, 0x8B44F7AF);
            c = FF(c, d, a, b, words[10], 17, 0xFFFF5BB1);
            b = FF(b, c, d, a, words[11], 22, 0x895CD7BE);
            a = FF(a, b, c, d, words[12], 7, 0x6B901122);
            d = FF(d, a, b, c, words[13], 12, 0xFD987193);
            c = FF(c, d, a, b, words[14], 17, 0xA679438E);
            b = FF(b, c, d, a, words[15], 22, 0x49B40821);

            a = GG(a, b, c, d, words[1], 5, 0xF61E2562);
            d = GG(d, a, b, c, words[6], 9, 0xC040B340);
            c = GG(c, d, a, b, words[11], 14, 0x265E5A51);
            b = GG(b, c, d, a, words[0], 20, 0xE9B6C7AA);
            a = GG(a, b, c, d, words[5], 5, 0xD62F105D);
            d = GG(d, a, b, c, words[10], 9, 0x02441453);
            c = GG(c, d, a, b, words[15], 14, 0xD8A1E681);
            b = GG(b, c, d, a, words[4], 20, 0xE7D3FBC8);
            a = GG(a, b, c, d, words[9], 5, 0x21E1CDE6);
            d = GG(d, a, b, c, words[14], 9, 0xC33707D6);
            c = GG(c, d, a, b, words[3], 14, 0xF4D50D87);
            b = GG(b, c, d, a, words[8], 20, 0x455A14ED);
            a = GG(a, b, c, d, words[13], 5, 0xA9E3E905);
            d = GG(d, a, b, c, words[2], 9, 0xFCEFA3F8);
            c = GG(c, d, a, b, words[7], 14, 0x676F02D9);
            b = GG(b, c, d, a, words[12], 20, 0x8D2A4C8A);

            a = HH(a, b, c, d, words[5], 4, 0xFFFA3942);
            d = HH(d, a, b, c, words[8], 11, 0x8771F681);
            c = HH(c, d, a, b, words[11], 16, 0x6D9D6122);
            b = HH(b, c, d, a, words[14], 23, 0xFDE5380C);
            a = HH(a, b, c, d, words[1], 4, 0xA4BEEA44);
            d = HH(d, a, b, c, words[4], 11, 0x4BDECFA9);
            c = HH(c, d, a, b, words[7], 16, 0xF6BB4B60);
            b = HH(b, c, d, a, words[10], 23, 0xBEBFBC70);
            a = HH(a, b, c, d, words[13], 4, 0x289B7EC6);
            d = HH(d, a, b, c, words[0], 11, 0xEAA127FA);
            c = HH(c, d, a, b, words[3], 16, 0xD4EF3085);
            b = HH(b, c, d, a, words[6], 23, 0x04881D05);
            a = HH(a, b, c, d, words[9], 4, 0xD9D4D039);
            d = HH(d, a, b, c, words[12], 11, 0xE6DB99E5);
            c = HH(c, d, a, b, words[15], 16, 0x1FA27CF8);
            b = HH(b, c, d, a, words[2], 23, 0xC4AC5665);

            a = II(a, b, c, d, words[0], 6, 0xF4292244);
            d = II(d, a, b, c, words[7], 10, 0x432AFF97);
            c = II(c, d, a, b, words[14], 15, 0xAB9423A7);
            b = II(b, c, d, a, words[5], 21, 0xFC93A039);
            a = II(a, b, c, d, words[12], 6, 0x655B59C3);
            d = II(d, a, b, c, words[3], 10, 0x8F0CCC92);
            c = II(c, d, a, b, words[10], 15, 0xFFEFF47D);
            b = II(b, c, d, a, words[1], 21, 0x85845DD1);
            a = II(a, b, c, d, words[8], 6, 0x6FA87E4F);
            d = II(d, a, b, c, words[15], 10, 0xFE2CE6E0);
            c = II(c, d, a, b, words[6], 15, 0xA3014314);
            b = II(b, c, d, a, words[13], 21, 0x4E0811A1);
            a = II(a, b, c, d, words[4], 6, 0xF7537E82);
            d = II(d, a, b, c, words[11], 10, 0xBD3AF235);
            c = II(c, d, a, b, words[2], 15, 0x2AD7D2BB);
            b = II(b, c, d, a, words[9], 21, 0xEB86D391);

            a = a + aa;
            b = b + bb;
            c = c + cc;
            d = d + dd;
        }

        byte[] result = new byte[16];
        int[] states = {a, b, c, d};
        for (int i = 0; i < 4; i++) {
            result[i * 4] = (byte) (states[i] & 0xFF);
            result[i * 4 + 1] = (byte) ((states[i] >>> 8) & 0xFF);
            result[i * 4 + 2] = (byte) ((states[i] >>> 16) & 0xFF);
            result[i * 4 + 3] = (byte) ((states[i] >>> 24) & 0xFF);
        }

        return result;
    }

    private static int FF(int a, int b, int c, int d, int x, int s, int t) {
        return b + Integer.rotateLeft((a + (b & c | ~b & d) + x + t), s);
    }

    private static int GG(int a, int b, int c, int d, int x, int s, int t) {
        return b + Integer.rotateLeft((a + (b & d | c & ~d) + x + t), s);
    }

    private static int HH(int a, int b, int c, int d, int x, int s, int t) {
        return b + Integer.rotateLeft((a + (b ^ c ^ d) + x + t), s);
    }

    private static int II(int a, int b, int c, int d, int x, int s, int t) {
        return b + Integer.rotateLeft((a + (c ^ (b | ~d)) + x + t), s);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
