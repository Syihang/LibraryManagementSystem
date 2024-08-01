package com.suyihang.controller.student;

import com.suyihang.Main;
import com.suyihang.database.DataSelectBook;
import com.suyihang.entity.AIGCrecord;
import com.suyihang.util.AIGCUtil;
import com.suyihang.util.StudentUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Map;

public class ChartController {

    @FXML
    private Label AICGMenu;

    @FXML
    private RadioButton allRadio;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private RadioButton barChartRadio;

    @FXML
    private ToggleGroup chartGroup;

    @FXML
    private ToggleGroup chartGroup1;

    @FXML
    private ToggleGroup chartGroup2;

    @FXML
    private Label findBookMenu;

    @FXML
    private Label lendBookMenu;

    @FXML
    private Label nameLabel;

    @FXML
    private Label personalMenu;

    @FXML
    private RadioButton personalRadio;

    @FXML
    private PieChart pieChart;

    @FXML
    private RadioButton pieChartRadio;

    @FXML
    private Label returnBookMenu;

    @FXML
    private RadioButton titleRadio;

    @FXML
    private AnchorPane topPanel;

    @FXML
    private RadioButton typeRadio;

    @FXML
    void barChartAction(ActionEvent event) {
        barChart.setVisible(true);
        pieChart.setVisible(false);
    }

    @FXML
    void pieChartAction(ActionEvent event) {
        barChart.setVisible(false);
        pieChart.setVisible(true);
    }

    @FXML
    void allAction(ActionEvent event) {
        if (typeRadio.isSelected()) {
            setChartData(DataSelectBook.findAllTypeinBorrowTableNum());
        } else {
            setChartData(DataSelectBook.findAllBookNumInBorrow());
        }
    }

    @FXML
    void personalAction(ActionEvent event) {
        if (typeRadio.isSelected()) {
            setChartData(DataSelectBook.findAllTypeinBorrowTableNumByStudentID(StudentUtil.current_student.getStudentId()));
        } else {
            setChartData(DataSelectBook.findAllBookNumInBorrowByStudentID(StudentUtil.current_student.getStudentId()));
        }
    }

    @FXML
    void titleAction(ActionEvent event) {
        if (personalRadio.isSelected()) {
            setChartData(DataSelectBook.findAllBookNumInBorrowByStudentID(StudentUtil.current_student.getStudentId()));
        } else {
            setChartData(DataSelectBook.findAllBookNumInBorrow());

        }
    }

    @FXML
    void typeAction(ActionEvent event) {
        if (personalRadio.isSelected()) {
            setChartData(DataSelectBook.findAllTypeinBorrowTableNumByStudentID(StudentUtil.current_student.getStudentId()));
        } else {
            setChartData(DataSelectBook.findAllTypeinBorrowTableNum());
        }
    }

    @FXML
    void skipAIGC(MouseEvent event) {
        AIGCUtil.currentAI = new AIGCrecord();
        try {
            Main.switchScene("/com/suyihang/student/AIGC.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void skipBookFind(MouseEvent event) {
        try {
            Main.switchScene("/com/suyihang/student/chart.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void skipBookLend(MouseEvent event) {
        try {
            Main.switchScene("/com/suyihang/student/lendBook.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void skipBookReturn(MouseEvent event) {
        try {
            Main.switchScene("/com/suyihang/student/returnBook.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void skipPersonalCenter(MouseEvent event) {
        try {
            Main.switchScene("/com/suyihang/student/personPanel.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setChartData(Map<String, Integer> data) {
        // 清空现有的数据
        pieChart.getData().clear();
        barChart.getData().clear();

        // 添加数据到饼状图
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            PieChart.Data pieData = new PieChart.Data(entry.getKey(), entry.getValue());
            pieChart.getData().add(pieData);
        }

        // 添加数据到柱状图
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            XYChart.Data<String, Number> barData = new XYChart.Data<>(entry.getKey(), entry.getValue());
            series.getData().add(barData);
        }
        barChart.getData().add(series);
    }

    @FXML
    private void initialize(){

        nameLabel.setText(StudentUtil.current_student.getStudentName());

        setChartData(DataSelectBook.findAllTypeinBorrowTableNumByStudentID(StudentUtil.current_student.getStudentId()));
    }
}
