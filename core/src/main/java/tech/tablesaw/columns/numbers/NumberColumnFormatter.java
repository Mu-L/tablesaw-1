package tech.tablesaw.columns.numbers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import tech.tablesaw.columns.ColumnFormatter;

public class NumberColumnFormatter extends ColumnFormatter {

  private final NumberFormat format;

  public static NumberColumnFormatter percent(int fractionalDigits) {
    NumberFormat format = NumberFormat.getPercentInstance();
    format.setGroupingUsed(false);
    format.setMinimumFractionDigits(fractionalDigits);
    format.setMaximumFractionDigits(fractionalDigits);
    return new NumberColumnFormatter(format);
  }

  /** Returns a formatter that prints floating point numbers with all precision */
  public static NumberColumnFormatter floatingPointDefault() {
    NumberFormat format =
        new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.getDefault()));
    format.setMaximumFractionDigits(340);
    format.setMaximumIntegerDigits(340);
    format.setGroupingUsed(false);
    return new NumberColumnFormatter(format);
  }

  /** Formats numbers using java default, so sometimes in scientific notation, sometimes not */
  public static NumberColumnFormatter standard() {
    return new NumberColumnFormatter();
  }

  public static NumberColumnFormatter ints() {
    NumberFormat format = new DecimalFormat();
    format.setGroupingUsed(false);
    format.setMinimumFractionDigits(0);
    format.setMaximumFractionDigits(0);
    return new NumberColumnFormatter(format);
  }

  public static NumberColumnFormatter intsWithGrouping() {
    NumberFormat format = new DecimalFormat();
    format.setGroupingUsed(true);
    format.setMinimumFractionDigits(0);
    format.setMaximumFractionDigits(0);
    return new NumberColumnFormatter(format);
  }

  public static NumberColumnFormatter fixedWithGrouping(int fractionalDigits) {
    NumberFormat format = new DecimalFormat();
    format.setGroupingUsed(true);
    format.setMinimumFractionDigits(fractionalDigits);
    format.setMaximumFractionDigits(fractionalDigits);
    return new NumberColumnFormatter(format);
  }

  public static NumberColumnFormatter currency(String language, String country) {
    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale(language, country));
    return new NumberColumnFormatter(format);
  }

  public NumberColumnFormatter() {
    super("");
    this.format = null;
  }

  public NumberColumnFormatter(NumberFormat format) {
    super("");
    this.format = format;
  }

  public NumberColumnFormatter(NumberFormat format, String missingString) {
    super(missingString);
    this.format = format;
  }

  public NumberColumnFormatter(String missingString) {
    super(missingString);
    this.format = null;
  }

  public String format(long value) {
    if (isMissingValue(value)) {
      return getMissingString();
    }
    if (format == null) {
      return String.valueOf(value);
    }
    return format.format(value);
  }

  public String format(double value) {
    if (isMissingValue(value)) {
      return getMissingString();
    }
    if (format == null) {
      return String.valueOf(value);
    }
    return format.format(value);
  }

  @Override
  public String toString() {
    return "NumberColumnFormatter{"
        + "format="
        + format
        + ", missingString='"
        + getMissingString()
        + '\''
        + '}';
  }

  private boolean isMissingValue(double value) {
    return value != value;
  }
}
