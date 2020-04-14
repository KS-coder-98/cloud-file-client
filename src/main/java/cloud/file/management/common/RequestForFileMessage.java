package cloud.file.management.common;

import java.util.List;

public class RequestForFileMessage extends Message {
    public RequestForFileMessage(String login, List<String> list){
        super(login, list);
    }


    @Override
    public void preprocess() {

    }
}
