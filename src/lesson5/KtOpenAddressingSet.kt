package lesson5

import java.util.NoSuchElementException


/**
 * Множество(таблица) с открытой адресацией на 2^bits элементов без возможности роста.
 */
class KtOpenAddressingSet<T : Any>(private val bits: Int) : AbstractMutableSet<T>() {

    init {
        require(bits in 2..31)
    }

    private val capacity = 1 shl bits

    private val storage = Array<Any?>(capacity) { null }

    override var size: Int = 0

    /**
     * Индекс в таблице, начиная с которого следует искать данный элемент
     */
    private fun T.startingIndex(): Int {
        return hashCode() and (0x7FFFFFFF shr (31 - bits))
    }

    /**
     * Проверка, входит ли данный элемент в таблицу
     */
    override fun contains(element: T): Boolean {
        var index = element.startingIndex()
        var current = storage[index]
        while (current != null) {
            if (current == element) {
                return true
            }
            index = (index + 1) % capacity
            current = storage[index]
        }
        return false
    }

    /**
     * Добавление элемента в таблицу.
     *
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     *
     * Бросает исключение (IllegalStateException) в случае переполнения таблицы.
     * Обычно Set не предполагает ограничения на размер и подобных контрактов,
     * но в данном случае это было введено для упрощения кода.
     */
    override fun add(element: T): Boolean {
        val startingIndex = element.startingIndex()
        var index = startingIndex
        var current = storage[index]
        while (current != null && current != "DD") {
            if (current == element) {
                return false
            }
            index = (index + 1) % capacity
            check(index != startingIndex) { "Table is full" }
            current = storage[index]
        }
        storage[index] = element
        size++
        return true
    }


    /**
     * Удаление элемента из таблицы
     *
     * Если элемент есть в таблице, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     *
     * Спецификация: [java.util.Set.remove] (Ctrl+Click по remove)
     *
     * Средняя
     */
    override fun remove(element: T): Boolean {
        val startingIndex = element.startingIndex()
        var index = startingIndex
        var current = storage[index]
        while (current != null) {                   //T(N) = O(1/(1 - A)), где A = size/capacity
            if (current == element) {
                storage[index] = "DD"
                size--
                return true
            }
            index = (index + 1) % capacity
            current = storage[index]
        }
        return false
    }
//Summary: T(N) = O(1/(1 - A)) - тк. трудоемкость операции зависит от коэф. заполнения; R(N) = O(1) - ничего не создаем.
    /**
     * Создание итератора для обхода таблицы
     *
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Средняя (сложная, если поддержан и remove тоже)
     */
    override fun iterator(): MutableIterator<T> =
        OpenAddressingSetIterator()

    inner class OpenAddressingSetIterator internal constructor() : MutableIterator<T> {

        private var counter = 0
        private var nextIndex = 0
        private var removeIndex = -1
        private val startSize = size


        override fun hasNext(): Boolean = counter < startSize       //T(N) = O(1); R(N) = O(1)

        override fun next(): T {
            if (hasNext()) {
                while (nextIndex < capacity && (storage[nextIndex] == null) || storage[nextIndex] == "DD") nextIndex++  //T(N) = O(1/(1 - A)), где A = size/capacity
                removeIndex = nextIndex
                counter++
                nextIndex++
                return storage[removeIndex] as T
            } else throw NoSuchElementException()
        }

        override fun remove() {     //T(N) = O(1); R(N) = O(1)
            if (removeIndex == -1)
                throw IllegalStateException()
            remove(storage[removeIndex] as T)
        }
    }
//(124): T(N) = O(1/(1 - A)) - тк. трудоемкость операции зависит от коэф. заполнения; R(N) = O(1) - ничего не создаем.
//(120, 132): T(N) = O(1); R(N) = O(1) - ничего не создаем, код без циклов
//Summary: T(N) = O(1/(1 - A)) - трудоемкость итератора обуславливается реализацией next(); R(N) = O(1) - ничего не создаем.
}
