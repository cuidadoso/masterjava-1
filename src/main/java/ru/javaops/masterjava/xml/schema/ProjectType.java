
package ru.javaops.masterjava.xml.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for projectType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="projectType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="topjava"/>
 *     &lt;enumeration value="masterjava"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "projectType", namespace = "http://javaops.ru")
@XmlEnum
public enum ProjectType {

    @XmlEnumValue("topjava")
    TOPJAVA("topjava"),
    @XmlEnumValue("masterjava")
    MASTERJAVA("masterjava");
    private final String value;

    ProjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProjectType fromValue(String v) {
        for (ProjectType c: ProjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
