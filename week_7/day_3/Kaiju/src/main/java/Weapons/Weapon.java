package Weapons;


public class Weapon {

    private String type;
    private int damageValue;

    public Weapon(String type, int damageValue) {
        this.type = type;
        this.damageValue = damageValue;
    }

    public String getType() {
        return type;
    }

    public int getDamageValue() {
        return damageValue;
    }
}
