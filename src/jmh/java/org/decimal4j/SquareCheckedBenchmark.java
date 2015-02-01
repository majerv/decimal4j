package org.decimal4j;

import java.io.IOException;
import java.math.BigDecimal;

import org.decimal4j.Decimal;
import org.decimal4j.arithmetic.JDKSupport;
import org.decimal4j.scale.ScaleMetrics;
import org.openjdk.jmh.runner.RunnerException;

/**
 * Micro benchmarks for checked square.
 */
public class SquareCheckedBenchmark extends AbstractUnaryOpIntLongValRoundingBenchmark {

	@Override
	protected <S extends ScaleMetrics> BigDecimal bigDecimals(BenchmarkState state, Values<S> values) {
		try {
			final BigDecimal result = values.bigDecimal1.multiply(values.bigDecimal1, state.mcLong64);
			//check overflow
			JDKSupport.bigIntegerToLongValueExact(result.unscaledValue());
			return result;
		} catch (ArithmeticException e) {
			return null;
		}
	}

	@Override
	protected <S extends ScaleMetrics> Decimal<S> immitableDecimals(BenchmarkState state, Values<S> values) {
		try {
			return values.immutable1.square(state.checkedTruncationPolicy);
		} catch (ArithmeticException e) {
			return null;
		}
	}

	@Override
	protected <S extends ScaleMetrics> Decimal<S> mutableDecimals(BenchmarkState state, Values<S> values) {
		try {
			return values.mutable.set(values.immutable1).square(state.checkedTruncationPolicy);
		} catch (ArithmeticException e) {
			return null;
		}
	}

	@Override
	protected <S extends ScaleMetrics> long nativeDecimals(BenchmarkState state, Values<S> values) {
		try {
			return state.checkedArithmetics.square(values.unscaled1);
		} catch (ArithmeticException e) {
			return 0;
		}
	}

	public static void main(String[] args) throws RunnerException, IOException, InterruptedException {
		run(SquareCheckedBenchmark.class);
	}
}