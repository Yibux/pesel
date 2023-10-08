import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    @DisplayName("Sprawdzenie peselu przy prawidlowych danych < 2000")
    public void testValidPESEL() throws IncorrectDayException, ParseException {

        String pesel = "92063012345";


        String expectedGender = "Kobieta";
        String expectedDateOfBirth = "1992-06-30";

        Main.main(new String[]{pesel});
        assertEquals(expectedGender, Main.checkGender(pesel));
        assertEquals(expectedDateOfBirth, Main.calculateDateOfBirth(pesel));
    }
    @Test
    @DisplayName("Sprawdzenie peselu przy prawidlowych danych > 2000")
    public void testValidPESEL2() throws IncorrectDayException, ParseException {

        String pesel = "02242105936";


        String expectedGender = "Mezczyzna";
        String expectedDateOfBirth = "2002-04-21";

        Main.main(new String[]{pesel});
        assertEquals(expectedGender, Main.checkGender(pesel));
        assertEquals(expectedDateOfBirth, Main.calculateDateOfBirth(pesel));
    }

    @Test
    @DisplayName("Test wyjątku NullException")
    public void testNullException() {
        String pesel = "";

        try {
            Main.main(new String[]{pesel});
            fail("Oczekiwano wyjątku RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Pesel jest pusty!", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test wyjątku NotNumbersOnlyException")
    public void testNotNumbersOnlyException() {
        String pesel = "123abc";

        try {
            Main.main(new String[]{pesel});
            fail("Oczekiwano wyjątku RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Pesel ma inne znaki niz cyfry!", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test wyjątku IncorrectLengthException")
    public void testIncorrectLengthException() {
        String pesel = "123456789";

        try {
            Main.main(new String[]{pesel});
            fail("Oczekiwano wyjątku RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Twoj pesel nie ma 11 znakow", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test wyjątku IncorrectMonthException")
    public void testIncorrectMonthException() {
        String pesel = "92003212345";

        try {
            Main.main(new String[]{pesel});
            fail("Oczekiwano wyjątku RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Niepoprawny miesiac", e.getMessage());
        }
    }

    @Test
    @DisplayName("Test wyjątku IncorrectDayException")
    public void testIncorrectDayException() {
        String pesel = "92063191345";

        try {
            Main.main(new String[]{pesel});
            fail("Oczekiwano wyjątku RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Niepoprawny dzien w miesiacu", e.getMessage());
        }
    }
}