/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 decimal4j (tools4j), Marco Terzer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.decimal4j.arithmetic;

import org.decimal4j.scale.ScaleMetrics;

/**
 * Base class for arithmetic implementations without overflow check for scales other than zero
 */
abstract public class AbstractUncheckedScaleNfArithmetic extends
		AbstractUncheckedArithmetic {

	private final ScaleMetrics scaleMetrics;
	private final int scale;
	private final long one;//10^scale

	public AbstractUncheckedScaleNfArithmetic(ScaleMetrics scaleMetrics) {
		this.scaleMetrics = scaleMetrics;
		this.scale = scaleMetrics.getScale();
		this.one = scaleMetrics.getScaleFactor();
	}

	@Override
	public ScaleMetrics getScaleMetrics() {
		return scaleMetrics;
	}

	@Override
	public int getScale() {
		return scale;
	}

	@Override
	public long one() {
		return one;
	}

	@Override
	public final long fromUnscaled(long unscaledValue, int scale) {
		return Scale.rescale(this, unscaledValue, scale, getScale());
	}

	@Override
	public String toString(long uDecimal) {
		return StringConversion.unscaledToString(this, uDecimal);
	}
}
