package com.ancient.dubbo.provider.example.client;

import com.ancient.dubbo.api.AncientDubboApi;
import org.apache.dubbo.config.annotation.Service;

@Service
public class AncientDubboProviderApiImpl implements AncientDubboApi {

    public String helloword(String str) {
        return "AncientDubboProviderApiImpl:" + str;
    }

}
