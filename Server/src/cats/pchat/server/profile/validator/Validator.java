package cats.pchat.server.profile.validator;

/**
 * Josh
 * 14/08/13
 * 5:09 PM
 */
public final class Validator {

    private Validator(){}

    private static boolean containsSymbols(final String s){
        for(final char c : s.toCharArray())
            if(!Character.isLetterOrDigit(c))
                return true;
        return false;
    }

    public static String checkName(final String name){
        if(containsSymbols(name))
            return "Name cannot contain any symbols";
        return name.length() >= 5 && name.length() <= 20 ? null : "Name length must be between [5, 20]";
    }

    public static String checkPass(final String pass){
        if(containsSymbols(pass))
            return "Pass cannot contain any symbols";
        return pass.length() >= 5 && pass.length() <= 20 ? null : "Pass length must be between [5, 20]";
    }
}
