package com.github.lybgeek.common.swagger.util;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.net.URL;

public class SwaggerUtil {

	private static final String url = "http://127.0.0.1:8080/v2/api-docs";
	/**
	 * 生成AsciiDocs格式文档
	 * @throws MalformedURLException
	 */
	public static void generateAsciiDocs() throws MalformedURLException {
		Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
				.withMarkupLanguage(MarkupLanguage.ASCIIDOC)
				.withOutputLanguage(Language.ZH)
				.withPathsGroupedBy(GroupBy.TAGS)
				.withGeneratedExamples()
				.withoutInlineSchema().build();

		Swagger2MarkupConverter.from(new URL(url))
				.withConfig(config)
				.build()
				.toFolder(Paths.get("./docs/asciidoc/generated"));
	}
	/**
	 * 生成AsciiDocs格式文档,并汇总成一个文件
	 * @throws MalformedURLException
	 */
	public static void generateAsciiDocsToFile() throws MalformedURLException {
		Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL(url))
                .withConfig(config)
                .build()
                .toFile(Paths.get("./docs/asciidoc/generated/all"));
	}
	
	/**
	 * 生成Markdown格式文档
	 * @throws MalformedURLException
	 */
	public static void generateMarkdownDocs() throws MalformedURLException {
		Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL(url))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("./docs/markdown/generated"));
	}
	/**
	 * 生成Markdown格式文档,并汇总成一个文件
	 * @throws MalformedURLException
	 */
	public static void generateMarkdownDocsToFile() throws MalformedURLException {
		Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL(url))
                .withConfig(config)
                .build()
                .toFile(Paths.get("./docs/markdown/generated/all"));
	}
	
	/**
	 * 生成Confluence格式文档
	 * @throws MalformedURLException
	 */
	public static void generateConfluenceDocs() throws MalformedURLException {
		Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.CONFLUENCE_MARKUP)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL(url))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("./docs/confluence/generated"));
	}
	
	/**
	 * 生成Confluence格式文档,并汇总成一个文件
	 * @throws MalformedURLException
	 */
	public static void generateConfluenceDocsToFile() throws MalformedURLException {
		Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.CONFLUENCE_MARKUP)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL(url))
                .withConfig(config)
                .build()
                .toFile(Paths.get("./docs/confluence/generated/all"));
	}
	
	
}