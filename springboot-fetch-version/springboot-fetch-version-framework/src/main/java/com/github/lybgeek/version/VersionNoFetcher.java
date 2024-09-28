package com.github.lybgeek.version;

import com.github.lybgeek.version.model.VersionMeta;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.CodeSource;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public final class VersionNoFetcher {

	private VersionNoFetcher() {
	}

	/**
	 * Return the full version string of the present codebase, or {@code null}
	 * if it cannot be determined.
	 * @return the version or {@code null}
	 * @see Package#getImplementationVersion()
	 */
	public static VersionMeta getVersion(ClassLoader classLoader,String libraryClassName) {
		if(classLoader == null){
			classLoader = Thread.currentThread().getContextClassLoader();
		}
		return determineVersion(classLoader,libraryClassName);
	}


	private static VersionMeta determineVersion(ClassLoader classLoader,String libraryClassName) {
		try {
		Class<?> libraryClass = Class.forName(libraryClassName,true,classLoader);
		String implementationVersion = libraryClass.getPackage().getImplementationVersion();
		if (implementationVersion != null) {
			return new VersionMeta(implementationVersion, libraryClassName,libraryClass.getPackage().getName());
		}
		VersionMeta versionMeta = getVersionMetaFromCodeSource(libraryClassName, libraryClass);
		if(versionMeta != null){
			return versionMeta;
		}
		return getVersionMetaFromManifest(classLoader,libraryClass);

		} catch (Exception ex) {
			return new VersionMeta();
		}

	}

	private static VersionMeta getVersionMetaFromCodeSource(String libraryClassName, Class<?> libraryClass) throws IOException, URISyntaxException {
		String implementationVersion;
		CodeSource codeSource = libraryClass.getProtectionDomain().getCodeSource();
		if (codeSource == null) {
			return null;
		}
		URL codeSourceLocation = codeSource.getLocation();

		URLConnection connection = codeSourceLocation.openConnection();
		if (connection instanceof JarURLConnection) {
			implementationVersion =  getImplementationVersion(((JarURLConnection) connection).getJarFile());
			if (implementationVersion != null){
				return new VersionMeta(implementationVersion, libraryClassName, libraryClass.getPackage().getName());
			}
		}
		try (JarFile jarFile = new JarFile(new File(codeSourceLocation.toURI()))) {
			implementationVersion = getImplementationVersion(jarFile);
			if (implementationVersion != null){
				return new VersionMeta(implementationVersion, libraryClassName, libraryClass.getPackage().getName());
			}
		}
		return null;
	}


	private static VersionMeta getVersionMetaFromManifest(ClassLoader classLoader,Class<?> libraryClass) {

		try (InputStream is = classLoader.getResourceAsStream("META-INF/MANIFEST.MF")) {
			if (is == null) {
				return new VersionMeta();
			}

			// 创建Manifest对象并从输入流中读取
			Manifest manifest = new Manifest(is);
			// 获取主属性集
			Attributes attributes = manifest.getMainAttributes();

			// 从属性集中获取版本号
			String version = attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
			if (version != null) {
				return new VersionMeta(version, libraryClass.getName(), libraryClass.getPackage().getName());
			}
		} catch (Exception e) {

		}
		return new VersionMeta();
	}

	private static String getImplementationVersion(JarFile jarFile) throws IOException {
		return jarFile.getManifest().getMainAttributes().getValue(Attributes.Name.IMPLEMENTATION_VERSION);
	}

}
