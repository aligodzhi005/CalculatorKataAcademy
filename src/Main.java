import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;


public class Main {

    public static final String[] rimNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    public static final String[] mathSymbols = {"+", "-", "*", "/"};

    //флаг показывающий какие числа ввели в терминале (true - римские, false - арабские)
    public static boolean isRimNum = false;

    //treeMap, необходимо для правильного перевода арабских чисел в римские
    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

    //метод, который переводит арабское число в римское
    public final static String toRoman(int number) {
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }

    //метод, проверяющий есть ли элемент в массиве строк
    public static boolean containsInArray(String[] array, String elem) {
        for(int i = 0; i < array.length; i++) {
            if (array[i].equals(elem)) {
                return true;
            }
        }
        return false;
    }

    //метод для поиска индекса данного элемента в массиве строк
    public static int indexInArray(String[] array, String elem) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(elem))
                return i;
        }
        return -1;
    }

    //проверка на то, чтобы подавали числа из диапозона [1, 10]
    public static void parseResult(int tmp) throws Exception {
        if (tmp > 10 || tmp < 1)
            throw new IOException();
    }

    //делю ввод по пробелам и проверяю ввод на правильность
    static String[] splitAndValidate(String input) throws Exception{
        String[] splitInput = input.split(" ");
        if (splitInput.length != 3) {
            throw new IOException();
        }
        //сперва надо проверить на римские числа
        isRimNum = containsInArray(rimNumbers, splitInput[0]);
        if (isRimNum == true)
            isRimNum = containsInArray(rimNumbers, splitInput[2]);
        //потом надо проверить на арабские числа так как Integer.parseInt возвращает исключение в случае провала
        if (isRimNum == false) {
            int tmp = Integer.parseInt(splitInput[0]);
            parseResult(tmp);
            tmp = Integer.parseInt(splitInput[2]);
            parseResult(tmp);
        }
        //проверяю валидность мат. символа
        if (!containsInArray(mathSymbols, splitInput[1])) {
            throw new IOException();
        }
        return splitInput;
    }

    //метод для проведения расчёты
    public static int mathCalc(int[] elements, String mathSymbol){
        int num = indexInArray(mathSymbols, mathSymbol);
        int res = 0;
        switch(num) {
            case(0):// +
                res =  elements[0] + elements[1];
                break;
            case(1):// -
                res = elements[0] - elements[1];
                break;
            case(2): // *
                res = elements[0] * elements[1];
                break;
            case(3): // /
                res = elements[0] / elements[1];
                break;
        }
        return res;
    }

    //метод, который считает римские числа и возвращает результат в римском виде
    public static String calcRim(String[] splitInput) throws Exception{
        int result = 0;
        int[] elements = new int[2];
        elements[0] = indexInArray(rimNumbers, splitInput[0]) + 1;
        elements[1] = indexInArray(rimNumbers, splitInput[2]) + 1;
        result = mathCalc(elements, splitInput[1]);

        if (result < 1)
            throw new IOException();

        return toRoman(result);
    }

    public static String calcArab(String[] splitInput) throws Exception {
        int result = 0;
        int[] elements = new int[2];
        elements[0] = Integer.parseInt(splitInput[0]);
        elements[1] = Integer.parseInt(splitInput[2]);
        result = mathCalc(elements, splitInput[1]);

        return String.valueOf(result);
    }

    public static String calc(String input) throws Exception{
        String[] splitedInput = splitAndValidate(input);
        String result;
        if (isRimNum == true) {
            result = calcRim(splitedInput);
        } else {
            result = calcArab(splitedInput);
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;


        while(true) {
            input = scanner.nextLine();
            try {
                System.out.println(calc(input));
            } catch (Exception e) {
                System.out.println("throws Exception");
                break;
            }
        }
    }
}
