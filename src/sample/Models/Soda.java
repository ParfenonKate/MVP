package sample.Models;

public class Soda extends Drinks{

    public enum Type {Cola, Sprite, Fanta;}

    public int bubbles; //Количесвто пузыриков

    public Type type; //Тип

    public Soda() {};

    public Soda(int v, int b, Type t) {
        super(v);
        this.bubbles=b;
        this.type=t;
    }

    public int getBubbles(){return this.bubbles;}
    public void setBubbles(int b){this.bubbles = b;}

    public Type getType(){return this.type;}
    public void setType(Type t){this.type=t;}

    @Override
    public String getDescription() {
        return String.format("%s.Количество пузыриков: %s", type,bubbles);
    }
    @Override
    public String getDrinkType() { return String.format("Газировка"); }
}
