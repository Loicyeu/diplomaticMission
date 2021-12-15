package fr.loicyeu.diplomaticmission.model;

import org.bukkit.ChatColor;

public enum C {
    DEFAULT(ChatColor.GREEN.toString()),
    ERROR(ChatColor.RED + "[ERROR] " + C.DEFAULT),
    FATAL("" + ChatColor.DARK_RED + ChatColor.BOLD + "[FATAL] Contactez un administrateur.");

    private final String text;

    C(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
