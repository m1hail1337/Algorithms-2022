@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File
import java.lang.IllegalArgumentException

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
 * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
 *
 * Пример:
 *
 * 01:15:19 PM
 * 07:26:57 AM
 * 10:00:03 AM
 * 07:56:14 PM
 * 01:15:19 PM
 * 12:40:31 AM
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 12:40:31 AM
 * 07:26:57 AM
 * 10:00:03 AM
 * 01:15:19 PM
 * 01:15:19 PM
 * 07:56:14 PM
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortTimes(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val lines = File(inputName).readLines() //T(N) = O(N); R(N) = O(N)
    val times = mutableListOf<Pair<Int, String>>()
    val values = mutableListOf<Int>()

    fun countSeconds(time: String, isMorning: Boolean): Int { // T(N) = O(3)
        val parts = time.split(":")
        var result = 0
        for (part in parts) result = result * 60 + part.toInt()
        if (!isMorning) result += 12 * 3600
        if (parts[0] == "12") result -= 12 * 3600
        values += result
        return result
    }

    for (line in lines) {   // T(N) = O(N); R(N) = O(N)
        isCorrect(line)
        times += when (line.split(" ")[1]) {
            "AM" -> Pair(countSeconds(line.split(" ")[0], true), line.split(" ")[0])
            else -> Pair(countSeconds(line.split(" ")[0], false), line.split(" ")[0])
        }
    }
    val valuesArray = countingSort(values.toIntArray(), 86400) // T(N) = O(N) + O(N); R(N) = O(N) + O(N)
    for (element in valuesArray) {      // T(N) = O(N); R(N) = O(N)
        val timesMap = times.toMap()
        when {
            element > 12 * 3600 -> writer.write(timesMap[element] + " PM")
            else -> writer.write(timesMap[element] + " AM")
        }
        writer.newLine()
    }
    writer.close()
}
// Comments:
// (40): T(N) = O(N) - каждую строку записываем в List; R(N) = O(N) - создаем List из N элементов
// (44): T(N) = O(3) - всегда 3 эл-та в parts
// (54): T(N) = O(N) - перебираем каждую line; R(N) = O(N) - заполняем List N элементами
// (61.1): T(N) = O(N) - каждое значение добавляем в IntArray; R(N) = O(N) - создаем IntArray
// (61.2): T(N) = O(N) - среднее время работы алг. сортировки подсчетом; R(N) = O(N) - создаем массив с подсчетом
// (62): T(N) = O(N) - перебираем каждый element; R(N) = O(N) - создаем Map из N элементов
// Summary: T(N) = 5O(N) + O(3) = O(N); R(N) = 4O(N) + O(1) = O(N)

fun isCorrect(line: String) {
    try {
        val list = line.split(" ")
        if (list.size == 2) {
            if (list[1] == "AM" || list[1] == "PM") {
                val time = list[0].split(":")
                if (time.size == 3 && time[0].toInt() < 13 && time[1].toInt() < 60 && time[2].toInt() < 60 &&
                    time[0].length == 3 && time[1].length == 3 && time[2].length == 3
                )
                    return
            }
        }
    } catch (e: Exception) {
        throw IllegalArgumentException("Incorrect format.")
    }

}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */
fun sortTemperatures(inputName: String, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    val values = File(inputName).readLines().map { it.toDouble() }.toDoubleArray() //T(N) = 3O(N); R(N) = 3O(N)
    quickSort(values)                       //T(N) = O(Nlog2N); R(N) = O(1)
    for (value in values) {         //T(N) = O(N); R(N) = O(1)
        writer.write(value.toString())
        writer.newLine()
    }
    writer.close()
}
// Comments:
// (154): T(N) = 3O(N) тк сначала мы каждую строку записываем в List -> каждую строку .toDouble() -> каждое значение добавляем в DoubleArray
// (154): R(N) = 3O(N) тк мы создаем List со строками -> создаем List со значениями -> создаем Array со значениями
// (155): T(N) = O(Nlog2N) - среднее время работы алг. быстрой сортировки; R(N) = O(1) - сортировка на месте
// (156): T(N) = O(N) - перебор всех значений (линейное время); R(N) = O(1) - ничего не создаем
// Summary: T(N) = 3O(N) + O(Nlog2N) + O(N) = O(Nlog2N); R(N) = 3O(N) + O(1) + O(1) = O(N)

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    TODO()
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    TODO()
}

