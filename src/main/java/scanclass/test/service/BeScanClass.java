package scanclass.test.service;

import dependencyinjection.Autowired;
import dependencyinjection.Scope;
import scanclass.Component;

@Component
//@Scope("prototype")
public class BeScanClass {

    @Autowired
    private DIData diData;

    public void test(){
        if(diData != null){
            System.out.println("DIData:" + diData.getData());
        }
    }
}
