package com.suyihang.controller.student;

import com.suyihang.Main;
import com.suyihang.database.DataSelect;
import com.suyihang.database.DataUpdate;
import com.suyihang.entity.AIGCrecord;
import com.suyihang.entity.Student;
import com.suyihang.util.AIGCUtil;
import com.suyihang.util.DialogUtil;
import com.suyihang.util.StudentUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class personPanelController {

    @FXML
    private Label AICGMenu;

    @FXML
    private TextField borrowLimit;

    @FXML
    private TextField borrowTimesTF;

    @FXML
    private TextField borrowedBookNumTF;

    @FXML
    private TextField creditRatingTF;

    @FXML
    private TextField creditValueTF;

    @FXML
    private TextField facultyTF;

    @FXML
    private Label findBookMenu;

    @FXML
    private TextField genderTF;

    @FXML
    private TextField lateReturnTimesTF;

    @FXML
    private Label lendBookMenu;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField passwordTF;

    @FXML
    private Label personalMenu;

    @FXML
    private Label returnBookMenu;

    @FXML
    private TextField studentIdTF;

    @FXML
    private TextField studentNameTF;

    @FXML
    private TextField telephoneTF;

    @FXML
    private AnchorPane topPanel;

    @FXML
    private TextField yearTF;

    @FXML
    void Modifications(ActionEvent event) {
        if (DataUpdate.updateStudent(studentIdTF.getId(), passwordTF.getText(), studentNameTF.getText(),
                facultyTF.getText(), telephoneTF.getText())) {
            DialogUtil.showAlert("提示", "修改成功");
        } else {
            DialogUtil.showAlert("提示", "修改失败,输入结果不得为空");
        }
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            Main.switchScene("/com/suyihang/login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
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

    @FXML
    public void initialize(){

        nameLabel.setText(StudentUtil.current_student.getStudentName());

        Student student = DataSelect.findStudentByID(StudentUtil.current_student.getStudentId());

        // 基本信息
        studentIdTF.setText(student.getStudentId());
        passwordTF.setText(student.getPassword());
        studentNameTF.setText(student.getStudentName());
        genderTF.setText(student.getGender());
        facultyTF.setText(student.getFaculty());
        yearTF.setText(String.valueOf(student.getYear()));
        telephoneTF.setText(student.getTelephone());

        // 借阅信息
        borrowTimesTF.setText(String.valueOf(student.getBorrowTimes()));
        lateReturnTimesTF.setText(String.valueOf(student.getLateReturnTimes()));
        creditValueTF.setText(String.valueOf(student.getCreditValue()));
        creditRatingTF.setText(student.getCreditRating());
        borrowLimit.setText(String.valueOf(student.getBorrowLimit()));
        borrowedBookNumTF.setText(String.valueOf(student.getBorrowedBookNum()));

    }

}
