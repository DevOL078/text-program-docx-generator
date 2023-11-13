package ru.oleynik;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    // По-умолчанию директории с исходниками будут искаться по пути "./input".
    // Если исходники в другом месте, нужно поменять эту переменную
    static final Path INPUT_PATH = Paths.get(".", "input");
    // Результирующий файл генерируется по пути "./output/text-program.docx".
    // Если при зпуске будет найден такой же файл, он удалится и создастся новый
    static final Path OUTPUT_PATH = Paths.get(".", "output", "text-program.docx");
    // Список директорий (дочерних к INPUT_PATH) с исходниками, которые будут сканироваться
    static final List<String> PROJECT_PATH_LIST = List.of(
            "microservice-A",
            "microservice-B",
            "ui-service"
    );
    // Расширения файлов с исходниками (попадают в разделы Интеграционные контракты, Исходники, Файлы с тестами)
    static final List<String> SRC_EXTENSIONS = List.of(
            ".java",
            ".yml",
            ".properties",
            ".graphqls",
            ".graphql",
            ".html",
            ".scss",
            ".ts",
            ".svg"
    );
    // Расширения файлов со скриптами миграции (попадают только в раздел Файлы миграции)
    static final List<String> MIGRATION_EXTENSIONS = List.of(
            ".sql",
            ".xml"
    );

    public static void main(String[] args) {
        if (OUTPUT_PATH.toFile().delete()) {
            System.out.println("Old file has been deleted");
        }

        try (XWPFDocument document = new XWPFDocument();
             FileOutputStream out = new FileOutputStream(OUTPUT_PATH.toFile())
        ) {
            DocumentGenerator generator = new DocumentGenerator(SRC_EXTENSIONS, MIGRATION_EXTENSIONS);
            generator.init(document);

            generator.insertTitle(document);

            for (String project : PROJECT_PATH_LIST) {
                System.out.printf("Microservice %s: start\n", project);
                generator.insertProject(document, INPUT_PATH.resolve(project), project);
                System.out.printf("Microservice %s: finish\n", project);
            }

            document.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}