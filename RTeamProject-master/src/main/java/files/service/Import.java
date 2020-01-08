package files.service;

public class Import {
    private String importedPackage;
    private String importedClass;

    public Import(String importedPackage) {
        this.importedPackage = importedPackage;
    }

    public Import(String importedPackage, String importedClass) {
        this.importedPackage = importedPackage;
        this.importedClass = importedClass;
    }

    public String getImportedPackage() {
        return importedPackage;
    }

    public String getImportedClass() {
        return importedClass;
    }

    @Override
    public String toString() {
        if (importedClass == null || importedClass.equals("")) {
            return importedPackage + ".*";
        } else {
            return importedPackage + '.' + importedClass;
        }
    }
    public static String findPath(String object){
        return null;
    }
}
