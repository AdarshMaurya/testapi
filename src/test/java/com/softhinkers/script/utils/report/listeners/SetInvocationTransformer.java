package com.softhinkers.script.utils.report.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static com.softhinkers.script.base.BaseTest.urlList;

public class SetInvocationTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        if (method.getName().contains("fileTest")) {
            iTestAnnotation.setInvocationCount(urlList.size());
        }
    }
}
