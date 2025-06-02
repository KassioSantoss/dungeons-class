package brcomkassin.dungeonsClass.attribute.regeneration;

public class ExponentialRegenerationStrategy implements RegenerationStrategy {

    private final double regenRate;
    private final double min;
    private final double max;
    private final double exponent; // quanto maior, mais acentuada a curva (ex: 1.5 ~ 3.0)

    public ExponentialRegenerationStrategy(double regenRate, double min, double max, double exponent) {
        this.regenRate = regenRate;
        this.min = min;
        this.max = max;
        this.exponent = exponent;
    }

    @Override
    public double computeRegen(double current, double base) {
        if (current >= base) return 0;

        double missing = base - current;
        double missingPercent = missing / base;

        // curva exponencial, ex: (missingPercent ^ exponent)
        double regen = base * regenRate * Math.pow(missingPercent, exponent);

        // clamp entre min e max
        return Math.min(Math.max(regen, min), max);
    }
}
