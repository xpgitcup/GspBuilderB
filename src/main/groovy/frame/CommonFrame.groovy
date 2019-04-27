package frame

import groovy.swing.SwingBuilder

import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JTabbedPane
import javax.swing.UIManager
import java.awt.BorderLayout
import java.awt.GridLayout
import java.awt.Toolkit
import java.awt.event.ActionEvent

class CommonFrame {
    //基础GUI设置
    def swing = new SwingBuilder()
    JFrame frame
    def toolkit = Toolkit.getDefaultToolkit()
    def screenSize = toolkit.getScreenSize()

    def WIDTH = 1200
    def HEIGHT = 768
    int X = (screenSize.width - WIDTH) / 2
    int Y = (screenSize.height - HEIGHT) / 2

    def document
    def status
    def gspSource
    def mainTabs
    def statusPanel
    def tabNames
    def tabFlags

    /*
    * 打开文件
    * */

    def openFile(extName) {
        def o = new JFileChooser()
        o.setFileSelectionMode(JFileChooser.FILES_ONLY)

        o.setFileFilter(new javax.swing.filechooser.FileFilter() {

            @Override
            boolean accept(File afile) {
                if (afile.getName().endsWith(extName)) {
                    return true
                } else {
                    return false
                }
            }

            @Override
            String getDescription() {
                return "word 文档"
            }
        })

        def ok = o.showOpenDialog(null)
        if (ok == JFileChooser.APPROVE_OPTION) {
            return o.getSelectedFile()
        } else {
            return null
        }

    }

    /*
    * 显示程序当前的状态
    * */

    def showStatus() {
        //显示结果
        def tmp = []
        statusPanel.clear()
        //document.class.properties.each { e->
        //document.class.fields.each { e ->
        document.status.each { e ->
            tmp.add("${e}\n")
        }
        statusPanel.text = "${tmp}"
    }

    /*
    * 处理用户的操作
    * */

    def commonAction(ActionEvent evt) {
        def actionName = evt.actionCommand

        println("通用事件处理：${actionName} -- ${evt}")
        if (evt.source?.name) {
            status.text = actionName
        }

        switch ("${evt.source.name}") {
            case "数据输入":
                println(tabNames.text)
                gspSource.text = document.createGspText(tabNames.text, tabFlags.text)
                break

        }

        if (document.guidStrings.indexOf(actionName) > 0) {
            //属于预定义的操作
            //根据事件的名称，来决定如何处理事件
            switch (actionName) {
                case "源域类":
                    changeSourceDomain()
                    break

            }
        } else {
            //根据事件的发起者来决定如何处理事件
            println("source: ${evt.source}")
            println("sourceName: ${evt.source.name}")
            processDefinedEvent(evt)
        }

        showStatus()
    }

    protected void processDefinedEvent(ActionEvent evt) {
        println("called from CommonFrame...")
        switch ("${evt.source.name}") {
            case "targetInput":
                inputTargetDomain()
                break
            case "mainDomainInput":
                grailsAuxDcoument.mainDomain = mainDomainInput.text
                println("主域类变更为：${grailsAuxDcoument.mainDomain}")
                break;
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    //工具栏
    def theToolBar = {
        swing.panel(layout: new BorderLayout(), constraints: BorderLayout.NORTH) {
            toolBar(constraints: BorderLayout.NORTH) {
                label(text: "操作流程：")
                // 这里是文档中定义的操作菜单
                document.guidStrings.each { e ->
                    button(text: e, actionPerformed: { evt -> commonAction(evt) })
                    label(text: "->")
                }
                separator()
                label(text: "当前操作:")
                status = label(text: "")
                label(text: "标题,json格式输入")
                tabNames = textField(text: "")
                label(text: "属性（是否树形结构）,json格式输入")
                tabFlags = textField(text: "")
                button(text: "确定", actionPerformed: { evt -> commonAction(evt) }, name:"数据输入")
            }
        }
    }

    /*
    * 显示区
    * */

    def mainPanel = {
        swing.panel(layout: new GridLayout(1, 1), constraints: BorderLayout.CENTER) {
            gspSource = textArea()
        }
    }


    def mainTabPanel = {
        mainTabs = swing.tabbedPane(id: "tabs", tabLayoutPolicy: JTabbedPane.SCROLL_TAB_LAYOUT) {
            //主显示区
            gspSource = textArea(id: 'gspSource')
            //目标
            scrollPane(id: 'statusPanel') {
                statusPanel = textArea()
            }
        }
    }

    /*
    * 设置界面
    * */

    def setupUI() {
        theToolBar()
        //mainPanel()
        mainTabPanel()
    }

    /*
    * 执行--主消息系循环
    * */

    def run() {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
        frame = swing.frame(title: document.name,
                size: [WIDTH, HEIGHT],
                location: [X, Y],
                defaultCloseOperation: javax.swing.WindowConstants.DISPOSE_ON_CLOSE) {
            setupUI()
        }
        frame.setVisible(true)
    }

    /*
    * 构造函数
    * */

    public CommonFrame(doc) {
        document = doc
    }
}
