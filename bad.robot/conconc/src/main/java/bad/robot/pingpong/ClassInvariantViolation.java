package bad.robot.pingpong;

/**
 * Methods of the class should preserve any class invariants. The class invariant constrains the state stored in the
 * object and should be constantly maintained between calls to public methods. An object invariant, or representation
 * invariant, is a programming construct consisting of a set of invariant properties that remain uncompromised
 * regardless of the state of the object. This ensures that the object will always meet predefined conditions,
 * and that methods may, therefore, always reference the object without the risk of making inaccurate presumptions.
 *
 * This exception is used to explicitly capture invariants and force run time exceptions if they are violated. Use
 * this rather than the <code>assert</code> keyword if you don't want to conditionally check invariants (ie, you wont
 * need to -enableassertions flag on the VM).
 *
 * @see http://en.wikipedia.org/wiki/Class_invariant
 */
public class ClassInvariantViolation extends UncheckedException {
    public ClassInvariantViolation() {
        super();
    }

    public ClassInvariantViolation(String message) {
        super(message);
    }

    public ClassInvariantViolation(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassInvariantViolation(Throwable cause) {
        super(cause);
    }
}
