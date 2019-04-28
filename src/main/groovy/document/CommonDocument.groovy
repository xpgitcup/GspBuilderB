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
        MarkupBuilder mb = new groovy.xml.MarkupBuilder(strXml)

        mb.setExpandEmptyElements(true) //扩展空的元素
        mb.setDoubleQuotes(true)    //采用双引号
        
        mb.div(class: "easyui-tabs", id:"list${tabsName}Div") {
            titles.each { e ->
                div(title:e, 'data-options':'closeable:false') {
                    div(id:"list${e}Div")
                    div(id:"pagination${e}Div", class:"easyui-pagination")
                }
            }
        }

        return strXml.toString()
    }
    
    def createTabsJS(tabsName, tabNames) {
        return "JS文件"
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

    def CommonDocument(str) {
        name = str
    }
}
