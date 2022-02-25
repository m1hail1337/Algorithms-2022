package lesson1

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Tag
import kotlin.test.Test
import java.util.*
import kotlin.test.assertTrue

class SortsTestKt {
    private val r = Random(Calendar.getInstance().timeInMillis)

    private fun assertSorted(arr: DoubleArray, prefix: String) {
        for (i in 0 until arr.size - 1) {
            assertTrue(
                arr[i] <= arr[i + 1],
                prefix + " ERROR: i = " + i + " a[i] = " + arr[i] + " a[i+1] = " + arr[i + 1]
            )
        }
    }

    private fun <T : Comparable<T>> assertSorted(arr: List<T>, prefix: String) {
        for (i in 0 until arr.size - 1) {
            assertTrue(
                arr[i] <= arr[i + 1],
                prefix + " ERROR: i = " + i + " a[i] = " + arr[i] + " a[i+1] = " + arr[i + 1]
            )
        }
    }

    @Test
    @Tag("Example")
    fun insertionSort() {
        val arr = mutableListOf(3, 7, 5, 9, 1, 6, 19, 13)
        insertionSort(arr)
        assertSorted(arr, "INSERTION SORT")
    }

    @Test
    @Tag("Example")
    fun insertionSortStrings() {
        val arr = mutableListOf("beta", "omega", "alpha", "", "!!!", "teta", "O")
        insertionSort(arr)
        assertSorted(arr, "INSERTION SORT")
    }

    @Test
    @Tag("Example")
    fun mergeSort() {
        val arr = doubleArrayOf(3.1, 7.2, 5.3, 9.4, 1.5, 6.6, 19.7, 13.8)
        mergeSort(arr)
        assertSorted(arr, "MERGE SORT")
    }

    @Test
    @Tag("Example")
    fun longInsertionSort() {
        val length = 65536
        val arr = MutableList(length) { r.nextInt() }
        insertionSort(arr)
        assertSorted(arr, "INSERTION SORT LONG")
    }

    @Test
    @Tag("Example")
    fun longHeapSort() {
        val length = 65536
        val arr = DoubleArray(length)
        for (i in 0 until length) {
            arr[i] = r.nextDouble()
        }
        heapSort(arr)
        assertSorted(arr, "HEAP SORT LONG")
    }

    @Test
    @Tag("Example")
    fun quickSort() {
        val arr = doubleArrayOf(3.1, 7.2, 5.3, 9.4, 1.5, 6.6, 19.7, 13.8)
        quickSort(arr)
        assertSorted(arr, "QUICK SORT")
    }

    @Test
    @Tag("Example")
    fun longQuickSort() {
        val length = 65536
        val arr = DoubleArray(length)
        for (i in 0 until length) {
            arr[i] = r.nextDouble()
        }
        quickSort(arr)
        assertSorted(arr, "QUICK SORT LONG")
    }

    @Test
    @Tag("Example")
    fun longLibrarySortForImmutable() {
        val length = 65536
        val arr = IntArray(length)
        for (i in 0 until length) {
            arr[i] = r.nextInt()
        }
        val list = arr.toList()
        val sortedList = librarySortForImmutable(list)
        assertSorted(sortedList, "LIBRARY SORT FOR IMMUTABLE")
    }

    @Test
    @Tag("Example")
    fun longLibrarySortForMutable() {
        val length = 65536
        val arr = IntArray(length)
        for (i in 0 until length) {
            arr[i] = r.nextInt()
        }
        val list = arr.toMutableList()
        librarySortForMutable(list)
        assertSorted(list, "LIBRARY SORT FOR MUTABLE")
    }
}