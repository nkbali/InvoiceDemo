package com.demo.invoice.controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Service
public class TestCallable implements Callable<String> {

    @Autowired
    Logger logger;

    private long waitTime = 1000;

    @Override
    public String call() throws Exception {
        Thread.sleep(waitTime);
        logger.info(Thread.currentThread().getName());
        return Thread.currentThread().getName();
    }
}
