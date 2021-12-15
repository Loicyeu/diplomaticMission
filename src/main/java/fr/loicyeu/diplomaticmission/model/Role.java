package fr.loicyeu.diplomaticmission.model;

import org.bukkit.ChatColor;

import java.util.*;

public enum Role {
    AMBASSADOR("Ambassadeur", "Survis et complète le coffre au centre de la map.", ChatColor.BLUE),
    GUARD("Garde", "Protège l'ambassadeur à tout prix !", ChatColor.YELLOW),
    AMBITIOUS_GUARD("Garde ambitieux", "Soit le seul garde en vie si l'ambassadeur gagne.", ChatColor.GOLD),
    MURDERER("Assassin", "Tue l'ambassadeur mais attention il est bien protégé.", ChatColor.RED);

    private static final Map<Integer, Role[]> combinations;

    static {
        combinations = new HashMap<>();
        combinations.put(3, new Role[]{AMBASSADOR, MURDERER, GUARD});
        combinations.put(4, new Role[]{AMBASSADOR, MURDERER, GUARD, GUARD});
        combinations.put(5, new Role[]{AMBASSADOR, MURDERER, GUARD, GUARD, AMBITIOUS_GUARD});
        combinations.put(6, new Role[]{AMBASSADOR, MURDERER, MURDERER, GUARD, GUARD, AMBITIOUS_GUARD});
    }

    private final String roleName;
    private final String words;
    private final ChatColor color;

    Role(String roleName, String words, ChatColor color) {
        this.roleName = roleName;
        this.words = words;
        this.color = color;
    }

    public static List<Role> getRandList(int nbPlayer) {
        if (nbPlayer < 3 || nbPlayer > 5) {
            throw new RuntimeException("You need to be at least 3 and 5 maximum");
        }
        List<Role> roles = new ArrayList<>();
        Collections.addAll(roles, combinations.get(nbPlayer));
        Collections.shuffle(roles);
        return roles;
    }

    /**
     * @return The role name colored.
     */
    public String getRoleName() {
        return getRoleName(false);
    }

    /**
     * @param colored If the role must be colored.
     * @return The role name colored or not.
     */
    public String getRoleName(boolean colored) {
        if (colored) {
            return color.toString() + roleName;
        } else {
            return roleName;
        }
    }

    public String getWords() {
        return words;
    }

    public ChatColor getColor() {
        return color;
    }

}