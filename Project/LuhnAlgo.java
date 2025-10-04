import java.security.SecureRandom;

public class LuhnAlgo {

    private static final SecureRandom RANDOM = new SecureRandom();

    /** Return Luhn checksum (total % 10). 0 means valid. */
    public static int luhnChecksum(String digitsOnly) {
        if (digitsOnly == null || digitsOnly.isEmpty()) {
            throw new IllegalArgumentException("Input must be numeric and non-empty");
        }
        int total = 0;
        int len = digitsOnly.length();
        // Process from right to left
        for (int i = len - 1, pos = 0; i >= 0; i--, pos++) {
            int d = Character.digit(digitsOnly.charAt(i), 10);
            if (d < 0) throw new IllegalArgumentException("Non-digit character found");
            // double every second digit (pos starts at 0 for rightmost)
            if (pos % 2 == 1) {
                d = d * 2;
                if (d > 9) d -= 9;
            }
            total += d;
        }
        return total % 10;
    }

    /** Return true if the cardNumber passes the Luhn check. Non-digits are ignored. */
    public static boolean isLuhnValid(String cardNumber) {
        String clean = digitsOnly(cardNumber);
        if (clean.isEmpty()) return false;
        return luhnChecksum(clean) == 0;
    }

    /** Compute the single Luhn check digit for the provided numeric prefix. */
    public static char calculateCheckDigit(String prefix) {
        if (prefix == null || prefix.isEmpty() || !prefix.matches("\\d+")) {
            throw new IllegalArgumentException("Prefix must be non-empty and contain only digits");
        }
        String temp = prefix + "0";
        int checksum = luhnChecksum(temp);
        int checkDigit = (10 - checksum) % 10;
        return (char) ('0' + checkDigit);
    }

    /** Generate a card-like number for demo/testing (do NOT use for fraud). */
    public static String generateCardNumber(String prefix, int length) {
        if (prefix == null || !prefix.matches("\\d*")) {
            throw new IllegalArgumentException("Prefix must contain only digits (or be empty)");
        }
        if (length <= prefix.length()) {
            throw new IllegalArgumentException("Length must be greater than prefix length");
        }
        int numRandom = length - prefix.length() - 1; // leave space for final check digit
        StringBuilder sb = new StringBuilder(prefix);
        for (int i = 0; i < numRandom; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        char check = calculateCheckDigit(sb.toString());
        sb.append(check);
        return sb.toString();
    }

    /** Helper: strip non-digit characters */
    private static String digitsOnly(String s) {
        if (s == null) return "";
        StringBuilder b = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) b.append(c);
        }
        return b.toString();
    }

    // ---------- Example usage ----------
    public static void main(String[] args) {
        // Common sandbox/test numbers
        String visaTest = "4111 1111 1111 1111";
        String mcTest   = "5105 1051 0510 5100";
        String example  = "4539 1488 0343 6467";

        System.out.println(visaTest + " valid? " + isLuhnValid(visaTest));
        System.out.println(mcTest   + " valid? " + isLuhnValid(mcTest));
        System.out.println(example  + " valid? " + isLuhnValid(example));

        // Generate demo numbers
        System.out.println("Generated (Visa-like 16): " + generateCardNumber("4", 16));
        System.out.println("Generated (Mastercard-like 16): " + generateCardNumber("51", 16));
        System.out.println("Generated (Amex-like 15): " + generateCardNumber("3782", 15));

        // Compute check digit for a prefix
        String prefix = "453914880343646"; // example without last digit
        char check = calculateCheckDigit(prefix);
        System.out.println("Check digit for prefix " + prefix + " is " + check +
                           " -> full: " + prefix + check + " valid? " + isLuhnValid(prefix + check));
    }
}






/* Output will be different on executing the Java Program 
Execution 1:
4111 1111 1111 1111 valid? true
5105 1051 0510 5100 valid? true
4539 1488 0343 6467 valid? true
Generated (Visa-like 16): 4772855087546409
Generated (Mastercard-like 16): 5184933219587762
Generated (Amex-like 15): 378284962449975
Check digit for prefix 453914880343646 is 7 -> full: 4539148803436467 valid? true

Execution 2:
4111 1111 1111 1111 valid? true
5105 1051 0510 5100 valid? true
4539 1488 0343 6467 valid? true
Generated (Visa-like 16): 4978098598279580
Generated (Mastercard-like 16): 5133848603490229
Generated (Amex-like 15): 378241519492221
Check digit for prefix 453914880343646 is 7 -> full: 4539148803436467 valid? true

*/
