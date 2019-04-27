package document

class CommonDocument {
    def name
    def guidStrings = []
    def tabNames
    def status = [
            'key1': 'value1'
    ]

    def createGspText(String tabNames, String tabFlags) {
        println("生成gsp文本...")

        def tabs = com.alibaba.fastjson.JSON.parse(tabNames)
        println("${tabs}")
        println("${tabs.size()}")

        return "自动生成的文本"
    }

    def CommonDocument(str) {
        name = str
    }
}
