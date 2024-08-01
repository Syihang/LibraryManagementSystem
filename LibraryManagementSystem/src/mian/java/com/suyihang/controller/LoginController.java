package com.suyihang.controller;

import com.suyihang.Main;
import com.suyihang.database.DataSelect;
import com.suyihang.database.DataSelectLogin;
import com.suyihang.util.AdminUtil;
import com.suyihang.util.StringUtil;
import com.suyihang.util.StudentUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LoginController {

    @FXML
    public Label warnLabel;

    @FXML
    private StackPane MainPane;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Button loginButton;

    @FXML
    private VBox loginView;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void handleLogin(ActionEvent event) {
        String value = choiceBox.getValue();
        warnLabel.setVisible(false);
        if (value == null){
            warnLabel.setText("请先选择登录信息");
            warnLabel.setVisible(true);
            return;
        }

        if (value.equals("用户登录")){
            skip_Student();
            return;
        }
        if (value.equals("管理员登录")){
            skip_Admin();
            return;
        }
        System.out.println(value);
    }

    private void skip_Student(){
        String id = usernameField.getText();

        String password = passwordField.getText();

        String password2 = DataSelectLogin.findStudentPassword(id);

        if (StringUtil.empty(id)) {
            warnLabel.setVisible(true);
            return;
        }

        if (StringUtil.empty(password)) {
            warnLabel.setText("密码不得为空");
            warnLabel.setVisible(true);
            return;
        }

        if (StringUtil.empty(password2)){
            System.out.println(password2);
            warnLabel.setText("用户名不存在");
            warnLabel.setVisible(true);
            return;
        }

        if (!password.equals(password2)) {
            warnLabel.setText("密码错误");
            warnLabel.setVisible(true);
            return;
        }

        try {
            StudentUtil.current_student = DataSelect.findStudentByID(id);
            Main.switchScene("/com/suyihang/student/lendBook.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void skip_Admin(){
        String id = usernameField.getText();

        String password = passwordField.getText();

        String password2 = DataSelectLogin.findAdminPassword(id);

        if (StringUtil.empty(id)) {
            warnLabel.setVisible(true);
            return;
        }

        if (StringUtil.empty(password)) {
            warnLabel.setText("密码不得为空");
            warnLabel.setVisible(true);
            return;
        }

        if (StringUtil.empty(password2)){
            warnLabel.setText("用户名不存在");
            warnLabel.setVisible(true);
            return;
        }

        if (!password.equals(password2)) {
            warnLabel.setText("密码错误");
            warnLabel.setVisible(true);
            return;
        }

        try {
            AdminUtil.currentAdmin = DataSelect.findAdminByAccount(id);
            Main.switchScene("/com/suyihang/admin/adminMenu.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        // 设置默认选择
        choiceBox.setValue("用户登录");
    }
}
