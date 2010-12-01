package tree;

/**
 * Number is the superclass to everything that has a known Value.
 * This includes Decimal, Fraction, Matrix(if this ends up being able
 * to contain Variables, this will extend from something else), Irrational,
 * and anything else that could be contained in the tree that will represent the
 * Coefficient in a Term.
 * @author jason
 *
 */
public abstract class Number extends Value{

}
