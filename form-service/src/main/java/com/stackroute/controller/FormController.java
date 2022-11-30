package com.stackroute.controller;


import com.stackroute.dao.Element;
import com.stackroute.dao.Form;
import com.stackroute.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping("/form")

public class FormController {

    @Autowired
    FormService formService;

    @PostMapping(value = "/add",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<Element> addForm(@RequestBody List<Element> elementList,@RequestParam(required = false) String name) {
        return formService.addForm(elementList,name);
    }


    @GetMapping(value = "/get")
    public ResponseEntity<List<Element>> getForm(@RequestParam(required = false) int id) {
        List<Element> elementList=formService.getForm(id);
        return ResponseEntity.ok(elementList);
    }

    @PutMapping(value = "/update")
    public void updateForm(@RequestParam(required = false) int id,@RequestParam(required = false) String name,@RequestBody List<Element> elementList) {
        formService.updateForm(id,name,elementList);
    }

    @DeleteMapping(value = "/delete")
    public void deleteForm(@RequestParam(required = false) String id) {
       formService.deleteForm(id);
    }

    @GetMapping(value = "/getallforms")
    public ResponseEntity<List<Form>> getAllForms() {
        List<Form> formList=formService.getAllForms();
        return ResponseEntity.ok(formList);
    }
    @GetMapping(value = "/status")
    public void updateStatus(@RequestParam(required = false) int id) {
        formService.updateFormStatus(id);
    }



}
