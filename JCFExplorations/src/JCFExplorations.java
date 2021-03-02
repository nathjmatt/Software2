import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import components.map.Map;
import components.naturalnumber.NaturalNumber;

/**
 * Simple class to experiment with the Java Collections Framework and how it
 * compares with the OSU CSE collection components.
 *
 * @author Put your name here
 *
 */
public final class JCFExplorations {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private JCFExplorations() {
    }

    /**
     * Raises the salary of all the employees in {@code map} whose name starts
     * with the given {@code initial} by the given {@code raisePercent}.
     *
     * @param map
     *            the name to salary map
     * @param initial
     *            the initial of names of employees to be given a raise
     * @param raisePercent
     *            the raise to be given as a percentage of the current salary
     * @updates map
     * @requires [the salaries in map are positive] and raisePercent > 0
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [the salaries of the employees in map whose names start with the given
     *  initial have been increased by raisePercent percent (and truncated to
     *  the nearest integer); all other employees have the same salary]
     * </pre>
     */
    public static void giveRaise(components.map.Map<String, Integer> map,
            char initial, int raisePercent) {
        assert map != null : "Violation of: map is not null";
        assert raisePercent > 0 : "Violation of: raisePercent > 0";

        // TODO - fill in body
//        Iterator<Map.Pair<String, Integer>> it = map.iterator();
//        while (it.hasNext()) {
//            Map.Pair<String, Integer> p = it.next();
//            if (p.key().charAt(0) == initial) {
//                map.replaceValue(p.key(),
//                        raisePercent * p.value() / 100 + p.value());
//            }
//        }
        Map<String, Integer> temp = map.newInstance();
        temp.transferFrom(map);
        while (temp.size() > 0) {
            Map.Pair<String, Integer> p = temp.removeAny();
            if (p.key().charAt(0) == initial) {
                map.add(p.key(), raisePercent * p.value() / 100 + p.value());
            } else {
                map.add(p.key(), p.value());
            }
        }

    }

    /**
     * Raises the salary of all the employees in {@code map} whose name starts
     * with the given {@code initial} by the given {@code raisePercent}.
     *
     * @param map
     *            the name to salary map
     * @param initial
     *            the initial of names of employees to be given a raise
     * @param raisePercent
     *            the raise to be given as a percentage of the current salary
     * @updates map
     * @requires <pre>
     * [the salaries in map are positive] and raisePercent > 0 and
     * [the dynamic types of map and of all objects reachable from map
     *  (including any objects returned by operations (such as
     *  entrySet() and, from there, iterator()), and so on,
     *  recursively) support all optional operations]
     * </pre>
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [the salaries of the employees in map whose names start with the given
     *  initial have been increased by raisePercent percent (and truncated to
     *  the nearest integer); all other employees have the same salary]
     * </pre>
     */
    public static void giveRaise(java.util.Map<String, Integer> map,
            char initial, int raisePercent) {
        assert map != null : "Violation of: map is not null";
        assert raisePercent > 0 : "Violation of: raisePercent > 0";

        // TODO - fill in body
        Set<Entry<String, Integer>> s = map.entrySet();
        Iterator<Entry<String, Integer>> it = s.iterator();
        while (it.hasNext()) {
            Entry<String, Integer> p = it.next();
            if (p.getKey().charAt(0) == initial) {
                p.setValue(raisePercent * p.getValue() / 100 + p.getValue());
            }
        }
    }

    /**
     * Increments by 1 every element in the given {@code Set}.
     *
     * @param set
     *            the set whose elements to increment
     * @updates set
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [set is the set of integers that are elements of #set incremented by 1]
     * </pre>
     */
    public static void incrementAll(components.set.Set<NaturalNumber> set) {
        assert set != null : "Violation of: set is not null";

        // TODO - fill in body
//        Iterator<Set<NaturalNumber>>
        components.set.Set<NaturalNumber> temp = set.newInstance();
        temp.transferFrom(set);
        while (temp.size() > 0) {
            NaturalNumber num = temp.removeAny();
            num.increment();
            set.add(num);
        }
    }

    /**
     * Increments by 1 every element in the given {@code Set}.
     *
     * @param set
     *            the set whose elements to increment
     * @updates set
     * @requires <pre>
     * [the dynamic types of set and of all objects reachable from set
     *  (including any objects returned by operations (such as iterator()), and
     *  so on, recursively) support all optional operations]
     * </pre>
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [set is the set of integers that are elements of #set incremented by 1]
     * </pre>
     */
    public static void incrementAll(java.util.Set<NaturalNumber> set) {
        assert set != null : "Violation of: set is not null";

        // TODO - fill in body
        Iterator<NaturalNumber> it = set.iterator();
        Set<NaturalNumber> tempSet = new HashSet<>();
        while (it.hasNext()) {
            NaturalNumber num = it.next();
            it.remove();
            num.increment();
            tempSet.add(num);
        }
        set.addAll(tempSet);
        tempSet.clear();

    }

}
