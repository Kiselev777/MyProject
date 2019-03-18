import javafx.util.Pair;

import java.util.Objects;
import java.util.stream.IntStream;

public class SoBigInteger {
    private String number;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoBigInteger that = (SoBigInteger) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return number;
    }

    private int compareTo(String s) {
        if (this.number.length() < s.length())
            return -1;
        else if (this.number.length() > s.length())
            return 1;
        return 0;
    }


    private static Pair<String, String> changeTo(String f, String s) { //Меняю значение строк местами для умножения и вычитания
        if (new SoBigInteger(f).com(new SoBigInteger(s)) == -1) {
            String newInt;
            String newInt2;
            newInt = f;
            newInt2 = s;
            f = newInt2;
            s = newInt;
        }
        Pair<String, String> p = new Pair<>(f, s);
        return p;
    }


    private static Pair<StringBuilder, StringBuilder> appendZero(StringBuilder first, StringBuilder second) {
        if (first.length() < second.length()) {
            while (first.length() != second.length())
                first.append("0");
        } else if (second.length() < first.length()) {
            while (first.length() != second.length())
                second.append("0");
        }
        return new Pair<>(first, second);
    }


    SoBigInteger(String number) { //Конструктор,которпый проверяет строку на то,что все эелемнты строки-числа.
        number = number.replaceFirst("^0+(?!$)", "");
        if (number.matches("[1-9]\\d*") || number.equals("0")) {
            this.number = number;
        } else throw new NumberFormatException("Не является числом");
    }


    SoBigInteger sum(SoBigInteger other) {// Сложение
        StringBuilder result = new StringBuilder();
        final int[] counter = {0};
        StringBuilder first = new StringBuilder(this.number).reverse();
        StringBuilder second = new StringBuilder(other.number).reverse();
        //Переворачиваю строку для удобства сложения,чтобы идти в цикле с 1 индекса
        Pair<StringBuilder, StringBuilder> pair = appendZero(first, second);
        first = pair.getKey();
        second = pair.getValue();
        //заполняю нулями строку,которая меньше
        StringBuilder finalFirst = first;
        StringBuilder finalSecond = second;
        IntStream.range(0, Math.max(first.length(), second.length())).forEach(i -> {
            byte n1 = Byte.parseByte(Character.toString(finalFirst.charAt(i)));
            byte n2 = Byte.parseByte(Character.toString(finalSecond.charAt(i)));
            if (n1 + n2 + counter[0] < 10) {
                result.append(n1 + n2 + counter[0]);
                counter[0] = 0;
            }
            if (n1 + n2 + counter[0] >= 10) {
                result.append(((n1 + n2) % 10 + counter[0]) % 10);
                counter[0] = 1;
                if (i == Math.max(finalFirst.length(), finalSecond.length()) - 1)
                    result.append(counter[0]);
            }
        });
        return new SoBigInteger(result.reverse().toString());
    }


    SoBigInteger sub(SoBigInteger other) { //Вычитание
        StringBuilder result = new StringBuilder();
        int counter = 0;
        byte n1;
        byte n2;
        //переворачиваю строку для удобства вычитания
        Pair<String, String> pair = changeTo(this.number, other.number);
        this.number = pair.getKey();
        other.number = pair.getValue();
        StringBuilder second = new StringBuilder(other.number);
        StringBuilder first = new StringBuilder(this.number);
        first.reverse();
        second.reverse();
        Pair<StringBuilder, StringBuilder> p = appendZero(first, second);
        first = p.getKey();
        second = p.getValue();
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
        int res = new SoBigInteger(first).compareTo(second);
        //Возвращает 1,если перво число больше второго
        //Возвращает -1,если первое число меньше первого
        if (res == 0) {
            for (int i = 0; i < Math.max(first.length(), second.length()); i++) {
                // Прохожусь по каждому элементу числа,если длины числа равены
                byte byte1 = Byte.parseByte(Character.toString(first.charAt(i)));
                byte byte2 = Byte.parseByte(Character.toString(second.charAt(i)));
                if (byte1 > byte2) {
                    return 1;
                } else if (byte1 < byte2) {
                    return -1;
                }
            }
            return res;
        }
        return res;
    }


    SoBigInteger mul(SoBigInteger other) { //Умножение
        StringBuilder forZero = new StringBuilder();
        SoBigInteger newResult2 = new SoBigInteger("0");
        byte n1;
        byte n2;
        Pair<String, String> pair = changeTo(this.number, other.number);
        this.number = pair.getKey();
        other.number = pair.getValue();
        StringBuilder second = new StringBuilder(other.number);
        StringBuilder first = new StringBuilder(this.number);
        if (first.charAt(0) == '0' || second.charAt(0) == '0')
            return new SoBigInteger("0");
        first.reverse();
        second.reverse();
        for (int i = 0; i < second.length(); i++) {
            StringBuilder result = new StringBuilder();
            int counter = 0;
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
        if (first.charAt(0) == '0' || second.charAt(0) == '0')
            return this;
        int res = this.com(other);
        if (res == -1)
            return new SoBigInteger("0");
        if (res == 0)
            return new SoBigInteger("1");
        while (this.com(other) >= 0) {
            this.number = this.sub(other).toString();
            divRes++;
        }

        return new SoBigInteger(Integer.toString(divRes));
    }


    SoBigInteger mod(SoBigInteger other) {
        String first = this.number;
        int res = this.com(other);
        if (res == -1)
            return this;
        if (res == 0)
            return new SoBigInteger("0");
        SoBigInteger newResult = this.div(other);
        SoBigInteger newResult2 = newResult.mul(other);
        return new SoBigInteger(first).sub(newResult2);
    }
}

