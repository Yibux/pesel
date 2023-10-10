import jdk.jfr.Description;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

class NullException extends Exception {
    public NullException(String errMess) {
        super(errMess);
    }
}

class NotNumbersOnlyException extends Exception {
    public NotNumbersOnlyException(String errMess) {
        super(errMess);
    }
}

class IncorrectLengthException extends Exception {
    public IncorrectLengthException(String errMess) {
        super(errMess);
    }
}

class IncorrectMonthException extends Exception {
    public IncorrectMonthException(String errMess) {
        super(errMess);
    }
}

class IncorrectDayException extends Exception {
    public IncorrectDayException(String errMess) {
        super(errMess);
    }
}


/**
 * Główna klasa aplikacji programu PESEL
 */
public class Main {

    /**
     * Indeks początkowy cyfry miesiąca w numerze PESEL.
     */
    public static final int MONTH_START_INDEX = 2;

    /**
     * Indeks końcowy cyfry miesiąca w numerze PESEL.
     */
    public static final int MONTH_END_INDEX = 4;

    /**
     * Maksymalna wartość cyfry miesiąca w numerze PESEL.
     */
    public static final int MAX_MONTH = 32;

    /**
     * Długość numeru PESEL.
     */
    public static final int PESEL_LENGTH = 11;

    /**
     * Metoda główna programu, która przyjmuje numer PESEL jako argument i dokonuje jego weryfikacji.
     *
     * @param args Argumenty wywołania programu (jedna linia zawierająca numer PESEL).
     */
    public static void main(String[] args) {

        System.out.println("Podaj pesel: ");
        Scanner scanner = new Scanner(System.in);
        try{
            //String pesel = scanner.nextLine();    //linijka do odkomentowania jezeli to nie test
            String pesel = args[0]; //jedynie do testów
            if (pesel.isEmpty())
                throw new NullException("Pesel jest pusty!");
            else if (!checkIfAllNumbers(pesel))
                throw new NotNumbersOnlyException("Pesel ma inne znaki niz cyfry!");
            else if (checkIf11Elements(pesel))
                throw new IncorrectLengthException("Twoj pesel nie ma 11 znakow");
            else if (!checkIfMonthValid(pesel))
                throw new IncorrectMonthException("Niepoprawny miesiac");

            int day = validateDay(pesel);

            if (day == -1)
                throw new IncorrectDayException("Niepoprawny dzien w miesiacu");

            String gender = checkGender(pesel);

            System.out.println("Jestes: " + gender + " . Twoja data urodzenia: " + calculateDateOfBirth(pesel));

        } catch (NullException | NotNumbersOnlyException | IncorrectLengthException | IncorrectMonthException | IncorrectDayException | ParseException e) {
            System.err.println(e.getMessage());
            scanner.close();
            throw new RuntimeException(e.getMessage());
        } finally {
            scanner.close();
        }


    }

    /**
     * Sprawdza, czy ciąg znaków zawiera tylko cyfry.
     * @param s Ciąg znaków do sprawdzenia.
     * @return `true`, jeśli ciąg znaków zawiera tylko cyfry; `false` w przeciwnym razie.
     */
    public static boolean checkIfAllNumbers(String s) {
        return s.matches("[0-9]+");
    }

    /**
     * Sprawdza, czy ciąg znaków ma dokładnie 11 znaków.
     * @param s Ciąg znaków do sprawdzenia.
     * @return `true`, jeśli ciąg znaków ma dokładnie 11 znaków; `false` w przeciwnym razie.
     */
    public static boolean checkIf11Elements(String s) {
        return s.length() != PESEL_LENGTH;
    }

    /**
     * Pobiera miesiąc z ciągu znaków reprezentującego pesel.
     * @param s Ciąg znaków pesel.
     * @return Miesiąc jako liczba całkowita.
     */
    public static int getMonth(String s) {
        return Integer.parseInt(s.substring(MONTH_START_INDEX, MONTH_END_INDEX));
    }

    /**
     * Pobiera rok z ciągu znaków reprezentującego pesel.
     * @param s Ciąg znaków pesel.
     * @return Rok jako liczba całkowita.
     */
    public static int getYear(String s) {
        return Integer.parseInt(s.substring(0, 2));
    }

    /**
     * Pobiera dzień z ciągu znaków reprezentującego pesel.
     * @param s Ciąg znaków pesel.
     * @return Dzień jako liczba całkowita.
     */
    public static int getDay(String s) {
        return Integer.parseInt(s.substring(4, 6));
    }

    /**
     * Sprawdza, czy miesiąc w peselu jest poprawny (1-12).
     * @param s Ciąg znaków pesel.
     * @return `true`, jeśli miesiąc jest poprawny; `false` w przeciwnym razie.
     */
    public static boolean checkIfMonthValid(String s) {
        return checkIfInBounds(1, getMonth(s), MAX_MONTH) && !checkIfInBounds(13, getMonth(s), 20);
    }

    /**
     * Sprawdza, który miesiąc reprezentowany jest w peselu.
     * @param s Ciąg znaków pesel.
     * @return Miesiąc jako liczba całkowita (1-12).
     */
    public static int checkWhichMonth(String s) {
        int month = getMonth(s);
        return month > 12 ? month - 20 : month;
    }

    /**
     * Oblicza rok na podstawie peselu.
     * @param s Ciąg znaków pesel.
     * @return Rok jako liczba całkowita.
     */
    public static int calculateYear(String s) {
        int year = getYear(s);
        int month = getMonth(s);
        return month > 12 ? year + 2000 : year + 1900;
    }

    /**
     * Sprawdza, czy liczba całkowita znajduje się w określonym zakresie.
     * @param start Początek zakresu (włącznie).
     * @param check Liczba do sprawdzenia.
     * @param end Koniec zakresu (włącznie).
     * @return `true`, jeśli liczba znajduje się w zakresie; `false` w przeciwnym razie.
     */
    public static boolean checkIfInBounds(int start, int check, int end) {
        return start <= check && check <= end;
    }

    /**
     * Sprawdza, ile dni ma luty w zależności od roku.
     * @param year Rok do sprawdzenia.
     * @return Liczba dni w lutym (28 lub 29).
     */
    public static int checkIfItFebruary(int year) {
        return year % 4 == 0 ? 29 : 28;
    }

    /**
     * Sprawdza, czy dzień w peselu jest poprawny dla danego miesiąca i roku.
     * @param s Ciąg znaków pesel.
     * @return Dzień jako liczba całkowita lub -1, jeśli dzień jest niepoprawny.
     */
    public static int validateDay(String s) {
        int year = getYear(s);
        int month = getMonth(s);
        int day = getDay(s);
        if (
                (month % 2 == 0 && !checkIfInBounds(1, day, 30))
                        || (month == 2 && !checkIfInBounds(1, day, checkIfItFebruary(year)))
                        || (month % 2 != 0 && !checkIfInBounds(1, day, 31))

        )
            return -1;
        return day;
    }

    /**
     * Sprawdza płeć na podstawie peselu.
     * @param s Ciąg znaków pesel.
     * @return Płeć ("Kobieta" lub "Mężczyzna").
     */
    public static String checkGender(String s) {
        return Integer.parseInt(s.substring(9, 10)) % 2 == 0 ? "Kobieta" : "Mężczyzna";
    }

    /**
     * Oblicza datę urodzenia na podstawie peselu i zwraca ją jako ciąg znaków w formacie "yyyy-MM-dd".
     * @param pesel Ciąg znaków pesel.
     * @return Data urodzenia jako ciąg znaków w formacie "yyyy-MM-dd".
     * @throws ParseException Jeśli nie można przekonwertować daty.
     * @throws IncorrectDayException Jeśli dzień jest niepoprawny.
     */
    public static String calculateDateOfBirth(String pesel) throws ParseException, IncorrectDayException {
        int year = calculateYear(pesel);
        int month = checkWhichMonth(pesel);
        int day = validateDay(pesel);

        if (day == -1) {
            throw new IncorrectDayException("Niepoprawny dzień w miesiącu");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dataString = String.format("%04d-%02d-%02d", year, month, day);
        Date date = sdf.parse(dataString);

        return sdf.format(date);
    }

}