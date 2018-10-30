package lesson2;

import com.sun.xml.internal.fastinfoset.util.CharArray;
import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     * <p>
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     * <p>
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     * <p>
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        List<Integer> prices = new ArrayList<>();
        try (FileReader reader = new FileReader(inputName)) {
            Scanner scan = new Scanner(reader);
            while (scan.hasNextLine()) {
                prices.add(Integer.parseInt(scan.nextLine()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка чтения");
        }

        Pair<Integer, Integer> maxPair = new Pair<>(1, 2);
        int maxPriceIndex = prices.size() - 1;
        int maxPrice = 0;
        for (int i = prices.size() - 2; i > 0; i--) {
            if (prices.get(maxPriceIndex) - prices.get(i) >= maxPrice) {
                maxPair = new Pair<>(i + 1, maxPriceIndex + 1);
                maxPrice = prices.get(maxPriceIndex) - prices.get(i);
            } else {
                if (prices.get(i) > prices.get(maxPriceIndex)) {
                    maxPriceIndex = i;
                }
            }
        }

        return maxPair;
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     * <p>
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 5
     * <p>
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 2 3
     * 8   4
     * 7 6 х
     * <p>
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     * <p>
     * 1 х 3
     * 8   4
     * 7 6 Х
     * <p>
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     * <p>
     * 1 Х 3
     * х   4
     * 7 6 Х
     * <p>
     * 1 Х 3
     * Х   4
     * х 6 Х
     * <p>
     * х Х 3
     * Х   4
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   х
     * Х 6 Х
     * <p>
     * Х Х 3
     * Х   Х
     * Х х Х
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     * <p>
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String first, String second) {
        Integer[][] lengthFinder = new Integer[first.length()][second.length()];
        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                lengthFinder[i][j] = 0;
            }
        }

        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                if (first.charAt(i) == (second.charAt(j))) {
                    if (i >= 1 && j >= 1) {
                        lengthFinder[i][j] = lengthFinder[i - 1][j - 1] + 1;
                    } else {
                        lengthFinder[i][j] = 1;
                    }
                }
            }
        }

        int maxMatch = 0;
        int maxIndex = 0;
        for (int i = 0; i < first.length(); i++) {
            for (int j = 0; j < second.length(); j++) {
                if (lengthFinder[i][j] > maxMatch) {
                    maxMatch = lengthFinder[i][j];
                    maxIndex = i;
                }
            }
        }

        return first.substring(maxIndex - maxMatch + 1, maxIndex + 1);
    }

    /**
     * Число простых чисел в интервале
     * Простая
     * <p>
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     * <p>
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */
    static public int calcPrimesNumber(int limit) {
        throw new NotImplementedError();
    }

    /**
     * Балда
     * Сложная
     * <p>
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     * <p>
     * И Т Ы Н
     * К Р А Н
     * А К В А
     * <p>
     * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     * <p>
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     * <p>
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     * <p>
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     **/
    static public Set<String> baldaSearcher(String inputName, Set<String> words) {

        Set<String> existWords = new HashSet<>();
        List<char[]> charsMatrix = new ArrayList<>();
        try (FileReader reader = new FileReader(inputName)) {
            Scanner scan = new Scanner(reader);
            while (scan.hasNextLine()) {
                String symbols = scan.nextLine().toLowerCase();
                if (!symbols.matches("\\S{2,}|[^A-zА-я ^ ?]|_|\\s{2,}|\\s$")) { //проверка на соответствие массива
                    charsMatrix.add(symbols.replaceAll("\\s*", "").toCharArray());
                } else {
                    throw new Exception("Некорректно введены данные");
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка чтения");
        }

        List<char[]> copyCharsMatrix = new ArrayList<>(charsMatrix.size());

        for (int i = 0; i < charsMatrix.size(); i++) {
            for (int j = 0; j < charsMatrix.get(i).length; j++) {
                for (String word : words) {
                    if (!existWords.contains(word)) {
                        if (charsMatrix.get(i)[j] == (word.toLowerCase().charAt(0))) {
                            if (word.length() == 1) {
                                existWords.add(word);
                            } else {
                                for (char[] chars : charsMatrix)
                                    copyCharsMatrix.add(chars.clone());
                                if (wordFinder(i, j, copyCharsMatrix, word.toLowerCase(), 1, false)) {
                                    existWords.add(word);
                                }
                                copyCharsMatrix.clear();
                            }
                        }
                    }
                }
            }
        }
        return existWords;
    }

    static private boolean wordFinder(int i, int j, List<char[]> charsMatrix, String word, int currentChar, boolean end) {
        if (currentChar == word.length()) {
            end = true;
        }
        if (!end && j + 1 < charsMatrix.get(i).length && charsMatrix.get(i)[j + 1] == word.charAt(currentChar)) {
            charsMatrix.get(i)[j] = '0';
            end = wordFinder(i, j + 1, charsMatrix, word, currentChar + 1, end);
        }
        if (!end && i + 1 < charsMatrix.size() && charsMatrix.get(i + 1)[j] == word.charAt(currentChar)) {
            charsMatrix.get(i)[j] = '0';
            end = wordFinder(i + 1, j, charsMatrix, word, currentChar + 1, end);
        }
        if (!end && j - 1 >= 0 && charsMatrix.get(i)[j - 1] == word.charAt(currentChar)) {
            charsMatrix.get(i)[j] = '0';
            end = wordFinder(i, j - 1, charsMatrix, word, currentChar + 1, end);
        }
        if (!end && i - 1 >= 0 && charsMatrix.get(i - 1)[j] == word.charAt(currentChar)) {
            charsMatrix.get(i)[j] = '0';
            end = wordFinder(i - 1, j, charsMatrix, word, currentChar + 1, end);
        }
        return end;
    }
}
