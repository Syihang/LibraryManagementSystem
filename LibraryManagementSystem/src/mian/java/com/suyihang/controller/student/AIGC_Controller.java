package com.suyihang.controller.student;

import com.suyihang.AI.WAIDialogue;
import com.suyihang.AI.WAIPainting;
import com.suyihang.Main;
import com.suyihang.database.DataSelectBook;
import com.suyihang.entity.AIGCrecord;
import com.suyihang.entity.Book;
import com.suyihang.util.AIGCUtil;
import com.suyihang.util.DialogUtil;
import com.suyihang.util.StringUtil;
import com.suyihang.util.StudentUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableViewSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class AIGC_Controller {

    @FXML
    public ImageView imageView;

    @FXML
    public TextField imageTF;

    @FXML
    public Button imageBut;

    @FXML
    private Label AICGMenu;

    @FXML
    private TextArea GPTTextArea;

    @FXML
    private Label findBookMenu;

    @FXML
    private Label lendBookMenu;

    @FXML
    private Label nameLabel;

    @FXML
    private Label personalMenu;

    @FXML
    private Label returnBookMenu;

    @FXML
    private Button sentBut;

    @FXML
    private TableView<AIGCrecord> tableView;

    @FXML
    private TextField textField;

    @FXML
    private TableColumn<AIGCrecord, String> tableColumn;

    @FXML
    private AnchorPane topPanel;


    @FXML
    void skipAIGC(MouseEvent event) {

        // 将当前的对话插入列表
        insertAIGC(AIGCUtil.currentAI);

        AIGCUtil.currentAI = new AIGCrecord();

        try {
            Main.switchScene("/com/suyihang/student/AIGC.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void skipBookFind(MouseEvent event) {
        insertAIGC(AIGCUtil.currentAI);
        try {
            Main.switchScene("/com/suyihang/student/chart.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void skipBookLend(MouseEvent event) {
        insertAIGC(AIGCUtil.currentAI);
        try {
            Main.switchScene("/com/suyihang/student/lendBook.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void skipBookReturn(MouseEvent event) {
        insertAIGC(AIGCUtil.currentAI);
        try {
            Main.switchScene("/com/suyihang/student/returnBook.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void skipPersonalCenter(MouseEvent event) {
        insertAIGC(AIGCUtil.currentAI);
        try {
            Main.switchScene("/com/suyihang/student/personPanel.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        addTableView();
        setTableView();
        addAction();
    }

    private void addTableView() {

        tableView.getItems().clear();

        nameLabel.setText(StudentUtil.current_student.getStudentName());
        // 初始化表格的列
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("text"));

        // 将 ArrayList 转为 ObservableList 并添加到表格中

        tableView.setItems(convertToObservableList(AIGCUtil.aigCrecords));
    }

    private ObservableList<AIGCrecord> convertToObservableList(ArrayList<AIGCrecord> bookList) {
        return FXCollections.observableArrayList(bookList);
    }

    @FXML
    void sentMessage(ActionEvent event) {
        sentMess();
        insertAIGC(AIGCUtil.currentAI);
        addTableView();
    }

    // 将数据发送到TextArea中, 同时传递给大模型
    private void sentMess() {
        String message = textField.getText().trim();
        textField.setText("");

        if (!StringUtil.empty(message)) {

            GPTTextArea.appendText("\nUser : " + message);
            // 创建后台任务
            Task<String> task = new Task<String>() {
                @Override
                protected String call() throws Exception {
                    // 调用后台函数并传递输入消息
                    return WAIDialogue.vivogptLibrary(message, AIGCUtil.currentAI.getSessionId());
                }
            };

            // 任务完成后的操作
            task.setOnSucceeded(event -> {
                String result = task.getValue();
                GPTTextArea.appendText("\nReport: " + result + "\n");
                insertAIGC(AIGCUtil.currentAI);
            });

            // 任务失败后的操作
            task.setOnFailed(event -> {
                GPTTextArea.appendText("\nTask failed: " + task.getException().getMessage());
            });

            // 启动后台任务
            Thread thread = new Thread(task);
            thread.setDaemon(true); // 确保后台线程在应用程序退出时也能结束
            thread.start();
        }
    }

    private void insertAIGC(AIGCrecord aigCrecord) {

        if (!StringUtil.empty(GPTTextArea.getText()) && AIGCUtil.currentAI != null) {
            AIGCUtil.currentAI.setText(GPTTextArea.getText());
        }

        if (!exitAIGC(aigCrecord)) {
            AIGCUtil.aigCrecords.add(aigCrecord);
        }
    }

    private boolean exitAIGC(AIGCrecord aigCrecord) {
        for (AIGCrecord aigCrecord1:AIGCUtil.aigCrecords) {
            if (aigCrecord1.getSessionId().equals(aigCrecord.getSessionId())) {
                return true;
            }
        }
        return false;
    }

    private void setTableView() {
        Platform.runLater(() -> {
            TableViewSkin<?> skin = (TableViewSkin<?>) tableView.getSkin();
            if (skin != null) {
                for (Node node : skin.getChildren()) {
                    if (node instanceof ScrollBar) {
                        ScrollBar scrollBar = (ScrollBar) node;
                        if (scrollBar.getOrientation() == Orientation.HORIZONTAL) {
                            scrollBar.setDisable(true);
                            scrollBar.setVisible(false);
                            System.out.println("Horizontal scroll bar disabled and hidden.");
                        }
                    }
                }
            } else {
                System.out.println("TableView skin is not initialized.");
            }
        });
    }



    // 点击表格事件
    private void addAction(){
        // 添加双击事件处理
        tableView.setRowFactory(tv -> {
            TableRow<AIGCrecord> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !row.isEmpty()) {
                    AIGCUtil.currentAI = row.getItem();
                    handleRowDoubleClick();
                }
            });
            return row;
        });
    }

    private void handleRowDoubleClick() {
        // 处理双击事件
        GPTTextArea.setText(AIGCUtil.currentAI.getText());
    }

    String text;
    String imageUrl = null;

    @FXML
    public void imageButAction(ActionEvent actionEvent) {
        text = imageTF.getText();
        if (StringUtil.empty(text)) {
            DialogUtil.showAlert("提示", "请先输入内容");
            return;
        }

        imageTF.setText("");

        // 创建一个Task来加载图像
        Task<Image> loadImageTask = new Task<>() {
            @Override
            protected Image call() throws Exception {
                imageUrl = WAIPainting.waitForImage(WAIPainting.task_submit(text));
                return new Image(imageUrl);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                // 在UI线程中更新ImageView
                imageView.setImage(getValue());
            }

            @Override
            protected void failed() {
                super.failed();
                // 处理任务失败的情况
                System.err.println("Image loading failed: " + getException().getMessage());
            }
        };

        // 创建一个新的线程来执行任务
        new Thread(loadImageTask).start();

    }

    @FXML
    public void downloadButAction() {

        System.out.println(111);

        if (StringUtil.empty(imageUrl)) {
            DialogUtil.showAlert("提示", "请先生成图片");
            return;
        }
        String destinationFile = text + ".jpg";

        immm();

    }

    private void immm() {
        // 构建FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");

        // 设置默认的文件名和文件类型过滤器
        fileChooser.setInitialFileName(text + ".jpg");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);

        // 显示文件保存对话框
        File file = fileChooser.showSaveDialog(Main.getMainStage());

        // 如果用户选择了文件，开始下载和保存图片
        if (file != null) {
//            imageUrl = "https://ai-painting-image.vivo.com.cn/ai-painting/763783fb8ee6844ee6b35b5ba473b6e6006b69b9-0.jpg";
            downloadImage(imageUrl, file);
        }
    }

    private void downloadImage(String imageUrl, File file) {
        try {
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            OutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[2048];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();

            System.out.println("Image downloaded successfully to: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
