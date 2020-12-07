package sample.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties({"description"})
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include= JsonTypeInfo.As.PROPERTY, property="@class")
public class Drinks {
    public int volume; //Общее свойство объем
    public Integer id = null;

    public Drinks() {};

    public Drinks(int v){ this.volume=v; } //Конструктор

    public int getVolume(){return this.volume;}
    public void setVolume(int v){this.volume=v;}

    @Override
    public String toString() {
        return String.format("Объем = " + this.getVolume());
    }

    public String getDescription() {
        return "";
    }

    public String getDrinkType() {
        return "";
    }

}
