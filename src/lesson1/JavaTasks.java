package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     * <p>
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) {
        List<Double> tempList = new ArrayList<>();
        try (FileReader reader = new FileReader(inputName)) {
            Scanner scan = new Scanner(reader);
            while (scan.hasNextLine()) {
                Double temp = Double.parseDouble(scan.nextLine());
                if (temp >= -273.0 && temp <= 500.0) {
                    tempList.add(temp);
                } else {
                    throw new IllegalArgumentException("Число не в диапазоне -273<n<500");
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Ошибка чтения");
        }

        Double[] tempArray = tempList.toArray(new Double[0]);

        Sorts.quickSort(tempArray, 0, tempArray.length - 1);

        try (FileWriter writer = new FileWriter(outputName)) {
            for (Double temp : tempArray) {
                writer.write(temp.toString() + "\n");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка записи");
        }
    }
    //Трудоемкость - O(log(n)*n)
    //Ресурсоёмкость - O(n)
    //где n - кол-во строк в файле

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */

    static public void sortSequence(String inputName, String outputName) {
        List<Integer> numList = new ArrayList<>();
        Map<Integer, Integer> mostFreq = new HashMap<>();
        int maxNum = 0;
        int maxFreq = 0;

        try (FileReader reader = new FileReader(inputName)) {
            Scanner scan = new Scanner(reader);
            while (scan.hasNextLine()) {
                Integer number = Integer.parseInt(scan.nextLine());
                if (number < 0) {
                    throw new IllegalArgumentException("Встретилось странное число!");
                }
                numList.add(number);
                if (number > maxNum) {
                    maxNum = number;
                }
                if (!mostFreq.containsKey(number)) {
                    mostFreq.put(number, 1);
                } else {
                    mostFreq.replace(number, mostFreq.get(number) + 1);
                }
                if (mostFreq.get(number) > maxFreq) {
                    maxFreq = mostFreq.get(number);
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка чтения");
        }

        int minNum = maxNum;
        for (Integer numInColl : mostFreq.keySet()) {
            if (mostFreq.get(numInColl).equals(maxFreq) && numInColl <= minNum) {
                minNum = numInColl;
            }
        }

        try (FileWriter writer = new FileWriter(outputName)) {
            for (Integer num : numList) {
                if (!num.equals(minNum))
                    writer.write(num.toString() + "\n");
            }
            for (int i = 0; i < mostFreq.get(minNum); i++) {
                writer.write(minNum + "\n");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка записи");
        }
    }
    //Трудоёмкость - O(n)
    //Ресурсоёмкость - O(n)
    //где n - кол-во входных символов

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
