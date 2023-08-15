package com.txxy.demo.test;

import com.virjar.sekiro.business.api.SekiroClient;
import com.virjar.sekiro.business.api.interfaze.ActionHandler;
import com.virjar.sekiro.business.api.interfaze.SekiroRequest;
import com.virjar.sekiro.business.api.interfaze.SekiroResponse;

/**
 * @Author zengch
 * @Date 2023-07-10
 **/
public class SekiroTest {

    public static void main(String[] args) throws InterruptedException {
//        http://127.0.0.1:5612/business/invoke?group=testGroup&action=testAction&param=testparm
        new SekiroClient("testGroup", "testClientId","127.0.0.1",5612).setupSekiroRequestInitializer(((sekiroRequest, handlerRegistry) -> {
            handlerRegistry.registerSekiroHandler(new ActionHandler() {
                //注册一个接口，名为testAction
                @Override
                public String action() {
                    return "testAction";
                }

                @Override
                public void handleRequest(SekiroRequest sekiroRequest, SekiroResponse sekiroResponse) {
                    //接口处理逻辑，这里直接返回"OK"
                    sekiroResponse.success("OK");

                }
            });
        })).start();
        Thread.sleep(10000);
    }
}
