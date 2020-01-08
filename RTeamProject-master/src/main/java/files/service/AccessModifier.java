package files.service;

public enum AccessModifier {
    PRIVATE,
    PUBLIC,
    PROTECTED,
    DEFAULT;

    @Override
    public String toString() {
        switch (this) {
            case PUBLIC:
                return "public";
            case PRIVATE:
                return "private";
            case PROTECTED:
                return "protected";
            case DEFAULT:
            default:
                return "default";
        }
    }
}
