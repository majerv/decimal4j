package org.decimal4j.op;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.decimal4j.Decimal;
import org.decimal4j.arithmetic.DecimalArithmetics;
import org.decimal4j.scale.ScaleMetrics;
import org.decimal4j.test.TestSettings;
import org.decimal4j.truncate.OverflowMode;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Unit test for {@link Decimal#negate()}
 */
@RunWith(Parameterized.class)
public class NegateTest extends Abstract1DecimalArgToDecimalResultTest {
	
	public NegateTest(ScaleMetrics scaleMetrics, OverflowMode overflowMode, DecimalArithmetics arithmetics) {
		super(arithmetics);
	}

	@Parameters(name = "{index}: scale={0}")
	public static Iterable<Object[]> data() {
		final List<Object[]> data = new ArrayList<Object[]>();
		for (final ScaleMetrics s : TestSettings.SCALES) {
			data.add(new Object[] {s, OverflowMode.UNCHECKED, s.getTruncatingArithmetics(OverflowMode.UNCHECKED)});
			data.add(new Object[] {s, OverflowMode.CHECKED, s.getTruncatingArithmetics(OverflowMode.CHECKED)});
		}
		return data;
	}

	@Override
	protected String operation() {
		return "-";
	}
	
	@Override
	protected BigDecimal expectedResult(BigDecimal operand) {
		return operand.negate();
	}
	
	@Override
	protected <S extends ScaleMetrics> Decimal<S> actualResult(Decimal<S> operand) {
		if (isUnchecked() && RND.nextBoolean()) {
			return operand.negate();
		}
		return operand.negate(getOverflowMode());
	}
}