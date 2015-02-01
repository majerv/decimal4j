package org.decimal4j.op;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;

import org.decimal4j.Decimal;
import org.decimal4j.arithmetic.DecimalArithmetics;
import org.decimal4j.factory.Factories;
import org.decimal4j.scale.ScaleMetrics;
import org.decimal4j.test.TestSettings;
import org.decimal4j.truncate.OverflowMode;
import org.decimal4j.truncate.TruncationPolicy;
import org.junit.Test;

/**
 * Base class for tests of operands with random and special values. The class
 * provides also some helper methods for subclasses comparing the result of an
 * operation of the {@link Decimal} with the expected result produced by the
 * equivalent operation of the {@link BigDecimal}.
 */
abstract public class AbstractOperandTest {

	protected static final Random RND = new Random();

	protected final DecimalArithmetics arithmetics;
	protected final MathContext mathContextLong64;
	protected final MathContext mathContextLong128;

	/**
	 * Constructor with arithemtics determining scale, rounding mode and
	 * overflow policy.
	 * 
	 * @param arithmetics
	 *            the arithmetics determining scale, rounding mode and overlfow
	 *            policy
	 */
	public AbstractOperandTest(DecimalArithmetics arithmetics) {
		this.arithmetics = arithmetics;
		this.mathContextLong64 = new MathContext(19, arithmetics.getRoundingMode());
		this.mathContextLong128 = new MathContext(39, arithmetics.getRoundingMode());
	}

	protected int getScale() {
		return arithmetics.getScale();
	}
	
	protected ScaleMetrics getScaleMetrics() {
		return arithmetics.getScaleMetrics();
	}

	protected TruncationPolicy getTruncationPolicy() {
		return arithmetics.getTruncationPolicy();
	}
	protected RoundingMode getRoundingMode() {
		return arithmetics.getRoundingMode();
	}
	protected OverflowMode getOverflowMode() {
		return arithmetics.getOverflowMode();
	}

	protected boolean isStandardTruncationPolicy() {
		return arithmetics.getRoundingMode() == TruncationPolicy.DEFAULT.getRoundingMode() && arithmetics.getOverflowMode() == TruncationPolicy.DEFAULT.getOverflowMode();
	}

	protected boolean isRoundingDown() {
		return arithmetics.getRoundingMode() == RoundingMode.DOWN;
	}
	protected boolean isUnchecked() {
		return !arithmetics.getOverflowMode().isChecked();
	}

	protected int getRandomTestCount() {
		return TestSettings.getRandomTestCount();
	}

	@Test
	public void runRandomTest() {
		final int n = getRandomTestCount();
		final ScaleMetrics scaleMetrics = arithmetics.getScaleMetrics();
		for (int i = 0; i < n; i++) {
			runRandomTest(scaleMetrics, i);
		}
	}

	@Test
	public void runSpecialValueTest() {
		final ScaleMetrics scaleMetrics = arithmetics.getScaleMetrics();
		runSpecialValueTest(scaleMetrics);
	}

	/**
	 * Returns the operation string, such as "+", "-", "*", "/", "abs" etc.
	 * 
	 * @return the operation string used in exceptions and log statements
	 */
	abstract protected String operation();

	abstract protected <S extends ScaleMetrics> void runRandomTest(S scaleMetrics, int index);

	abstract protected <S extends ScaleMetrics> void runSpecialValueTest(S scaleMetrics);

	protected long[] getSpecialValues(ScaleMetrics scaleMetrics) {
		return TestSettings.TEST_CASES.getSpecialValuesFor(scaleMetrics);
	}

	protected <S extends ScaleMetrics> Decimal<S> randomDecimal(S scaleMetrics) {
		final long unscaled = RND.nextBoolean() ? RND.nextLong() : RND.nextInt();
		return newDecimal(scaleMetrics, unscaled);
	}
	
	protected static long randomLong(long n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be positive, but was " + n);

        long bits, val;
        do {
            bits = RND.nextLong() >>> 1;
            val = bits % n;
        } while (bits - val + (n-1) < 0);
        return val;
	}

	protected static <S extends ScaleMetrics> Decimal<S> newDecimal(S scaleMetrics, long unscaled) {
		return Factories.valueOf(scaleMetrics).createImmutable(unscaled);
	}

	protected static BigDecimal toBigDecimal(Decimal<?> decimal) {
		return decimal.toBigDecimal();
	}
}