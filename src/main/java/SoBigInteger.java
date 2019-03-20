import javafx.util.Pair;

import java.util.Objects;
import java.util.stream.IntStream;

public class SoBigInteger implements Comparable<SoBigInteger> {
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
        return new StringBuilder(number).reverse().toString();
    }

    @Override
    public int compareTo(SoBigInteger other) {
        String first = this.number;
        String second = other.number;
        int res = first.length() - second.length();
        if (res < 0)
            return -1;
        if (res > 0)
            return 1;
        for (int i = Math.max(first.length(), second.length())-1 ; i >=0; i--) {
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


    private Pair<String, String> changeTo(SoBigInteger number, SoBigInteger other) { //Меняю значение строк местами для умножения и вычитания
        if (number.compareTo(other) < 0) {
            String newInt;
            String newInt2;
            newInt = this.number;
            newInt2 = other.number;
            this.number = newInt2;
            other.number = newInt;
        }
        return new Pair<>(this.number, other.number);
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
            this.number = new StringBuilder(number).reverse().toString();
        } else throw new NumberFormatException("Не является числом");
    }


    SoBigInteger sum(SoBigInteger other) {// Сложение
        StringBuilder result = new StringBuilder();
        final int[] counter = {0};
        StringBuilder first = new StringBuilder(this.number);
        StringBuilder second = new StringBuilder(other.number);
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
        Pair<String, String> pair = changeTo(this, other);
        String first = pair.getKey();
        String second = pair.getValue();
        StringBuilder f = new StringBuilder(first);
        StringBuilder s = new StringBuilder(second);
        Pair<StringBuilder, StringBuilder> p = appendZero(f, s);
        f = p.getKey();
        s = p.getValue();
        for (int i = 0; i < Math.max(f.length(), s.length()); i++) {
            n1 = (byte) (Byte.parseByte(Character.toString(f.charAt(i))) - counter);
            n2 = Byte.parseByte(Character.toString(s.charAt(i)));
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


    SoBigInteger mul(SoBigInteger other) { //Умножение
        StringBuilder forZero = new StringBuilder();
        SoBigInteger newResult2 = new SoBigInteger("0");
        byte n1;
        byte n2;
        Pair<String, String> pair = changeTo(this, other);
        this.number = pair.getKey();
        other.number = pair.getValue();
        StringBuilder second = new StringBuilder(other.number);
        StringBuilder first = new StringBuilder(this.number);
        if (first.charAt(first.length()-1) == '0' || second.charAt(second.length()-1) == '0')
            return new SoBigInteger("0");
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
        int res = this.compareTo(other);
        if (res < 0)
            return new SoBigInteger("0");
        if (res == 0)
            return new SoBigInteger("1");
        while (this.compareTo(other) >= 0) {
            this.number = this.sub(other).toString();
            divRes++;
        }

        return new SoBigInteger(Integer.toString(divRes));
    }


    SoBigInteger mod(SoBigInteger other) {
        String first = this.number;
        int res = this.compareTo(other);
        if (res < 0)
            return this;
        if (res == 0)
            return new SoBigInteger("0");
        SoBigInteger newResult = this.div(other);
        SoBigInteger newResult2 = newResult.mul(other);
        return new SoBigInteger(first).sub(newResult2);
    }
}



