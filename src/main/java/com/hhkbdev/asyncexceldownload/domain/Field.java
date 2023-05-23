package com.hhkbdev.asyncexceldownload.domain;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class Field {

  FieldSchema fieldSchema;

  FieldStyle fieldStyle;

  public Field(FieldSchema fieldSchema, FieldStyle fieldStyle) {
    this.fieldSchema = fieldSchema;
    this.fieldStyle = fieldStyle;
  }

  public FieldSchema getFieldSchema() {
    return fieldSchema;
  }

  public FieldStyle getFieldStyle() {
    return fieldStyle;
  }

  public static class FieldSchema {
    Long id;
    String fieldName;
    String fieldType;
    String fieldComment;

    public FieldSchema(Long id, String fieldName, String fieldType, String fieldComment) {
      this.id = id;
      this.fieldName = fieldName;
      this.fieldType = fieldType;
      this.fieldComment = fieldComment;
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
