package com.demo.invoice.controllers;

import org.aspectj.weaver.ast.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Callable;

public class TestCallable implements Callable<String> {

    long waitTime;

    public TestCallable(long waitTime){
        this.waitTime = waitTime;
    }
    @Override
    public String call() throws Exception {
        Thread.sleep(waitTime);
        System.out.println(Thread.currentThread().getName());
        return Thread.currentThread().getName();
    }
}
