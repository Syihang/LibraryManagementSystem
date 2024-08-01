package com.suyihang.controller.student;

import com.suyihang.database.DataInsert;
import com.suyihang.database.DataSelect;
import com.suyihang.database.DataSelectBook;
import com.suyihang.entity.Book;
import com.suyihang.entity.Student;
import com.suyihang.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

public class LendBookItemController {

    @FXML
    private Label borrowLimitLabel;

    @FXML
    private Button noBut;

    @FXML
    private Label repertoryLabel;

    @FXML
    private DatePicker returnDatePicker;

    @FXML
    private Label studentCreditLabel;

    @FXML
    private Label studentNameLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Button yesBut;

    @FXML
    void noLent(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void yesLent(ActionEvent event) {
        int borrowLimitNum = student.getBorrowLimit() - student.getBorrowedBookNum();
        if (borrowLimitNum < 1) {
            DialogUtil.showAlert("提示", "借阅失败,您当前状态不可借书,请检查是否存在未及时归还的书籍");
            return;
        }

        if (book.getRepertory() <= 0) {
            DialogUtil.showAlert("提示", "借阅失败,书籍库存不足");
            return;

        }

        LocalDate returnDate = returnDatePicker.getValue();
        if (returnDate == null) {
            DialogUtil.showAlert("提示", "请填写归还日期");
            return;
        }
        Date date = Date.valueOf(returnDate);
        Date currentDate = DateUtil.getCurrentTime();
        System.out.println(date);
        if (DateUtil.calculateDateDifference(currentDate, date) > 90){
            DialogUtil.showAlert("提示", "最长借阅时间不得超过90天");
            return;
        }
        if (DateUtil.timeCompare(currentDate, date) == 1) {
            DialogUtil.showAlert("提示", "归还时间不得早于当前时间");
            return;
        }
        DialogUtil.showAlert("提示", "借阅成功,请在规定时间内归还");

        insertData(currentDate, date);

    }

    private void insertData(Date currentDate, Date returnDate) {
        DataInsert.insertBorrow(student.getStudentId(), book.getISBN(), currentDate, returnDate, null);
        StudentUtil.current_student = DataSelect.findStudentByID(student.getStudentId());
        BookUtil.currentSelectBook = DataSelectBook.selectBookByISBN(book.getISBN());
        initLabel();
    }


    @FXML
    private void initialize() {
        initLabel();
    }

    Student student = StudentUtil.current_student;
    Book book = BookUtil.currentSelectBook;

    private void initLabel() {

        student = StudentUtil.current_student;
        book = BookUtil.currentSelectBook;

        titleLabel.setText(book.getTitle());
        repertoryLabel.setText(String.valueOf(book.getRepertory()));

        studentNameLabel.setText(student.getStudentName());
        studentCreditLabel.setText(String.valueOf(student.getCreditValue()));
        int borrowLimitNum = student.getBorrowLimit() - student.getBorrowedBookNum();
        borrowLimitLabel.setText(String.valueOf(borrowLimitNum));
    }

}
