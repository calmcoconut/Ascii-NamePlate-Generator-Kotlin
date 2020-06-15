import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class AsciiResourceFromFile {
    private val absResourcePath: String = "C:/Users/Alejandro/Projects/_HyperSkill/asciiNameGen/src"
    private val smallAsciiLetter = hashMapOf<String, List<String>>(" " to arrayListOf<String>(" ".repeat(5)))
    private val romanAsciiLetter = hashMapOf<String, List<String>>(" " to arrayListOf<String>(" ".repeat(10)))
    val romanMaxColumnSize = 10
    val smallMaxColumnSize = 3
    var maxWidth = 0
    var minWidth = 0
    var isMaxWidthName = true

    init {
        mapSmall()
        mapRoman()
    }

    fun getWordSingleLine(word: String, isRoman: Boolean): String {
        var str: String = ""
        var index = 0
        var maxSize = 900
        var isLengthAccounted = false
        var totalLength = 0
        while (index < maxSize) {
            word.forEach {
                val list = getLetterRaw(it.toString(), isRoman)
                if (maxSize == 900 || list.size > maxSize) {
                    maxSize = list.size
                }
                if (list.size < maxSize) {
                    str += list.get(0)
                    if (!isLengthAccounted && index == 0) {
                        totalLength += list.get(0).length
                        isLengthAccounted = true
                    }
                } else {
                    str += list.get(index)
                    if (!isLengthAccounted && index == 0) {
                        totalLength += list.get(index).length
                        isLengthAccounted = true
                    }
                }
                isLengthAccounted = false
            }
            str += "\n"
            index++
        }
        if (maxWidth < totalLength) {
            minWidth = maxWidth
            maxWidth = totalLength
            if (isRoman) {
                this.isMaxWidthName = true
            }
            else {
                this.isMaxWidthName = false
            }
        }
        else {
            minWidth = totalLength
        }
        return str
    }

    fun getLetterRaw(letter: String, isRoman: Boolean): List<String> {
        val map = if (isRoman) romanAsciiLetter else smallAsciiLetter
        return map.get(letter)!!
    }

    fun getLetterString(letter: String, isRoman: Boolean): String {
        val map = if (isRoman) romanAsciiLetter else smallAsciiLetter
        var ascii: String = ""
        map.get(letter)?.forEach() {
            ascii += it + "\n"
        }
        return ascii
    }


    private fun mapSmall() {
        val smallFilePath = absResourcePath + "/medium.txt"
        val scanner = Scanner(File(smallFilePath))
        readResourceFile(scanner, smallAsciiLetter)
        scanner.close()
    }

    private fun mapRoman() {
        val romanFilePath = absResourcePath + "/roman.txt"
        val scanner = Scanner(File(romanFilePath))
        readResourceFile(scanner, romanAsciiLetter)
        scanner.close()
    }

    private fun readResourceFile(scanner: Scanner, map: HashMap<String, List<String>>) {

        // 1. grab # of columns needed.
        val numberOfColumns: Int = scanner.nextInt()
        // 2. grab # of entries
        val numberOfEntries: Int = scanner.nextInt()
        // 3. start inputting into hashMap
        scanner.nextLine()
        var i = 0
        // a. first value will be key
        while (i < numberOfEntries) {
            var key: String
            var width: Int
            var line: String = scanner.nextLine()
            key = line.get(0).toString()
            width = line.substringAfter(" ").toInt()
            var list: MutableList<String> = ArrayList<String>(numberOfColumns)
            list = recursiveMapper(scanner, list, numberOfColumns, 0)
            map.put(key, list)

            i++
        }
    }

    private fun recursiveMapper(
        scanner: Scanner,
        list: MutableList<String>,
        numberOfColumns: Int,
        current: Int
    ): MutableList<String> {

        if (numberOfColumns == current) {
        } else {
            val row: String = scanner.nextLine()
            list.add(current, row)
            recursiveMapper(scanner, list, numberOfColumns, current + 1)
        }
        return list
    }

    override fun toString(): String {
        var str: String = ""
        smallAsciiLetter.forEach() {
            str += it.key + "\n"
            it.value.forEach() {
                str += it + "\n"
            }
        }
        romanAsciiLetter.forEach() {
            str += it.key + "\n"
            it.value.forEach() {
                str += it + "\n"
            }
        }
        return str
    }
}