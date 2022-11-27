package com.stackroute.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="element")
@Data
public class Element {

    @Id
    String id;
    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    int orderElement;
    boolean bold;
    boolean canHaveAlternateForm;
    boolean canHaveDisplayHorizontal;
    boolean canHaveOptionCorrect;
    boolean canHaveOptionValue;
    boolean canHavePageBreakBefore;
    boolean canPopulateFromApi;
    boolean canHaveAnswer;
    String field_name;
    String label;
    String content;
    String element;
    boolean italic;
    boolean required;
    String text;





}
