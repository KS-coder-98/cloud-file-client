package cloud.file.management.server.controller;

import cloud.file.management.server.model.serverTasks.LoadUserResources;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;

public class MainViewController {


    @FXML
    private ListView<String> listViewUsers;

    @FXML
    private TreeView<?> treeViewFiles;

    private void refreshListUser(){
        while (true){
            Platform.runLater(()->{
                var listOfUsers = LoadUserResources.loadUsers();
                if ( listOfUsers != null ){
                    listViewUsers.getItems().clear();
                    for (String nick : listOfUsers){
                        listViewUsers.getItems().add(nick);
                    }

                }
            });
            try {
                Thread.sleep(60000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize(){
        Thread refreshUserListThread = new Thread(this::refreshListUser);
        refreshUserListThread.setDaemon(true);
        refreshUserListThread.start();
    }

}
