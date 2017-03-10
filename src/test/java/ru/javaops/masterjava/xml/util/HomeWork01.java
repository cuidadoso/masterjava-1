package ru.javaops.masterjava.xml.util;

import com.google.common.io.Resources;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.javaops.masterjava.xml.schema.Group;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;

import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

/**
 * Created by apyreev on 10-Mar-17.
 */
public class HomeWork01 {
    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    public static void main(String[] args) throws Exception {
        if(args.length == 1) {
            String project = args[0];
            testHW01(project);
        }
    }

    public static void testHW01(final String projecyName) throws Exception {
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource("payload.xml").openStream());
        List<Group> groups = payload.getGroups().getGroup();
        Set<String> users = new TreeSet<>();
        for (final Group group : groups) {
            if (projecyName.equals(group.getProject().value())) {
                users.addAll(group.getUsers());
            }
        }
        for (final String user : users) {
            System.out.println(user);
        }
    }

    public void testHW01() throws Exception {
        try (InputStream is =
                     Resources.getResource("payload.xml").openStream()) {
            XPathProcessor processor = new XPathProcessor(is);
            NodeList nodes = processor.getElementsByName("Group");
            IntStream.range(0, nodes.getLength()).forEach(
                    i -> {
                        Node node = nodes.item(i);
                        Node project = node.getAttributes().getNamedItem("project");
                        NodeList childes = node.getChildNodes();
                        String projectName = project.getNodeValue();
                        if("masterjava".equals(projectName))
                        {
                            System.out.println(projectName);
                        }
                        //System.out.println(projectName);
                    }
            );
        }
    }

}
