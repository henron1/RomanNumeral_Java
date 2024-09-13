import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class RomanNumeralHelperTest {

    @Test
    public void testConvertToRoman_ValidNumbers() {
        // Test basic single-digit numbers
        assertEquals("I", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(1));
        assertEquals("IV", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(4));
        assertEquals("V", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(5));
        assertEquals("IX", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(9));

        // Test tens
        assertEquals("X", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(10));
        assertEquals("XL", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(40));
        assertEquals("L", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(50));
        assertEquals("XC", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(90));

        // Test hundreds
        assertEquals("C", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(100));
        assertEquals("CD", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(400));
        assertEquals("D", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(500));
        assertEquals("CM", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(900));

        // Test thousands
        assertEquals("M", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(1000));
        assertEquals("MM", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(2000));
        assertEquals("MMM", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(3000));

        // Test a mix of thousands, hundreds, tens, and units
        assertEquals("MCMXCIV", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(1994));
        assertEquals("MMXXIV", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(2024));
        assertEquals("MMMCMXC", RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(3990));
    }

    @Test
    public void testConvertToRoman_InvalidNumbers() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(0);
        });
        assertEquals("Input must be between 1 and 3999", exception.getMessage());
        exception = assertThrows(IllegalArgumentException.class, () -> {
            RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(-1);
        });
        assertEquals("Input must be between 1 and 3999", exception.getMessage());
        exception = assertThrows(IllegalArgumentException.class, () -> {
            RomanNumeralConverter.RomanNumeralHandler.RomanNumeralHelper.convertToRoman(4000);
        });
        assertEquals("Input must be between 1 and 3999", exception.getMessage());
    }
}
