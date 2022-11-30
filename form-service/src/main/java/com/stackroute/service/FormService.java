package com.stackroute.service;

import com.stackroute.dao.Element;
import com.stackroute.dao.Form;
import com.stackroute.repository.ElementRepository;
import com.stackroute.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FormService {

    @Autowired
    FormRepository formRepository;

    @Autowired
    ElementRepository elementRepository;


    public List<Element> addForm(List<Element> elementList,String name){
        Form form=new Form();
        form.setName(name);
        form.setActive(false);
        form.setLatestUpdate(String.valueOf(new Date()));
        Form myForm=formRepository.save(form);
        AtomicInteger order= new AtomicInteger(1);
        elementList.stream().forEach(element -> {element.setForm(myForm);
            element.setOrderElement(order.get());
            order.addAndGet(1);
        elementRepository.save(element);});
        return elementList;

    }

    public List<Element> getForm(int formId){
       List<Element> elementList=elementRepository.findAllByFormIdOrderByOrderElement(formId);
       elementList.stream().forEach(element -> element.setForm(null));
        return elementList;

    }
    public void updateForm( int id,String name, List<Element> elementList){
        deleteForm(String.valueOf(id));
        Form form=new Form();
        form.setName(name);
        form.setLatestUpdate(String.valueOf(new Date()));
        Form myForm=formRepository.save(form);
        AtomicInteger order= new AtomicInteger(1);
        elementList.stream().forEach(element -> {
            element.setForm(myForm);
            element.setOrderElement(order.get());
            order.addAndGet(1);
            elementRepository.save(element);});
    }
    public void updateFormStatus( int id){
        Form form=formRepository.findById(id).get();
        form.setActive(!form.isActive());
        form.setLatestUpdate(String.valueOf(new Date()));
        formRepository.save(form);
    }

    public void deleteForm(String  formId){
        formRepository.deleteById(Integer.valueOf(formId));

    }
    public List<Form> getAllForms(){
    List<Form> formList=formRepository.findAll();
    formList.stream().forEach(form -> form.setElementList(null));
        return formList;
    }




}
