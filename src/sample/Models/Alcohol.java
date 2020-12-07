package sample.Models;

public class Alcohol extends Drinks{
    public enum Type {bear, wine, vodka;}

    public int strength; //Крепость

    public Type type; //Тип

    public Alcohol() {};

    public Alcohol(int v,int s,Type t) {
        super(v);
        this.strength=s;
        this.type=t;
    }

    public int getStrength(){return this.strength;}
    public void setStrength(int s){this.strength = s;}

    public Type getType(){return this.type;}
    public void setType(Type t){this.type=t;}

    @Override
    public String getDescription() {

        String typeString = "";
        switch (this.type)
        {
            case vodka:
                typeString = "Водка";
                break;
            case bear:
                typeString = "Пиво";
                break;
            case wine:
                typeString = "Вино";
                break;
        }

        return String.format("%s.Крепость: %s", typeString,strength);
    }

    @Override
    public String getDrinkType() { return String.format("Алкоголь"); }
}
