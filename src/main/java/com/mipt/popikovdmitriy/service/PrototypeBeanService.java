package com.mipt.popikovdmitriy.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.mipt.popikovdmitriy.scope.PrototypeScopedBean;

@Service
public class PrototypeBeanService {

  private final ObjectProvider<PrototypeScopedBean> prototypeBeanProvider;

  public PrototypeBeanService(ObjectProvider<PrototypeScopedBean> prototypeBeanProvider) {
    this.prototypeBeanProvider = prototypeBeanProvider;
  }

  public PrototypeScopedBean newPrototypeBean() {
    return prototypeBeanProvider.getObject();
  }
}
