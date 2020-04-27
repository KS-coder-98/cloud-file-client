package cloud.file.management.server.app;

import cloud.file.management.server.model.ServerSetting;
import cloud.file.management.server.model.communication.EchoMultiServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppMain extends Application {
    public static void main(String[] args) {
        ServerSetting serverSetting = new ServerSetting();
        new EchoMultiServer(ServerSetting.getPort());
        Thread communicationThread = new Thread(EchoMultiServer::run);
        communicationThread.setDaemon(true);
        communicationThread.start();
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainView.fxml"));
        VBox mainView = fxmlLoader.load();
        Scene scene = new Scene(mainView);
        stage.setScene(scene);
        stage.setTitle("SERVER CLOUD FILE MANAGEMENT");
        stage.show();
    }
}
