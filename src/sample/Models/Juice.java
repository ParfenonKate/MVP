package sample.Models;

public class Juice  extends Drinks{

    public enum Type {Apple, Orange, Cherry;}
    public Boolean hasPulp;//Наличие мякоти

    public Type type; //Тип

    public Juice() {};

    public Juice(int v, boolean p, Type t) {
        super(v);
        this.hasPulp=p;
        this.type=t;
    }

    public boolean getHasPulp(){return this.hasPulp;}
    public void setHasPulp(boolean p){this.hasPulp = p;}

    public Type getType(){return this.type;}
    public void setType(Type t){this.type=t;}

    @Override
    public String getDescription() {
        String hasPulpString = this.hasPulp ? "С мякотью" : "Без мякоти";

        String typeString = "";
        switch (this.type)
        {
            case Orange:
                typeString = "Апельсиновый";
                break;
            case Cherry:
                typeString = "Вишневый";
                break;
            case Apple:
                typeString = "Яблочный";
                break;
        }

        return String.format("%s.%s",typeString ,hasPulpString);
    }
    @Override
    public String getDrinkType() { return String.format("Сок"); }
}
