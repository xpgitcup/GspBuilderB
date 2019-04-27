package main

import document.CommonDocument
import frame.CommonFrame

class Main {
    static void main(String[] argvs) {

        def document = new CommonDocument("文档名称")//CommonDocument('种子程序, ver 0.1, 2018.03.03')
        println(document.name)

        def mainFrame = new CommonFrame(document)//CommonFrame(document)
        mainFrame.run()
    }
}
