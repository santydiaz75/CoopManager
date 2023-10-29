package winUIPackage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.table.DefaultTableCellRenderer;

public class Float2Renderer extends DefaultTableCellRenderer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final DecimalFormat df;
    private final int SCALE = 2;

    public Float2Renderer() {
        df = (DecimalFormat) NumberFormat.getNumberInstance();
        df.setMaximumFractionDigits(SCALE);
        df.setGroupingUsed(false);
    }

    @Override
    public void setValue(Object value) {
        String text = "";
        if (value != null) {
            if (value.getClass() == Float.class) {
                if (!Float.isInfinite((Float) value) && !Float.isNaN((Float) value)) {
                    BigDecimal bd = new BigDecimal((Float) value);
                    bd = bd.setScale(SCALE, BigDecimal.ROUND_HALF_UP);
                    text = df.format(bd.floatValue());
                }
                else {
                    text = "N/A";
                }
            }
        }

        setHorizontalAlignment(RIGHT);
        setText(text+" ");
    }
}

