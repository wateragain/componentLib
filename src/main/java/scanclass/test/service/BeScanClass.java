package scanclass.test.service;

import scanclass.dependencyinjection.Autowired;
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
