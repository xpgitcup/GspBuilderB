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
                div(title:e, 'data-options':'closeable:false') {
                    div(id:"list${e}Div"){data("这里是显示数据")}
                    div(id:"pagination${e}Div", class:"easyui-pagination"){data("???")}
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
