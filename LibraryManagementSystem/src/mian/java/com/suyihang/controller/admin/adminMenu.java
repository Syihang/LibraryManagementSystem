package com.suyihang.controller.admin;

import com.suyihang.Main;
import com.suyihang.database.DataSelect;
import com.suyihang.database.DataSelectBook;
import com.suyihang.entity.Book;
import com.suyihang.entity.Borrow;
import com.suyihang.entity.Lose;
import com.suyihang.entity.Student;
import com.suyihang.file.ExcelToDatabase;
import com.suyihang.file.StudentImportDataListener;
import com.suyihang.util.AdminUtil;
import com.suyihang.util.DialogUtil;
import com.suyihang.util.StringUtil;
import com.suyihang.util.StudentUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

public class adminMenu {

    @FXML
    private Button bookAdd;

    @FXML
    private TableColumn<?, ?> bookAuthor;

    @FXML
    private TextField bookAuthorTF;

    @FXML
    private TableColumn<?, ?> bookISBN;

    @FXML
    private TextField bookISBNTF;

    @FXML
    private Button bookImportBut;

    @FXML
    private TextField bookImportTF;

    @FXML
    private BorderPane bookManagePanel;

    @FXML
    private TableColumn<?, ?> bookName;

    @FXML
    private TextField bookNameTF;

    @FXML
    private TableColumn<?, ?> bookNum;

    @FXML
    private TextField bookPriceTF;

    @FXML
    private TextField bookPublicDateTF;

    @FXML
    private TextField bookPublicTF;

    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TextField bookTotalNumTF;

    @FXML
    private TableColumn<?, ?> bookType;

    @FXML
    private TextField bookTypeTF;

    @FXML
    private Button bookUpdate;

    @FXML
    private Button boolDelete;

    @FXML
    private Button borrowAdd;

    @FXML
    private TableColumn<?, ?> borrowBookTitle;

    @FXML
    private TextField borrowBookTitleTF;

    @FXML
    private TableColumn<?, ?> borrowDate;

    @FXML
    private TextField borrowDateTF;

    @FXML
    private Button borrowDelete;

    @FXML
    private TextField borrowIDTF;

    @FXML
    private Button borrowImportBut;

    @FXML
    private TextField borrowImportTF;

    @FXML
    private BorderPane borrowManagePanel;

    @FXML
    private TableColumn<?, ?> borrowName;

    @FXML
    private TextField borrowNameTF;

    @FXML
    private TableColumn<?, ?> borrowRemainDate;

    @FXML
    private TextField borrowRemainDateTF;

    @FXML
    private TextField borrowReturnDateTF;

    @FXML
    private TableView<Borrow> borrowTableView;

    @FXML
    private Button borrowUpdate;

    @FXML
    private Button loseAdd;

    @FXML
    private TableColumn<?, ?> loseBookTitle;

    @FXML
    private TextField loseBookTitleTF;

    @FXML
    private TextField loseCompensationTF;

    @FXML
    private Button loseDelete;

    @FXML
    private Button loseImportBut;

    @FXML
    private TextField loseImportTF;

    @FXML
    private BorderPane loseManagePanel;

    @FXML
    private TableColumn<?, ?> loseName;

    @FXML
    private TextField loseNameTF;

    @FXML
    private TextField loseNoteTF;

    @FXML
    private TableColumn<?, ?> loseNotes;

    @FXML
    private TableColumn<?, ?> loseStatus;

    @FXML
    private TextField loseStatusTF;

    @FXML
    private TextField loseSubmitDateTF;

    @FXML
    private TableView<Lose> loseTableView;

    @FXML
    private Button loseUpdate;

    @FXML
    private Label nameLabel;

    @FXML
    private AnchorPane topPanel;

    @FXML
    private TextField userAccountTF;

    @FXML
    private Button userAdd;

    @FXML
    private TextField userCollageTF;

    @FXML
    private TextField userCreditValueTF;

    @FXML
    private Button userDelete;

    @FXML
    private TextField userGenderTF;

    @FXML
    private TableColumn<?, ?> userID;

    @FXML
    private Button userImportBut;

    @FXML
    private TextField userImportTF;

    @FXML
    private BorderPane userManagePanel;

    @FXML
    private TableColumn<?, ?> userName;

    @FXML
    private TextField userNameTF;

    @FXML
    private TableColumn<?, ?> userPassword;

    @FXML
    private TextField userPasswordTF;

    @FXML
    private TableView<Student> userTableView;

    @FXML
    private TableColumn<?, ?> userTelephone;

    @FXML
    private TextField userTelephoneTF;

    @FXML
    private Button userUpdate;

    @FXML
    private TextField userYearTF;

    @FXML
    void bookManageAction(MouseEvent event) {
        bookManagePanel.setVisible(true);
        userManagePanel.setVisible(false);
        borrowManagePanel.setVisible(false);
        loseManagePanel.setVisible(false);
    }

    @FXML
    void userManageAction(MouseEvent event) {
        bookManagePanel.setVisible(false);
        userManagePanel.setVisible(true);
        borrowManagePanel.setVisible(false);
        loseManagePanel.setVisible(false);
    }

    @FXML
    void borrowManageAction(MouseEvent event) {
        bookManagePanel.setVisible(false);
        userManagePanel.setVisible(false);
        borrowManagePanel.setVisible(true);
        loseManagePanel.setVisible(false);
    }

    @FXML
    void loseManageAction(MouseEvent event) {
        bookManagePanel.setVisible(false);
        userManagePanel.setVisible(false);
        borrowManagePanel.setVisible(false);
        loseManagePanel.setVisible(true);
    }

    @FXML
    void logoutAction(MouseEvent event) {
        try {
            Main.switchScene("/com/suyihang/login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        nameLabel.setText(AdminUtil.currentAdmin.getAdminName());

        Thread thread1 = new Thread(() -> setBookTableView(DataSelectBook.selectAllBooks()));
        Thread thread2 = new Thread(this::addBookTableViweAction);
        Thread thread3 = new Thread(() -> setUserTableView(DataSelect.findAllStudents()));
        Thread thread4 = new Thread(() -> addUserTableViweAction());
        Thread thread5 = new Thread(() -> setBorrowTableView(DataSelectBook.findAllBorrows()));
        Thread thread6 = new Thread(() -> setLoseTableView(DataSelect.findAllLose()));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();

// 等待所有线程执行完成
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
            thread6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        addButtonAction();

        addImportTFAction();
    }

    private void setBookTableView(ArrayList<Book> bookList) {
        bookTableView.getItems().clear();

        // 初始化表格的列
        bookName.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        bookISBN.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        bookNum.setCellValueFactory(new PropertyValueFactory<>("repertory"));

        // 将 ArrayList 转为 ObservableList 并添加到表格中
        bookTableView.setItems(FXCollections.observableArrayList(bookList));
    }

    private void setUserTableView(ArrayList<Student> studentList) {
        userTableView.getItems().clear();

        // 初始化表格的列
        userID.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        userPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        userName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        userTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        // 将 ArrayList 转为 ObservableList 并添加到表格中
        userTableView.setItems(FXCollections.observableArrayList(studentList));
    }

    private void setBorrowTableView(ArrayList<Borrow> borrowList) {
        borrowTableView.getItems().clear();

        // 初始化表格的列
        borrowName.setCellValueFactory(new PropertyValueFactory<>("name"));
        borrowBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        borrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowTime"));
        borrowRemainDate.setCellValueFactory(new PropertyValueFactory<>("days"));

        // 将 ArrayList 转为 ObservableList 并添加到表格中
        borrowTableView.setItems(FXCollections.observableArrayList(borrowList));
    }

    private void setLoseTableView(ArrayList<Lose> loseList) {
        loseTableView.getItems().clear();

        // 初始化表格的列
        loseName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        loseBookTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        loseStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        loseNotes.setCellValueFactory(new PropertyValueFactory<>("remark"));

        // 将 ArrayList 转为 ObservableList 并添加到表格中
        loseTableView.setItems(FXCollections.observableArrayList(loseList));
    }

    private void addBookTableViweAction(){
        // 添加双击事件处理
        bookTableView.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1 && !row.isEmpty()) {
                    Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
                    bookISBNTF.setText(selectedBook.getISBN());
                    bookNameTF.setText(selectedBook.getTitle());
                    bookAuthorTF.setText(selectedBook.getAuthor());
                    bookPublicTF.setText(selectedBook.getPublisher());
                    bookPublicDateTF.setText(String.valueOf(selectedBook.getPublicationDate()));
                    bookTotalNumTF.setText(String.valueOf(selectedBook.getTotal()));
                    bookPriceTF.setText(String.valueOf(selectedBook.getPrice()));
                    bookTypeTF.setText(selectedBook.getType());
                }
            });
            return row;
        });
    }

    private void addUserTableViweAction(){
        // 添加双击事件处理
        userTableView.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1 && !row.isEmpty()) {
                    Student selectedBorrow = userTableView.getSelectionModel().getSelectedItem();
                    userAccountTF.setText(selectedBorrow.getStudentId());
                    userPasswordTF.setText(selectedBorrow.getPassword());
                    userNameTF.setText(selectedBorrow.getStudentName());
                    userGenderTF.setText(selectedBorrow.getGender());
                    userCreditValueTF.setText(String.valueOf(selectedBorrow.getCreditValue()));
                    userCollageTF.setText(selectedBorrow.getFaculty());
                    userYearTF.setText(String.valueOf(selectedBorrow.getYear()));
                    userTelephoneTF.setText(selectedBorrow.getTelephone());
                }
            });
            return row;
        });
    }

    private void addButtonAction() {
        boolDelete.setOnAction(e -> {
            boolean b = DialogUtil.showConfirmationDialog("提示", "是否确认删除");
            System.out.println(b);

            if (b) {
                DialogUtil.showAlert("提示", "删除失败,该书籍未全部收回");
            }

        });

        bookAdd.setOnAction(e -> {
            DialogUtil.showAlert("提示", "书籍编号重复");
        });

        bookUpdate.setOnAction(e -> {
            DialogUtil.showAlert("提示", "修改成功");
        });

        userDelete.setOnAction(e -> {
            boolean b = DialogUtil.showConfirmationDialog("提示", "是否确认删除");
            System.out.println(b);

            if (b) {
                DialogUtil.showAlert("提示", "删除失败,该用户有书籍未归还");
            }

        });

        userAdd.setOnAction(e -> {
            DialogUtil.showAlert("提示", "学号重复");
        });

        userUpdate.setOnAction(e -> {
            DialogUtil.showAlert("提示", "修改成功");
        });
    }

    String fileName = null;

    // 设置文件拖动到文本框的事件
    private void addImportTFAction(){
        // 设置拖动进入的事件
        userImportTF.setOnDragOver(event -> {
            if (event.getGestureSource() != userImportTF && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        // 设置拖动释放的事件
        userImportTF.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                File file = db.getFiles().get(0); // 获取第一个拖动的文件
                userImportTF.setText(file.getAbsolutePath()); // 显示文件的绝对路径
                fileName = file.getAbsolutePath();
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    @FXML
    public void userImportButAction(ActionEvent actionEvent) {

        if (StringUtil.empty(userImportTF.getText().trim())) {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(Main.getMainStage());
            if (file != null) {
                userImportTF.setText(file.getAbsolutePath());
                fileName = file.getAbsolutePath();
            } else {
                return;
            }
        } else {
            fileName = userImportTF.getText();
        }

        if (fileName.endsWith(".xlsx")  ) {
            ExcelToDatabase.importStudentToDatabase(fileName);
            DialogUtil.showAlert("提示", "成功导入" + StudentImportDataListener.successCount + "条学生数据");
            setUserTableView(DataSelect.findAllStudents());
        } else {
            DialogUtil.showAlert("提示", "文件格式错误,仅支持xlsx文件");
        }
    }
}
