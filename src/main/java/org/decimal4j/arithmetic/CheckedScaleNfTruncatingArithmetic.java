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

import java.math.RoundingMode;

import org.decimal4j.scale.ScaleMetrics;
import org.decimal4j.truncate.DecimalRounding;

/**
 * Arithmetic implementation without rounding but with overflow check for scales
 * other than zero. An exception is thrown if an operation leads to an overflow.
 */
public final class CheckedScaleNfTruncatingArithmetic extends
		AbstractCheckedScaleNfArithmetic {

	public CheckedScaleNfTruncatingArithmetic(ScaleMetrics scaleMetrics) {
		super(scaleMetrics);
	}

	@Override
	public RoundingMode getRoundingMode() {
		return RoundingMode.DOWN;
	}

	@Override
	public long addLong(long uDecimal, long lValue) {
		return Checked.addDecimalAndLong(this, uDecimal, lValue);
	}

	@Override
	public long subtractLong(long uDecimal, long lValue) {
		return Checked.subtractLongFromDecimal(this, uDecimal, lValue);
	}

	@Override
	public long multiply(long uDecimal1, long uDecimal2) {
		return Mul.multiplyChecked(this, uDecimal1, uDecimal2);
	}

	@Override
	public long square(long uDecimal) {
		return Mul.squareChecked(this, uDecimal);
	}

	@Override
	public long divide(long uDecimalDividend, long uDecimalDivisor) {
		return Div.divideChecked(this, uDecimalDividend, uDecimalDivisor);
	}

	@Override
	public long pow(long uDecimal, int exponent) {
		return Pow.pow(this, DecimalRounding.DOWN, uDecimal, exponent);
	}

	@Override
	public long avg(long a, long b) {
		return Avg.avg(a, b);
	}

	@Override
	public long sqrt(long uDecimal) {
		return Sqrt.sqrt(this, uDecimal);
	}

	@Override
	public long divideByLong(long uDecimalDividend, long lDivisor) {
		return Checked.divideByLong(this, uDecimalDividend, lDivisor);
	}

	@Override
	public long divideByPowerOf10(long uDecimal, int positions) {
		return Pow10.divideByPowerOf10Checked(this, uDecimal, positions);
	}

	@Override
	public long invert(long uDecimal) {
		return Invert.invert(this, uDecimal);
	}

	@Override
	public long multiplyByPowerOf10(long uDecimal, int positions) {
		return Pow10.multiplyByPowerOf10Checked(this, uDecimal, positions);
	}

	@Override
	public long shiftLeft(long uDecimal, int positions) {
		return Shift.shiftLeftChecked(this, DecimalRounding.DOWN, uDecimal, positions);
	}

	@Override
	public long shiftRight(long uDecimal, int positions) {
		return Shift.shiftRightChecked(this, DecimalRounding.DOWN, uDecimal, positions);
	}

	@Override
	public long round(long uDecimal, int precision) {
		return Round.round(this, uDecimal, precision);
	}

	@Override
	public long fromFloat(float value) {
		return FloatConversion.floatToUnscaled(this, DecimalRounding.DOWN, value);
	}

	@Override
	public long fromDouble(double value) {
		return DoubleConversion.doubleToUnscaled(this, DecimalRounding.DOWN, value);
	}

	@Override
	public long parse(String value) {
		return StringConversion.parseUnscaledDecimal(this, DecimalRounding.DOWN, value);
	}

}
