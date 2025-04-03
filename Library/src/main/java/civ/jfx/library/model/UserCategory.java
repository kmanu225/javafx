package civ.jfx.library.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UserCategory {

    private static Map<String, ArrayList<Integer>> userCapacities = new HashMap<>();

    /**
     * Set userCapacities Map
     *
     * userCapacities contains :
     * - (String) The user category :
     * - M : Library managers category.
     * - S : Blacklisted users category.
     * - A, B, C : Library users profiles.
     *
     * - (ArrayList<Integer>) capacities :
     * - maxBooks : The maximum number of books a user can borrow.
     * - maxDays : The maximum number of days a user can keep a borrowed book.
     */
    public UserCategory() {
        userCapacities.put("M", new ArrayList<>(Arrays.asList(6, 10)));
        userCapacities.put("S", new ArrayList<>(Arrays.asList(0, 0)));
        userCapacities.put("A", new ArrayList<>(Arrays.asList(4, 7)));
        userCapacities.put("B", new ArrayList<>(Arrays.asList(3, 5)));
        userCapacities.put("C", new ArrayList<>(Arrays.asList(2, 3)));

    }

    public Integer getMaxBooks(String category) {
        return userCapacities.get(category).get(0);
    }

    public Integer getMaxDays(String category) {
        return userCapacities.get(category).get(1);
    }
}
