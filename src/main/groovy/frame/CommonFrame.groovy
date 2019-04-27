package frame

import groovy.swing.SwingBuilder

import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JTabbedPane
import javax.swing.UIManager
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.Toolkit
import java.awt.event.ActionEvent

class CommonFrame {
    //基础GUI设置
    def swing = new SwingBuilder()
    JFrame frame
    def toolkit = Toolkit.getDefaultToolkit()
    def screenSize = toolkit.getScreenSize()

    def WIDTH = 800
    def HEIGHT = 600
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
    * 处理用户的操作
    * */

    def commonAction(ActionEvent evt) {
        def actionName = evt.actionCommand

        println("通用事件处理：${actionName} -- ${evt}")

        println("事件来源：${evt.source.name}")
        switch ("${evt.source.name}") {
            case "数据输入":
                println(tabNames.text)
                document.tabNames = tabNames.text
                document.tabFlags = tabFlags.text
                document.useJson = useJson.isSelected()
                gspSource.text = document.createGspText()
                break

        }

        switch (actionName) {
            case "生成标签页":
                gspSource.text = document.createTabs(tabsName.text, tabNames.text)
                break
            case "生成面板":
                gspSource.text = document.createPanel(panelTitle.text)
                break
            case "生成树形面板":
                gspSource.text = document.createTreeView(treeTitle.text)
                break

        }
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
    def tabsName
    def makeTabsButton
    def panelTitle
    def makePanelButton
    def treeTitle
    def makeTreeButton

    def theToolBar = {
        swing.panel(layout: new BorderLayout(1, 1), constraints: BorderLayout.NORTH) {
            toolBar(constraints: BorderLayout.NORTH) {
                label(text: "标签页设置：")
                label(text:"总标题")
                tabsName = textField(text:"")
                label(text: "标题,逗号分隔")
                tabNames = textField(text: "")
                makeTabsButton = button(text: "生成标签页", actionPerformed: { evt -> commonAction(evt) }, name: "标签页")
            }
            toolBar(constraints: BorderLayout.CENTER) {
                label(text: "面板设置：")
                label(text: "标题")
                panelTitle = textField(text: "", actionPerformed: { evt -> commonAction(evt) })
                makePanelButton = button(text: "生成面板", actionPerformed: { evt -> commonAction(evt) }, name: "面板")
            }
            toolBar(constraints: BorderLayout.SOUTH) {
                label(text: "树形结构：")
                label(text: "标题")
                treeTitle = textField(text: "", actionPerformed: { evt -> commonAction(evt) })
                makeTreeButton = button(text: "生成树形面板", actionPerformed: { evt -> commonAction(evt) }, name: "树形面板")
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

    /*
    * 设置界面
    * */

    def setupUI() {
        theToolBar()
        mainPanel()
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
