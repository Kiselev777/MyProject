import java.util.Scanner;
import java.util.stream.IntStream;

public class SoBigInteger {
    private String number;
    private static int counter; // счётчик перехода за разряд
    private static StringBuilder result;

    @Override
    public String toString() {
        return number;
    }

    public static void main(String[] args) {
        SoBigInteger result = new SoBigInteger("1000");
        Scanner sc = new Scanner(System.in);
        System.out.println(result.div(new SoBigInteger("10")));
    }


    SoBigInteger(String number) { //Конструктор,которпый проверяет строку на то,что все эелемнты строки-числа.

        if (number.matches("\\d*")) {
            this.number = number;
        } else throw new NumberFormatException("Не является числом");
    }


    SoBigInteger sum(SoBigInteger other) {// Сложение
        result = new StringBuilder();
        counter = 0;
        StringBuilder first = new StringBuilder(this.number).reverse();
        StringBuilder second = new StringBuilder(other.number).reverse();
        //Переворачиваю строку для удобства сложения,чтобы идти в цикле с 1 индекса

        if (first.length() < second.length()) {
            while (first.length() != second.length())
                first.append("0");
        } else if (second.length() < first.length()) {
            while (first.length() != second.length())
                second.append("0");
        }
        //заполняю нулями строку,которая меньше

        IntStream.range(0, Math.max(first.length(), second.length())).forEach(i -> {
            byte n1 = Byte.parseByte(Character.toString(first.charAt(i)));
            byte n2 = Byte.parseByte(Character.toString(second.charAt(i)));
            if (n1 + n2 + counter < 10) {
                result.append(n1 + n2 + counter);
                counter = 0;
            }
            if (n1 + n2 + counter >= 10) {
                result.append(((n1 + n2) % 10 + counter) % 10);
                counter = 1;
                if (i == Math.max(first.length(), second.length()) - 1)
                    result.append(counter);
            }
        });
        return new SoBigInteger(result.reverse().toString());
    }


    SoBigInteger sub(SoBigInteger other) { //Вычитание
        result = new StringBuilder();
        counter = 0;
        byte n1;
        byte n2;
        //переворачиваю строку для удобства вычитания
        StringBuilder second = new StringBuilder(other.number);
        StringBuilder first = new StringBuilder(this.number);
        if (new SoBigInteger(first.toString()).com(new SoBigInteger(second.toString())) == -1) {
            StringBuilder newInt = new StringBuilder();
            StringBuilder newInt2 = new StringBuilder();
            newInt = first;
            newInt2 = second;
            first = newInt2;
            second = newInt;
        }
        first.reverse();
        second.reverse();
        if (first.length() < second.length()) {
            while (first.length() != second.length())
                first.append("0");
        } else if (second.length() < first.length()) {
            while (first.length() != second.length())
                second.append("0");
        }


        for (int i = 0; i < Math.max(first.length(), second.length()); i++) {
            n1 = (byte) (Byte.parseByte(Character.toString(first.charAt(i))) - counter);
            n2 = Byte.parseByte(Character.toString(second.charAt(i)));
            if (n1 - n2 >= 0) {
                result.append(n1 - n2);
                counter = 0;
            }
            if (n1 - n2 < 0) {
                result.append(10 + n1 - n2);
                counter = 1;
            }
        }

        counter = 0;
        result.reverse();
        for (int i = 0; i < result.length(); i++) {
            if (result.charAt(i) == '0')
                counter++;
            else break;
        }
        return new SoBigInteger(result.toString().substring(counter));
    }


    int com(SoBigInteger other) { //Сравнение
        String first = this.number;
        String second = other.number;
        if (first.length() > second.length())
            return 1;
        //Возвращает 1,если перво число больше второго
        if (first.length() < second.length())
            return -1;
        //Возвращает -1,если первое число меньше первого

        for (int i = 0; i < Math.max(first.length(), second.length()); i++) {
            byte byte1 = Byte.parseByte(Character.toString(first.charAt(i)));
            byte byte2 = Byte.parseByte(Character.toString(second.charAt(i)));
            if (byte1 > byte2) {
                return 1;
            } else if (byte1 < byte2) {
                return -1;
            }
        }

        // Прохожусь по каждому элементу числа,если длины числа равены
        return 0;

        //Возвращает 0,если числа равны.Нет смысла нагружать метод,используя в нём вычитание.
    }


    SoBigInteger mul(SoBigInteger other) { //Умножение
        StringBuilder forZero = new StringBuilder();
        SoBigInteger newResult2 = new SoBigInteger("");
        byte n1;
        byte n2;
        StringBuilder second = new StringBuilder(other.number);
        StringBuilder first = new StringBuilder(this.number);
        if (new SoBigInteger(first.toString()).com(new SoBigInteger(second.toString())) == -1) {
            StringBuilder newInt = new StringBuilder();
            StringBuilder newInt2 = new StringBuilder();
            newInt = first;
            newInt2 = second;
            first = newInt2;
            second = newInt;
        }
        first.reverse();
        second.reverse();

        for (int i = 0; i < second.length(); i++) {
            result = new StringBuilder();
            counter = 0;
            n1 = Byte.parseByte(Character.toString(second.charAt(i)));
            for (int j = 0; j < first.length(); j++) {
                n2 = Byte.parseByte(Character.toString(first.charAt(j)));
                if (n1 * n2 + counter < 10) {
                    result.append((n1 * n2 + counter) % 10);
                    counter = 0;
                }
                if (n1 * n2 + counter >= 10) {
                    result.append(((n1 * n2) % 10 + counter) % 10);
                    counter = (n1 * n2 + counter) / 10;
                    if (j == first.length() - 1) {
                        result.append(counter);
                    }
                }
            }
            counter = 0;
            SoBigInteger res = new SoBigInteger(new StringBuilder(forZero + result.toString()).reverse().toString());
            newResult2 = newResult2.sum(res);
            forZero.append("0");

        }
        return new SoBigInteger(newResult2.toString());
    }

    SoBigInteger div(SoBigInteger other) {//Деление
        int divRes = 0;
        String second = other.number;
        String first = this.number;
        if (new SoBigInteger(first).com(new SoBigInteger(second)) == -1)
            return new SoBigInteger("0");
        if (new SoBigInteger(first).com(new SoBigInteger(second)) == 0)
            return new SoBigInteger("1");
        while (new SoBigInteger(first).com(new SoBigInteger(second)) >= 0) {
            first = (new SoBigInteger(first).sub(new SoBigInteger(second))).toString();
            divRes++;
        }

        return new SoBigInteger(Integer.toString(divRes));
    }


    SoBigInteger mod(SoBigInteger other) {
        String second = other.number;
        String first = this.number;
        if (new SoBigInteger(first).com(new SoBigInteger(second)) == -1)
            return new SoBigInteger(first);
        if (new SoBigInteger(first).com(new SoBigInteger(second)) == 0)
            return new SoBigInteger("0");
        String newResult = String.valueOf((new SoBigInteger(first).div(new SoBigInteger(second))));
        String newResult2 = String.valueOf(new SoBigInteger(newResult).mul(new SoBigInteger(second)));
        return new SoBigInteger(first).sub(new SoBigInteger(newResult2));
    }
}

