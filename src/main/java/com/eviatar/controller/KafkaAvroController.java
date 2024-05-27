package com.eviatar.controller;

import com.eviatar.model.Employee;
import com.eviatar.model.User;
import com.eviatar.producer.ProducerAvro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("data")
public class KafkaAvroController {

    @Autowired
    private ProducerAvro producerAvro;

    @PostMapping("/send")
    public String sendMessage(@RequestBody Employee employee){
        producerAvro.sendUser(employee);
        return "message received";
    }
    @PostMapping("/checkLogin")
    public String sendMessage(@RequestBody User user){
        return "all good";
    }
}
