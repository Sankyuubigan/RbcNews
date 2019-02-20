package nildencorp.apps.rbcnews.model;

import androidx.room.TypeConverter;

public class Converters {

    @TypeConverter
    public Source storedStringToSource(String value) {
        String[] source=value.split("\\s*,\\s*");
        return new Source(source[0],source[1]);
    }

    @TypeConverter
    public String sourceToStoredString(Source cl) {
        String value = "";

        value += cl.getId() + ",";
        value += cl.getName();
        return value;
    }
}
