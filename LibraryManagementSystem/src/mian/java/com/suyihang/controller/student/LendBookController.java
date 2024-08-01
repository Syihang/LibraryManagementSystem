package com.suyihang.controller.student;

import com.suyihang.Main;
import com.suyihang.database.DataSelect;
import com.suyihang.database.DataSelectBook;
import com.suyihang.entity.AIGCrecord;
import com.suyihang.entity.Book;
import com.suyihang.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Map;

public class LendBookController {

    @FXML
    private Label AICGMenu;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TextField authorField;

    @FXML
    private TableColumn<Book, String> bookTitleColumn;

    @FXML
    private TableView<Book> booksTableView;

    @FXML
    private Button buyBut;

    @FXML
    private Label findBookMenu;

    @FXML
    private Button findBut;

    @FXML
    private Label lendBookMenu;

    @FXML
    private Button lendBut;

    @FXML
    private Label nameLabel;

    @FXML
    private Label personalMenu;

    @FXML
    private Label priceLabel;

    @FXML
    private TableColumn<Book, String> publicColumn;

    @FXML
    private Label publicDateLabel;

    @FXML
    private TextField publicField;

    @FXML
    private Label publicLabel;

    @FXML
    private TableColumn<Book, Integer> repetoryColumn;

    @FXML
    private Label repetoryLabel;

    @FXML
    private Label returnBookMenu;

    @FXML
    private TextField titleField;

    @FXML
    private Label authorLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private AnchorPane topPanel;

    @FXML
    private Label typeLabel;

    @FXML
    private ComboBox<String> typeBox;

    private Map<String, Integer> typeCom;

    @FXML
    void buyBooks(ActionEvent event) {
        DialogUtil.showAlert("提示", "该功能暂未开放。");
    }

    @FXML
    void lendBooks(ActionEvent event) {

        if (BookUtil.currentSelectBook == null) {
            DialogUtil.showAlert("提示", "请先选择书籍。");
        } else {
            Main.addScene("/com/suyihang/student/LendBookItem.fxml");
            Book selectedBook = booksTableView.getSelectionModel().getSelectedItem();
            selectedBook = DataSelectBook.selectBookByISBN(selectedBook.getISBN());
            setRightLabel(selectedBook);
            findBook();
        }

    }

    @FXML
    void findBooks(ActionEvent event) {
        findBook();
    }

    private void findBook() {

        String title = titleField.getText();
        String author = authorField.getText();
        String typeName = typeBox.getValue();

        int typeID;

        if (StringUtil.empty(typeName)) {
            typeID = 0;
        } else {
            typeID = typeCom.get(typeName);
        }

        // 按条件查找
        ArrayList<Book> bookByCriteria = DataSelectBook.findBookByCriteria(title, author, typeID);

        addTableView(bookByCriteria);
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
    private void initialize() {
        StudentUtil.current_student = DataSelect.findStudentByID(StudentUtil.current_student.getStudentId());

        typeCom = DataSelectBook.findAllType();

        addTableView(DataSelectBook.selectAllBooks());

        addComboBox();
    }

    private ObservableList<Book> convertToObservableList(ArrayList<Book> bookList) {
        return FXCollections.observableArrayList(bookList);
    }

    @FXML
    private void handleRowSelect(MouseEvent event) {
        if (event.getClickCount() == 1) { // Double-click to select
            Book selectedBook = booksTableView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                System.out.println("Selected book: " + selectedBook);
                setRightLabel(selectedBook);
                // You can now use selectedBook to perform further actions
            }
        }
    }

    private void setRightLabel(Book book) {
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        publicLabel.setText(book.getPublisher());
        publicDateLabel.setText(book.getPublicationDate().toString());
        typeLabel.setText(String.valueOf(book.getType()));
        priceLabel.setText(String.valueOf(book.getPrice()));
        repetoryLabel.setText(String.valueOf(book.getRepertory()));

        BookUtil.currentSelectBook = book;
    }

    private void addTableView(ArrayList<Book> bookList) {
        booksTableView.getItems().clear();

        nameLabel.setText(StudentUtil.current_student.getStudentName());
        // 初始化表格的列
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publicColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        repetoryColumn.setCellValueFactory(new PropertyValueFactory<>("repertory"));

        // 将 ArrayList 转为 ObservableList 并添加到表格中

        booksTableView.setItems(convertToObservableList(bookList));
    }

    private void addComboBox() {

        typeBox.getItems().add("");

        typeBox.setValue("");

        for (Map.Entry<String, Integer> entry : typeCom.entrySet()) {
            typeBox.getItems().add(entry.getKey());
        }
    }

}
