package teamproject.skycode.news.notion;

import java.beans.PropertyEditorSupport;

public class StringTrimmerEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        // Convert empty string to null
        if (text == null || text.trim().isEmpty()) {
            setValue(null);
        } else {
            setValue(text.trim());
        }
    }

}
