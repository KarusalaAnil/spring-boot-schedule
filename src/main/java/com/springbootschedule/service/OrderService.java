package com.springbootschedule.service;

import com.springbootschedule.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @PostConstruct
    public void intiDataDB(){


    }
}
