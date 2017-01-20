package nl.openweb.iot.wio.domain;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Service;

import nl.openweb.iot.wio.WioException;
import nl.openweb.iot.wio.db.GroveBean;
import nl.openweb.iot.wio.domain.grove.GenericGrove;
import nl.openweb.iot.wio.rest.NodeResource;

@Service
public class GroveFactory {

    private NodeResource nodeResource;

    private Map<String, Class<Grove>> typeToClassMap = new HashMap<>();

    @Autowired
    public GroveFactory(NodeResource nodeResource) {
        this.nodeResource = nodeResource;
    }

    @PostConstruct
    public void init() throws ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);
        provider.addIncludeFilter(new AssignableTypeFilter(GenericGrove.class));

        Set<BeanDefinition> components = provider.findCandidateComponents("nl/openweb/iot/wio/domain/grove");
        for (BeanDefinition component : components) {
            Class cls = Class.forName(component.getBeanClassName());
            Annotation annotation = cls.getAnnotation(Type.class);
            if (annotation instanceof Type) {
                String type = ((Type) annotation).value();
                typeToClassMap.put(type, cls);
            }
        }
    }

    Grove createGrove(GroveBean groveBean, Node parent) throws WioException {
        try {
            Grove result;
            Class<Grove> groveClass = typeToClassMap.get(groveBean.getType());
            if (groveClass != null) {
                Constructor<Grove> constructor = groveClass.getConstructor(GroveBean.class, Node.class, NodeResource.class);
                result = constructor.newInstance(groveBean, parent, nodeResource);
            } else {
                result = new GenericGrove(groveBean, parent, nodeResource);
            }
            return result;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new WioException(e.getMessage());
        }
    }
}
