package org.decimal4j.arithmetic;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.decimal4j.api.DecimalArithmetic;
import org.decimal4j.scale.ScaleMetrics;
import org.decimal4j.scale.Scales;
import org.decimal4j.truncate.TruncationPolicy;

/**
 * Base class for all arithmetic implementations. Only operations are
 * implemented that are common irrespective of {@link #getScale() scale},
 * {@link RoundingMode rounding mode} and {@link #getOverflowMode() overflow
 * mode}.
 */
abstract public class AbstractArithmetic implements DecimalArithmetic {

	@Override
	public int getScale() {
		return getScaleMetrics().getScale();
	}
	
	@Override
	public long one() {
		return getScaleMetrics().getScaleFactor();
	}
	
	@Override
	public TruncationPolicy getTruncationPolicy() {
		return getOverflowMode().getTruncationPolicyFor(getRoundingMode());
	}

	@Override
	public final int signum(long uDecimal) {
		return (int) ((uDecimal >> 63) | (-uDecimal >>> 63));
	}

	@Override
	public final int compare(long uDecimal1, long uDecimal2) {
		return (uDecimal1 < uDecimal2) ? -1 : ((uDecimal1 == uDecimal2) ? 0 : 1);
	}

	@Override
	public BigDecimal toBigDecimal(long uDecimal) {
		return BigDecimal.valueOf(uDecimal, getScale());
	}

	@Override
	public BigDecimal toBigDecimal(long uDecimal, int scale) {
		final ScaleMetrics thisMetrics = getScaleMetrics();
		final int thisScale = thisMetrics.getScale();
		if (scale == thisScale) {
			return toBigDecimal(uDecimal);
		}
		if (scale < thisScale) {
			final int diff = thisScale - scale;
			if (diff <= 18) {
				final ScaleMetrics diffMetrics = Scales.valueOf(diff);
				final long rescaled = diffMetrics.getArithmetic(getRoundingMode()).divideByPowerOf10(uDecimal, diff);
				return BigDecimal.valueOf(rescaled, scale);
			}
		} else {
			//does it fit in a long?
			final int diff = scale - thisScale;
			if (diff <= 18) {
				final ScaleMetrics diffMetrics = Scales.valueOf(diff);
				if (uDecimal >= diffMetrics.getMinIntegerValue() && uDecimal <= diffMetrics.getMaxIntegerValue()) {
					final long rescaled = diffMetrics.multiplyByScaleFactor(uDecimal);
					return BigDecimal.valueOf(rescaled, scale);
				}
			}
		}
		//let the big decimal deal with such large numbers then
		return BigDecimal.valueOf(uDecimal, thisScale).setScale(scale, getRoundingMode());
	}
	
	@Override
	public long avg(long a, long b) {
		return Avg.avg(this, a, b);
	}

}