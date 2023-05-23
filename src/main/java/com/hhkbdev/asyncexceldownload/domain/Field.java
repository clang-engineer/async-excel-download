package com.hhkbdev.asyncexceldownload.domain;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class Field {

  Long id;
  String fieldName;
  String fieldType;
  String fieldComment;

  HorizontalAlignment horizontalAlignment;

  public Field(Long id, String fieldName, String fieldType, String fieldComment, HorizontalAlignment alignment) {
    this.id = id;
    this.fieldName = fieldName;
    this.fieldType = fieldType;
    this.fieldComment = fieldComment;
    this.horizontalAlignment = alignment;
  }

  public Long getId() {
    return id;
  }

  public String getFieldName() {
    return fieldName;
  }

  public String getFieldType() {
    return fieldType;
  }

  public String getFieldComment() {
    return fieldComment;
  }

  public HorizontalAlignment getHorizontalAlignment() {
    return horizontalAlignment;
  }
}
