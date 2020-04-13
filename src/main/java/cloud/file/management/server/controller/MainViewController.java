package cloud.file.management.server.controller;

import cloud.file.management.server.model.HandlerResources;
import cloud.file.management.server.model.serverTasks.LoadUserResources;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Objects;

public class MainViewController {


    @FXML
    private ListView<String> listViewUsers;

    @FXML
    private TreeView<String> treeViewFiles;

    private void refreshListUser() {
        while (true) {
            Platform.runLater(() -> {
                var listOfUsers = LoadUserResources.loadUsers();
                if (!Objects.isNull(listOfUsers)) {
                    listViewUsers.getItems().clear();
                    for (String nick : listOfUsers)
                        listViewUsers.getItems().add(nick);
                }
            });
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadTreeItems(Path path) {
        try {
            TreeItem<String> root = HandlerResources.listDirectory(path);
            treeViewFiles.setRoot(root);
            root.setExpanded(true);
        } catch (FileNotFoundException ex) {
            TreeItem<String> root = new TreeItem<>("INVALID PATH !!!");
            treeViewFiles.setRoot(root);
        }
    }

    public void initialize() {
        Thread refreshUserListThread = new Thread(this::refreshListUser);
        refreshUserListThread.setDaemon(true);
        refreshUserListThread.start();

        listViewUsers.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (!Objects.isNull(newValue))
                        loadTreeItems(Path.of(newValue));
                });
    }

}
