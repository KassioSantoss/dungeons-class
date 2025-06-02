package brcomkassin.dungeonsClass.attribute.regeneration;

public class ScaledRegenerationStrategy implements RegenerationStrategy {

    private final double regenRate;
    private final double minHeal;
    private final double maxHeal;

    public ScaledRegenerationStrategy(double regenRate, double minHeal, double maxHeal) {
        this.regenRate = regenRate;
        this.minHeal = minHeal;
        this.maxHeal = maxHeal;
    }

    @Override
    public double computeRegen(double current, double base) {
        if (current >= base) return 0;

        double missingPercent = 1.0 - (current / base);
        double scaled = base * regenRate * missingPercent;

        return Math.max(minHeal, Math.min(scaled, maxHeal));
    }
}
