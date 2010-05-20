package barsuift.simLife.j2d.panel;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import barsuift.simLife.Percent;
import barsuift.simLife.environment.Sun;
import barsuift.simLife.environment.SunUpdateCode;


public class SunLuminosityPanel extends JPanel implements ChangeListener, Observer {

    private static final long serialVersionUID = -6102868842517781193L;

    private static final int LUMINOSITY_MIN = 0;

    private static final int LUMINOSITY_MAX = 100;

    private static final MessageFormat LABEL_FORMAT = new MessageFormat("Sun luminosity ({0})");

    private final Sun sun;

    private final JLabel sliderLabel;

    private final JSlider luminositySlider;

    public SunLuminosityPanel(Sun sun) {
        this.sun = sun;
        sun.addObserver(this);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        sliderLabel = createLabel();
        luminositySlider = createSlider();
        add(sliderLabel);
        add(luminositySlider);
    }

    private JSlider createSlider() {
        JSlider luminositySlider = new JSlider(JSlider.HORIZONTAL, LUMINOSITY_MIN, LUMINOSITY_MAX, sun.getLuminosity()
                .getIntValue());
        luminositySlider.addChangeListener(this);
        // Turn on labels at major tick marks.
        luminositySlider.setMajorTickSpacing(20);
        luminositySlider.setMinorTickSpacing(5);
        luminositySlider.setPaintTicks(true);
        luminositySlider.setPaintLabels(true);
        return luminositySlider;
    }

    private JLabel createLabel() {
        JLabel sliderLabel = new JLabel(createLuminosityLabelText(), JLabel.CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderLabel.setMinimumSize(sliderLabel.getPreferredSize());
        return sliderLabel;
    }

    private String createLuminosityLabelText() {
        return LABEL_FORMAT.format(new Object[] { sun.getLuminosity() });
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        int luminosity = (int) source.getValue();
        sun.setLuminosity(new Percent(luminosity));
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg == SunUpdateCode.luminosity) {
            sliderLabel.setText(createLuminosityLabelText());
            luminositySlider.setValue(sun.getLuminosity().getIntValue());
        }
    }

    protected JLabel getLabel() {
        return sliderLabel;
    }

    protected JSlider getSlider() {
        return luminositySlider;
    }

}
