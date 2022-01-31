package server.Util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable {
    private String operation;
    private Status status;
    
    Map<String, Object> params;
    
    public Message(String operation) {
        this.operation = operation;
        params = new HashMap<>();
    }
    
    public String getOperation() {
        return operation;
    }
    
    public void setStatus(Status status){
        this.status = status;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setParam(String key, Object value) {
        params.put(key, value);
    }
    
    public Object getParam(String key) {
        return params.get(key);
    }
    
    @Override
    public String toString() {
        String m = "Operation: " + operation;
        m += "\nStatus: " + status;
        
        m += " Parameters:\n ";
        for (String p : params.keySet()) {
            m += "\n" + p + ": " + params.get(p);
        }
        
        return m;
        
    }
}
