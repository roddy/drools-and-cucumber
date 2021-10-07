package app.roddy.space.data.person;

public enum LearnedSkillLevel {

    DOCTORATE(5),
    MASTER(4),
    UNIVERSITY(3),
    TRADE_SCHOOL(2),
    ELEMENTARY(1),
    NONE(0);

    private final int modifier;
    private LearnedSkillLevel(int modifier) {
        this.modifier = modifier;
    }

    public int getModifier() {
        return modifier;
    }

    public static LearnedSkillLevel fromModifier(int modifier) {
        if(modifier < 0) {
            modifier = 0;
        } else if (modifier > 5) {
            modifier = 5;
        }
        switch(modifier) {
            case 5: return DOCTORATE;
            case 4: return MASTER;
            case 3: return UNIVERSITY;
            case 2: return TRADE_SCHOOL;
            case 1: return ELEMENTARY;
            case 0: return NONE;
            default: return NONE;
        }
    }

}
