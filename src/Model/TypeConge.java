package Model;

public enum TypeConge {
    Conge_Paye("Conge Paye"),
    Conge_Non_Paye("Conge Non Paye"),
    Conge_Maladie("Conge Maladie");

    private final String description;

    TypeConge(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static TypeConge fromDescription(String description) {
        for (TypeConge type : values()) {
            if (type.getDescription().equalsIgnoreCase(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with description: " + description);
    }

    @Override
    public String toString() {
        return description;
    }
}