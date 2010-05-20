package barsuift.simLife;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PercentState {

    private BigDecimal value;

    public PercentState() {
        value = new BigDecimal(0);
    }

    public PercentState(BigDecimal value) {
        this.value = value;
    }

    public PercentState(PercentState copy) {
        value = copy.value;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Percent toPercent() {
        return new Percent(value);
    }

    @Override
    public String toString() {
        return "PercentState [value=" + value + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PercentState other = (PercentState) obj;
        if (value == null) {
            if (other.value != null)
                return false;
        } else
            if (!value.equals(other.value))
                return false;
        return true;
    }

}
