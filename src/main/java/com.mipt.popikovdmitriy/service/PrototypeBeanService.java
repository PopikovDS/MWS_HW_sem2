package com.mipt.popikovdmitriy.service;

import com.mipt.popikovdmitriy.scope.PrototypeScopedBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
public class PrototypeBeanService {

    private final ObjectProvider<PrototypeScopedBean> prototypeScopedBeanProvider;

    public PrototypeBeanService(ObjectProvider<PrototypeScopedBean> prototypeScopedBeanProvider) {
        this.prototypeScopedBeanProvider = prototypeScopedBeanProvider;
    }

    public PrototypeScopedBean newPrototypeBean() {
        return prototypeScopedBeanProvider.getObject();
    }
}
