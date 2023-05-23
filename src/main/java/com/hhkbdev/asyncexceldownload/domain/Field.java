package com.hhkbdev.asyncexceldownload.domain;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class Field {

  Long id;
  String fieldName;
  String fieldType;
  String fieldComment;
  FieldStyle fieldStyle;

  public Field(Long id, String fieldName, String fieldType, String fieldComment, FieldStyle fieldStyle) {
    this.id = id;
    this.fieldName = fieldName;
    this.fieldType = fieldType;
    this.fieldComment = fieldComment;
    this.fieldStyle = fieldStyle;
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

  public FieldStyle getFieldStyle() {
    return fieldStyle;
  }

  public static class FieldStyle {
    HorizontalAlignment horizontalAlignment;

    public HorizontalAlignment getHorizontalAlignment() {
      return horizontalAlignment;
    }

    public FieldStyle(HorizontalAlignment horizontalAlignment) {
      this.horizontalAlignment = horizontalAlignment;
    }
  }
}
