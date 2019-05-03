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

        mb.div(class: "easyui-tabs", id: "list${tabsName}Div") {
            titles.each { e ->
                div(title: e, 'data-options': 'closeable:false') {
                    div(id: "list${e}Div", )
                    div(id: "pagination${tabsName}${e}Div", class: "easyui-pagination")
                }
            }
        }

        return strXml.toString()
    }

    def createTabsJS(tabsName, tabNames, domainName) {
        def tempFile = new File("jsTemplate.txt")
        def titles = tabNames.split(",")
        def titlesAsJson = com.alibaba.fastjson.JSON.toJSONString(titles)
        def controller
        if (domainName=='Home') {
            controller = 'home'
            //controller = "operation4" + domainName.substring(0,1).toLowerCase() + domainName.substring(1)
        } else {
            controller = "operation4" + domainName
        }
        def firstTitle = titles[0]

        def engine = new groovy.text.SimpleTemplateEngine()
        def template = engine.createTemplate(tempFile.text)
        def jsText = template.make([
                domainName: tabsName,
                tabNames: titlesAsJson,
                controller: controller,
                firstTitle: firstTitle
        ])

        return jsText
    }

    def createPanel(panelTitle) {
        println("${panelTitle}")
        def strXml = new StringWriter()
        MarkupBuilder mb = new groovy.xml.MarkupBuilder(strXml);
        mb.setExpandEmptyElements(true) //扩展空的元素
        mb.setDoubleQuotes(true)    //采用双引号

        mb.div(class: "easyui-panel") {
            div(title: panelTitle, id: "list${panelTitle}Div", class: "easyui-panel")
            div(id: "pagination${panelTitle}Div", class: "easyui-pagination")
        }
        return strXml.toString()
    }

    def createPanelJS(panelText) {
        def tempFile = new File("jsTemplate4Panel.txt")

        def engine = new groovy.text.SimpleTemplateEngine()
        def template = engine.createTemplate(tempFile.text)
        def jsText = template.make([
                domainName: panelText,
                controller: "operation4${panelText}"
        ])

        return jsText
    }

    def createTreeView(treeTitle) {
        println("${treeTitle}")
        def strXml = new StringWriter()
        MarkupBuilder mb = new groovy.xml.MarkupBuilder(strXml);

        mb.setExpandEmptyElements(true) //扩展空的元素
        mb.setDoubleQuotes(true)    //采用双引号

        mb.ul(id:"treeView${treeTitle}Ul", class:"easyui-tree") {

        }

        return strXml.toString()
    }

    def createTreeViewJS(treeTitle) {
        def tempFile = new File("jsTemplate4Tree.txt")

        def engine = new groovy.text.SimpleTemplateEngine()
        def template = engine.createTemplate(tempFile.text)
        def jsText = template.make([
                domainName: treeTitle
        ])

        return jsText
    }

    def CommonDocument(str) {
        name = str
    }
}

