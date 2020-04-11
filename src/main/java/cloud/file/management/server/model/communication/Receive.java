package cloud.file.management.server.model.communication;

import cloud.file.management.common.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Receive {
    private ObjectInputStream in;

    public Receive(ObjectInputStream in){
        this.in = in;
    }

    public void run(){
        Message inputObject;
        try {
            while ((inputObject = (Message)in.readObject())!=null){
                System.out.println(inputObject.getLogin()+"   "+inputObject.getPath());
                File file =  new File("D:\\folderTestowyAplikacja\\kopia.PNG");
                try(FileOutputStream fileOutputStream = new FileOutputStream(file)){
                    fileOutputStream.write(inputObject.getFileInByte());
                    fileOutputStream.flush();
                }
            }
        }catch (ClassNotFoundException | IOException e){
            e.printStackTrace();
        }
    }
}
