package google.architecture.common.service;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 *  ARouter所谓的服务就是直接或者是间接实现ARouter提供的IProvider接口的类
 */

public interface TestService extends IProvider {
    String sayHello(String name);
}
