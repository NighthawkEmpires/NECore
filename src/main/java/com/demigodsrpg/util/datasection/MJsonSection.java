package com.demigodsrpg.util.datasection;

import org.bson.Document;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("unchecked")
public class MJsonSection implements DataSection, Serializable {
    private static final long serialVersionUID = -5020712662755828168L;

    // -- PRIVATE FIELDS -- //

    private String MONGO_ID;
    private Document SECTION_DATA;

    // -- CONSTRUCTORS -- //

    private MJsonSection() {
    }

    public MJsonSection(Map<String, Object> data) {
        this(null, data);
    }

    public MJsonSection(String id, Map<String, Object> data) {
        MONGO_ID = id;
        if (data != null) {
            SECTION_DATA = AbstractMongoRegistry.mapToDocument(data);
        } else {
            throw new NullPointerException("Section data cannot be null, is this a valid section?");
        }
    }

    // -- GETTERS -- //

    public Set<String> getKeys() {
        return SECTION_DATA.keySet();
    }

    public Map<String, Object> getValues() {
        return SECTION_DATA;
    }

    public boolean contains(String s) {
        return SECTION_DATA.containsKey(s);
    }

    public boolean isSet(String s) {
        return SECTION_DATA.containsKey(s) && SECTION_DATA.get(s) != null;
    }

    public Object getRaw(String s) {
        return SECTION_DATA.get(s);
    }

    public Object getRaw(String s, Object o) {
        if (contains(s)) {
            return getRaw(s);
        }
        return o;
    }

    public Object getRawNullable(String s) {
        if (contains(s)) {
            return getRaw(s);
        }
        return null;
    }

    public String getString(String s) {
        return getRaw(s).toString();
    }

    public String getString(String s, String s2) {
        return getRaw(s, s2).toString();
    }

    public String getStringNullable(String s) {
        return contains(s) ? getString(s) : null;
    }

    public boolean isString(String s) {
        return getRaw(s) instanceof String;
    }

    public int getInt(String s) {
        return getDouble(s).intValue();
    }

    public int getInt(String s, int i) {
        return getDouble(s, i).intValue();
    }

    public Integer getIntNullable(String s) {
        return contains(s) ? getInt(s) : null;
    }

    public boolean isInt(String s) {
        return getRaw(s) instanceof Integer;
    }

    public boolean getBoolean(String s) {
        return Boolean.parseBoolean(getString(s));
    }

    public boolean getBoolean(String s, boolean b) {
        return Boolean.parseBoolean(getRaw(s, b).toString());
    }

    public Boolean getBooleanNullable(String s) {
        return contains(s) ? getBoolean(s) : null;
    }

    public boolean isBoolean(String s) {
        return getRaw(s) instanceof Boolean;
    }

    public Double getDouble(String s) {
        return Double.parseDouble(getRaw(s).toString());
    }

    public Double getDouble(String s, double v) {
        return Double.parseDouble(getRaw(s, v).toString());
    }

    public Double getDoubleNullable(String s) {
        return contains(s) ? getDouble(s) : null;
    }

    public boolean isDouble(String s) {
        return getRaw(s) instanceof Double;
    }

    public long getLong(String s) {
        return getDouble(s).longValue();
    }

    public long getLong(String s, long l) {
        return getDouble(s, l).longValue();
    }

    public Long getLongNullable(String s) {
        return contains(s) ? getLong(s) : null;
    }

    public boolean isLong(String s) {
        return getRaw(s) instanceof Long;
    }

    public List<Object> getList(String s) {
        return (List) getRaw(s);
    }

    public List<Object> getList(String s, List<Object> objects) {
        return (List) getRaw(s, objects);
    }

    public List<Object> getListNullable(String s) {
        return contains(s) ? getList(s) : null;
    }

    public boolean isList(String s) {
        return getRaw(s) instanceof List;
    }

    public List<String> getStringList(String s) {
        return (List) getRaw(s);
    }

    public List<Double> getDoubleList(String s) {
        return (List) getRaw(s);
    }

    public List<Double> getDoubleListNullable(String s) {
        return contains(s) ? getDoubleList(s) : null;
    }

    public List<Map<String, Object>> getMapList(String s) {
        return (List) getRaw(s);
    }

    public List<Map<String, Object>> getMapListNullable(String s) {
        return contains(s) ? getMapList(s) : null;
    }

    public MJsonSection getSectionNullable(String s) {
        try {
            MJsonSection section = new MJsonSection();
            section.SECTION_DATA = (Document) getRaw(s);
            return section;
        } catch (Exception ignored) {
        }
        return null;
    }

    public boolean isSection(String s) {
        return getRaw(s) instanceof Document;
    }

    // -- MUTATORS -- //

    public void set(String s, Object o) {
        if (!s.equals("_id")) {
            SECTION_DATA.put(s, o);
        }
    }

    public void remove(String s) {
        if (!s.equals("_id")) {
            SECTION_DATA.remove(s);
        }
    }

    public MJsonSection createSection(String s) {
        if (!s.equals("_id")) {
            MJsonSection section = new MJsonSection();
            SECTION_DATA.put(s, section.SECTION_DATA);
            return section;
        }
        throw new IllegalArgumentException("Cannot set a section as '_id'.");
    }

    public MJsonSection createSection(String s, Map<String, Object> map) {
        if (!s.equals("_id")) {
            MJsonSection section = new MJsonSection(map);
            SECTION_DATA.put(s, section.SECTION_DATA);
            return section;
        }
        throw new IllegalArgumentException("Cannot set a section as '_id'.");
    }

    @Override
    public FJsonSection toFJsonSection() {
        return new FJsonSection(AbstractMongoRegistry.documentToMap(SECTION_DATA));
    }

    @Override
    public MJsonSection toMJsonSection() {
        return this;
    }

    public void setMongoId(String id) {
        MONGO_ID = id;
    }

    public String getMongoId() {
        return MONGO_ID;
    }
}
