class AsciiMaker constructor(val name: String, val status: String) {

    private val normalColumnSize = 15
    private val ascii = AsciiResourceFromFile()
    private var asciiName: String = ""
    private var asciiStatus: String = ""
    private var maxSize: Int = 0
    private var minSize:Int = 0
    private var isNameLonger = true

    init {
        printAscii()
    }

    fun printAscii() {
        getAsciiWordStrings()
        setMaxSize()

        if (isNameLonger) {
            this.asciiName = setWiderWordBorder(this.asciiName)
            this.asciiStatus = setPaddingSmallerValue(this.asciiStatus)
        } else {
            this.asciiStatus = setWiderWordBorder((this.asciiStatus))
            this.asciiName = setPaddingSmallerValue(this.asciiName)
        }
        println("8".repeat(this.maxSize + 8))
//        println("88" + " ".repeat(maxSize + 3) + "88") // for an extra line of 88's border
        print(this.asciiName)
        print(this.asciiStatus)
        println("8".repeat(this.maxSize + 8))
    }

    private fun getAsciiWordStrings() {
        asciiName = ascii.getWordSingleLine(name, true)
        asciiStatus = ascii.getWordSingleLine(status, false)
    }

    private fun setMaxSize() {
        this.maxSize = ascii.maxWidth
        this.minSize = ascii.minWidth
        this.isNameLonger = ascii.isMaxWidthName
    }

    private fun setPaddingSmallerValue(word: String): String {
        var newStr = ""
        val spaceInLeft = (maxSize - minSize) / 2
        val spaceInRight = spaceInLeft + (maxSize - minSize) % 2
        var rowIndex = 0
        while (rowIndex < word.length) {
            newStr += "88  " + " ".repeat(spaceInLeft) + word.substring(rowIndex, rowIndex + minSize + 1)
                .replace(" \n", (" ".repeat(spaceInRight) + "   88\n"))
            rowIndex += minSize + 1
        }
        return newStr
    }

    private fun setWiderWordBorder(word: String): String {
        var newStr = ""

        var rowIndex = 0
        while (rowIndex < word.length) {
            newStr += "88  " + (word.substring(rowIndex, rowIndex + maxSize + 1).replace(" \n", "   88\n"))
            rowIndex += maxSize + 1
        }
        return newStr
    }
}