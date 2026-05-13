package com.mipt.popikovdmitriy.service;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import com.mipt.popikovdmitriy.scope.PrototypeScopedBean;

/**
 * Service that acts as a factory for obtaining new instances of
 * {@link com.mipt.popikovdmitriy.scope.PrototypeScopedBean}.
 *
 * <p>
 * Uses Spring's {@link org.springframework.beans.factory.ObjectProvider} to
 * request fresh prototype-scoped beans on demand, ensuring that each call to
 * {@link #newPrototypeBean()} returns a distinct instance.</p>
 */
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
