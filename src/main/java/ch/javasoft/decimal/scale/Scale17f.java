package ch.javasoft.decimal.scale;

import ch.javasoft.decimal.Decimal17f;
import ch.javasoft.decimal.MutableDecimal17f;

/**
 * Scale class for decimals with 17 {@link #getScale() fraction digit} and
 * {@link #getScaleFactor() scale factor} 100,000,000,000,000,000.
 */
public final class Scale17f extends AbstractScale {
	public static final Scale17f INSTANCE = new Scale17f();

	@Override
	public int getScale() {
		return 17;
	}

	@Override
	public long getScaleFactor() {
		return 100000000000000000L;
	}

	@Override
	public long multiplyByScaleFactor(long factor) {
		return factor * 100000000000000000L;
	}

	@Override
	public long mulloByScaleFactor(int factor) {
		return (factor & LONG_MASK) * 1569325056;//(scaleFactor & LONG_MASK)
	}

	@Override
	public long mulhiByScaleFactor(int factor) {
		return (factor & LONG_MASK) * 23283064;//(scaleFactor >>> 32)
	}

	@Override
	public long divideByScaleFactor(long dividend) {
		return dividend / 100000000000000000L;
	}

	@Override
	public long moduloByScaleFactor(long dividend) {
		return dividend % 100000000000000000L;
	}

	@Override
	public long multiplyByScaleFactorHalf(long dividend) {
		return dividend * 50000000000000000L;
	}

	@Override
	public long divideByScaleFactorHalf(long dividend) {
		return dividend / 50000000000000000L;
	}

	@Override
	public Decimal17f createImmutable(long unscaled) {
		return Decimal17f.valueOfUnscaled(unscaled);
	}

	@Override
	public MutableDecimal17f createMutable(long unscaled) {
		return MutableDecimal17f.unscaled(unscaled);
	}
}