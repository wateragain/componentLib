package scanclass.test.service;

import scanclass.Component;

@Component
public class DIData {

    public String data = "abc";

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
