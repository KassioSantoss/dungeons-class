package brcomkassin.dungeonsClass.attribute.regeneration;

import brcomkassin.dungeonsClass.attribute.Attribute;

public class RegenerationEngine {

    private final RegenerationStrategy strategy;

    public RegenerationEngine(RegenerationStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean tick(Attribute attribute) {
        double current = attribute.getCurrentValue();
        double base = attribute.getBaseValue();

        double regen = strategy.computeRegen(current, base);
        if (regen <= 0) return false;

        attribute.setCurrentValue(Math.min(current + regen, base));
        return true;
    }
}

