package com.dg.mtms.server.model.request.injector;

import java.lang.reflect.Parameter;

public interface RequestInjector {

    Object inject(Parameter parameter);

}
