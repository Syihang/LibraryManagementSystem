package com.suyihang.controller.student;

import com.suyihang.Main;
import com.suyihang.database.DataInsert;
import com.suyihang.database.DataSelectBook;
import com.suyihang.database.DataUpdate;
import com.suyihang.entity.AIGCrecord;
import com.suyihang.entity.Book;
import com.suyihang.entity.Borrow;
import com.suyihang.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.sql.Date;
import java.util.ArrayList;

public class ReturnBookController {

    @FXML
    private Label AICGMenu;

    @FXML
    private ToggleGroup RadioGroup;

    @FXML
    private Button applyKoseBut;

    @FXML
    private Button applyReturnBut;

    @FXML
    private TableColumn<Borrow, String> author;

    @FXML
    private TextField bookLentDateTextField;

    @FXML
    private TableColumn<Borrow, String> bookName;

    @FXML
    private TextField bookNotes;

    @FXML
    private TextField bookRemainDateTextField;

    @FXML
    private DatePicker bookReturnDate;

    @FXML
    private TextField bookStatus;

    @FXML
    private TextField bookTitleTextField;

    @FXML
    private TextField bookTitleTextField1;

    @FXML
    private Label findBookMenu;

    @FXML
    private Label lendBookMenu;

    @FXML
    private TableColumn<Borrow, String> lendData;

    @FXML
    private Button delayBut;

    @FXML
    private AnchorPane losePanel;

    @FXML
    private Label nameLabel;

    @FXML
    private RadioButton noRadioBut;

    @FXML
    private Label personalMenu;

    @FXML
    private TableColumn<Borrow, String> remainData;

    @FXML
    private Label returnBookMenu;

    @FXML
    private Button returnBut;

    @FXML
    private AnchorPane returnPanel;

    @FXML
    private Button submitBut;

    @FXML
    private TableView<Borrow> tableView;

    @FXML
    private AnchorPane topPanel;

    @FXML
    private TableColumn<Borrow, String> type;

    @FXML
    private RadioButton yesRadioBut;

    @FXML
    private DatePicker reportTime;

    @FXML
    void applyLoseButAction(ActionEvent event) {
        returnPanel.setVisible(false);
        losePanel.setVisible(true);
    }

    @FXML
    void applyReturnButAction(ActionEvent event) {
        returnPanel.setVisible(true);
        losePanel.setVisible(false);
    }

    @FXML
    void delayButAction(ActionEvent event) {
        if (selectedBorrow == null) {
            DialogUtil.showAlert("提示", "请先选择书籍");
            return;
        }
        Date returnTime = Date.valueOf(bookReturnDate.getValue());
        if (DataUpdate.updateExpectedReturnTime(selectedBorrow.getBorrowId(), returnTime)) {
            long day = DateUtil.calculateDateDifference(selectedBorrow.getReturnTime(), returnTime);
            DialogUtil.showAlert("提示", "您已成功申请延时" + day + "天");
        } else {
            DialogUtil.showAlert("提示", "申请失败,该书籍已归还");
        }
        addTableView(DataSelectBook.findAllBorrowsByStudentID(StudentUtil.current_student.getStudentId()));
    }

    @FXML
    void returnButAction(ActionEvent event) {
        if (selectedBorrow == null) {
            DialogUtil.showAlert("提示", "请先选择书籍");
            return;
        }
        Date returnTime;
        if (bookReturnDate.getValue() == null) {
            returnTime = DateUtil.getCurrentTime();
        } else {
            returnTime = Date.valueOf(bookReturnDate.getValue());
        }
        if (DateUtil.timeCompare(returnTime, DateUtil.getCurrentTime()) != 0) {
            DialogUtil.showAlert("提示", "归还时间只能选择当日");
        } else {
            if (DataUpdate.updateBorrow(selectedBorrow.getBorrowId(), returnTime)) {
                DialogUtil.showAlert("提示", "归还成功");
            } else {
                DialogUtil.showAlert("提示", "操作失败,该书籍已归还");
            }
        }
        addTableView(DataSelectBook.findAllBorrowsByStudentID(StudentUtil.current_student.getStudentId()));
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
    void submitButAction(ActionEvent event) {
        if (selectedBorrow == null) {
            DialogUtil.showAlert("提示", "请先选择书籍");
            return;
        }
        String student_id = StudentUtil.current_student.getStudentId();
        String ISBN = selectedBorrow.getISBN();
        String status = bookStatus.getText();
        Date reportTimes = Date.valueOf(reportTime.getValue());
        int isCompensation = yesRadioBut.isSelected() ? 1:0;
        String note = bookNotes.getText();
        if (DataInsert.insertLose(student_id, ISBN, status, reportTimes, isCompensation, note)) {
            if (DataUpdate.updateBorrow(selectedBorrow.getBorrowId(), reportTimes)) {
                DialogUtil.showAlert("提示", "提交成功");
            } else {
                DialogUtil.showAlert("提示", "提交失败,书籍已归还");
            }
        } else {
            DialogUtil.showAlert("提示", "提交失败");
        }
        addTableView(DataSelectBook.findAllBorrowsByStudentID(StudentUtil.current_student.getStudentId()));
    }

    private void addTableView(ArrayList<Borrow> borrowArrayList) {
        tableView.getItems().clear();

        nameLabel.setText(StudentUtil.current_student.getStudentName());
        // 初始化表格的列
        bookName.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        lendData.setCellValueFactory(new PropertyValueFactory<>("borrowTime"));
        remainData.setCellValueFactory(new PropertyValueFactory<>("days"));

        // 将 ArrayList 转为 ObservableList 并添加到表格中

        tableView.setItems(convertToObservableList(borrowArrayList));

        // 设置默认排序
        tableView.getSortOrder().add(remainData);
        remainData.setSortType(TableColumn.SortType.DESCENDING);
        tableView.sort();
    }

    private ObservableList<Borrow> convertToObservableList(ArrayList<Borrow> borrowArrayList) {
        return FXCollections.observableArrayList(borrowArrayList);
    }

    @FXML
    private void initialize(){
        addTableView(DataSelectBook.findAllBorrowsByStudentID(StudentUtil.current_student.getStudentId()));
        addAction();
    }
    Borrow selectedBorrow;
    private void addAction(){
        // 添加单击事件处理
        tableView.setRowFactory(tv -> {
            TableRow<Borrow> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1 && !row.isEmpty()) {
                    selectedBorrow = tableView.getSelectionModel().getSelectedItem();
                    handleRowDoubleClick(selectedBorrow);
                }
            });
            return row;
        });
    }

    private void handleRowDoubleClick(Borrow selectedBorrow) {
        // 设置还书
        bookTitleTextField.setText(selectedBorrow.getTitle());
        bookLentDateTextField.setText(String.valueOf(selectedBorrow.getBorrowTime()));
        bookRemainDateTextField.setText(selectedBorrow.getDays());

        // 设置丢失
        bookTitleTextField1.setText(selectedBorrow.getTitle());
    }

}
