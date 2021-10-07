package app.roddy.space.data.person;

/**
 * A Latent skill is one that is inherent to the person. Think of it as their "natural" skill level that they're born
 * with. Some people are naturally prodigies. Some people are naturally abysmal. Most people are naturally average (by
 * definition.)
 */
public enum LatentSkillLevel {

    PRODIGY (2),
    SUPERIOR (1),
    AVERAGE (0),
    BELOW_AVERAGE(-1),
    ABYSMAL(-2);

    private final int modifier;
    LatentSkillLevel(int modifier) {
        this.modifier = modifier;
    }

    public int getModifier() {
        return modifier;
    }

    public LatentSkillLevel fromModifier(int modifier) {
        return switch (modifier) {
            case 2 -> PRODIGY;
            case 1 -> SUPERIOR;
            case 0 -> AVERAGE;
            case -1 -> BELOW_AVERAGE;
            case -2 -> ABYSMAL;
            default -> AVERAGE;
        };
    }
}
