package brcomkassin.dungeonsClass.attribute;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attribute implements Cloneable {

    private String name;
    private double baseValue;
    private double currentValue;

    public Attribute(String name, double baseValue) {
        this.name = name;
        this.baseValue = baseValue;
        this.currentValue = baseValue;
    }

    public void increaseBaseValue(double amount) {
        this.baseValue += amount;
    }

    public void increaseBothValues(double amount) {
        this.baseValue += amount;
        this.currentValue += amount;
    }

    public void normalize() {
        this.currentValue = Math.min(this.currentValue, this.baseValue);
    }

    @Override
    public String toString() {
        return name + "=" + currentValue + "/" + baseValue;
    }

    public double getAppliedValue() {
        return baseValue / 1000f;
    }

    public double getCurrentValue() {
        return (double) currentValue;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public void increaseCurrentValue(double amount) {
        this.currentValue = Math.min(this.currentValue + amount, this.baseValue);
    }

    public void decreaseCurrentValue(double amount) {
        setCurrentValue((double) (currentValue - amount));
    }

    @Override
    public Attribute clone() {
        Attribute clone = new Attribute(this.name, this.baseValue);
        clone.setCurrentValue((double) this.currentValue);
        return clone;
    }
}
