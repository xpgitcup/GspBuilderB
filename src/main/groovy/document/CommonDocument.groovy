package document

import groovy.xml.MarkupBuilder

class CommonDocument {
    def name
    def guidStrings = []
    def tabNames
    def tabFlags
    def useJson

    def status = [
            'key1': 'value1'
    ]

    def createTabs(tabsName, tabNames) {
        println("${tabNames}")

        def titles = tabNames.split(",")

        def strXml = new StringWriter()
        MarkupBuilder mb = new groovy.xml.MarkupBuilder(strXml);

        mb.div(class: "easyui-tabs", id:"list${tabsName}Div") {
            titles.each { e ->
                div(title:e) {

                }
            }
        }

        return strXml.toString()
    }

    def createPanel(panelTitle) {
        println("${panelTitle}")
        def strXml = new StringWriter()
        MarkupBuilder mb = new groovy.xml.MarkupBuilder(strXml);
        return strXml.toString()
    }

    def createTreeView(treeTitle) {
        println("${treeTitle}")
        def strXml = new StringWriter()
        MarkupBuilder mb = new groovy.xml.MarkupBuilder(strXml);
        return strXml.toString()
    }

    def createGspText() {
        println("生成gsp文本...")

        println("标题：${tabNames}")
        println("标志：${tabFlags}")
        println("是否采用Json：${useJson}")

        def tabs
        def flags = []
        if (useJson) {
            tabs = com.alibaba.fastjson.JSON.parseObject(tabNames)
            flags = com.alibaba.fastjson.JSON.parseObject(tabFlags)
        } else {
            tabs = tabNames.split(",")
            tabFlags.split(",").each { e ->
                if (e == 'true') {
                    flags.add(true)
                } else {
                    flags.add(false)
                }
            }
        }
        println("设置：${tabs}, ${flags}")


        if (tabs) {
            if (tabs.size() < 2) {
                println("单个Panel ${tabs}")
                mb.div(class: "easyui-panel") {

                }
            } else {
                println("使用tabs ${tabs}")
            }
        }

        return strXml.toString()
    }

    def CommonDocument(str) {
        name = str
    }
}
