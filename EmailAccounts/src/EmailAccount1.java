import components.map.Map;
import components.map.Map1L;

/**
 * Implementation of {@code EmailAccount}.
 *
 * @author Nathan Matteson
 *
 */
public final class EmailAccount1 implements EmailAccount {

    /*
     * Private members --------------------------------------------------------
     */

    // TODO - declare static and instance data members
    private String firstName;
    private String lastName;
    private String eAddress;
    private static Map<String, Integer> accounts = new Map1L<>();

    /*
     * Constructor ------------------------------------------------------------
     */

    /**
     * Constructor.
     *
     * @param firstName
     *            the first name
     * @param lastName
     *            the last name
     */
    public EmailAccount1(String firstName, String lastName) {

        // TODO - fill in body
        this.firstName = firstName;
        this.lastName = lastName;
        String lower = lastName.toLowerCase();
        if (accounts.hasKey(lower)) {
            accounts.replaceValue(lower, accounts.value(lower) + 1);
        } else {
            accounts.add(lower, 1);
        }
        this.eAddress = lower + "." + accounts.value(lower) + "@osu.edu";
    }

    /*
     * Methods ----------------------------------------------------------------
     */

    @Override
    public String name() {

        return this.firstName + " " + this.lastName;
    }

    @Override
    public String emailAddress() {

        return this.eAddress;
    }

    @Override
    public String toString() {

        // TODO - fill in body

        // Added to make skeleton compilable
        return "Name: " + this.firstName + " " + this.lastName + ", Email: "
                + this.eAddress;
    }

}
