package br.com.caelum.stella.nfe.builder.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import net.vidageek.mirror.Mirror;
import br.com.caelum.stella.nfe.modelo.*;

/**
 * @author jonasabreu
 * 
 *         Objeto que usa um pouco de magia preta pra gerar um builder a partir
 *         dos fields de um objeto.
 */
final public class JAXBBuilderCodeGenerator {

	private static boolean enableFileGeneration = false;

	public static void main(final String[] args) throws IOException {
		Class<?>[] sources = { Avulsa.class};
		generateBuilderFor(sources);
	}

	private static void generateBuilderFor(Class<?>... sources)
			throws FileNotFoundException {
		for (Class<?> type : sources) {
			generateBuilderFor(type);
		}
	}

	private static void generateBuilderFor(Class<?> source)
			throws FileNotFoundException {
		ClassObject clazz = new ClassObject(source);

		List<Field> fields = Mirror.on(source).reflectAll().fields();
		for (Field field : fields) {
			String name = field.getName();
			XmlElement annotation = field.getAnnotation(XmlElement.class);
			if (annotation != null
					&& !"##default".equals(annotation.name())) {
				name = annotation.name();
			}
			clazz.addMethod(name, field.getType());
		}

		clazz.setPackageName("br.com.caelum.stella.nfe.builder.generated");

		String mainPath = "src/main/java/";
		String testPath = "src/test/java/";
		String packagePath = clazz.getPackageName().replace('.', '/') + "/";
		String extension = ".java";
		String implementationPathName = mainPath + packagePath
				+ clazz.getImplementationName() + extension;
		String interfacePathName = mainPath + packagePath
				+ clazz.getInterfaceName() + extension;
		String interfaceTestPathName = testPath + packagePath
				+ clazz.getInterfaceTestName() + extension;

		generateSourceCode(clazz.getInterface(), interfacePathName);
		generateSourceCode(clazz.getImplementation(), implementationPathName);
		generateSourceCode(clazz.getInterfaceTest(), interfaceTestPathName);

		System.out.println(clazz.getInterface());
		System.out.println(clazz.getImplementation());
		System.out.println(clazz.getInterfaceTest());
	}

	private static void generateSourceCode(String code, String pathName)
			throws FileNotFoundException {
		if (enableFileGeneration) {
			File impl = new File(pathName);
			PrintStream implPrintStream = new PrintStream(impl);
			implPrintStream.print(code);
		}
	}

}