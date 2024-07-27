package com.github.lybgeek.validate.util;

public class SnakeToKebabConverter {

    /**
     * Converts a snake_case string to kebab-case.
     *
     * @param snakeCase the snake_case string to convert
     * @return the converted kebab-case string
     */
    public static String snakeToKebab(String snakeCase) {
        if (snakeCase == null || snakeCase.isEmpty()) {
            return snakeCase;
        }
        // Remove leading and trailing underscores
        snakeCase = snakeCase.replaceAll("^_+|_+$", "");
        // Replace consecutive underscores with a single dash
        snakeCase = snakeCase.replaceAll("_+", "-");
        return snakeCase;
    }

    public static void main(String[] args) {
        String snakeCase = "example_snake_case_string";
        String kebabCase = snakeToKebab(snakeCase);
        System.out.println(kebabCase); // Output: example-snake-case-string
    }
}