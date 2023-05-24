package com.hhkbdev.asyncexceldownload.domain;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

public class Field {

  Schema schema;

  Style style;

  public Field(Schema schema, Style style) {
    this.schema = schema;
    this.style = style;
  }

  public Schema getSchema() {
    return schema;
  }

  public Style getStyle() {
    return style;
  }

  public static class Schema {
    String id;
    String name;
    String type;
    String comment;

    public Schema(String id, String name, String type, String comment) {
      this.id = id;
      this.name = name;
      this.type = type;
      this.comment = comment;
    }

    public String getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getType() {
      return type;
    }

    public String getComment() {
      return comment;
    }
  }

  public static class Style {
    Integer width;
    HorizontalAlignment horizontalAlignment;

    public Integer getWidth() {
      return width;
    }

    public HorizontalAlignment getHorizontalAlignment() {
      return horizontalAlignment;
    }

    public Style(Integer width, HorizontalAlignment horizontalAlignment) {
      this.width = width;
      this.horizontalAlignment = horizontalAlignment;
    }
  }
}
