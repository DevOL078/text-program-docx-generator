package ru.croc;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFAbstractNum;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFNumbering;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAbstractNum;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTLvl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTMultiLevelType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTNumPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabs;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLevelSuffix;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMultiLevelType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STStyleType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class DocumentGenerator {

    private static final String SUBTITLE_STYLE_1_NAME = "Subtitle-Style1";
    private static final String SUBTITLE_STYLE_2_NAME = "Subtitle-Style2";
    private static final String SUBTITLE_STYLE_3_NAME = "Subtitle-Style3";
    private final List<String> srcExtensions;
    private final List<String> migrationExtensions;
    private BigInteger subtitleNumId;

    public DocumentGenerator(List<String> srcExtensions, List<String> migrationExtensions) {
        this.srcExtensions = srcExtensions;
        this.migrationExtensions = migrationExtensions;
    }

    public void init(XWPFDocument document) {
        initSettings(document);
        initStyles(document);
    }

    private void initStyles(XWPFDocument document) {
        XWPFStyles styles = document.createStyles();

        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        cTAbstractNum.setAbstractNumId(BigInteger.valueOf(5));
        CTMultiLevelType ctMultiLevelType = cTAbstractNum.addNewMultiLevelType();
        ctMultiLevelType.setVal(STMultiLevelType.MULTILEVEL);

        CTLvl cTLvl = cTAbstractNum.addNewLvl();
        cTLvl.setIlvl(BigInteger.valueOf(0));
        cTLvl.addNewStart().setVal(BigInteger.valueOf(1));
        cTLvl.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl.addNewPStyle().setVal(SUBTITLE_STYLE_1_NAME);
        cTLvl.addNewSuff().setVal(STLevelSuffix.SPACE);
        cTLvl.addNewLvlText().setVal("%1.");
        cTLvl.addNewLvlJc().setVal(STJc.LEFT);

        CTInd ctInd = cTLvl.addNewPPr().addNewInd();
        ctInd.setLeft(BigInteger.valueOf(0));
        ctInd.setFirstLine(BigInteger.valueOf(0));

        CTLvl cTLvl2 = cTAbstractNum.addNewLvl();
        cTLvl2.setIlvl(BigInteger.valueOf(1));
        cTLvl2.addNewStart().setVal(BigInteger.valueOf(1));
        cTLvl2.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl2.addNewPStyle().setVal(SUBTITLE_STYLE_2_NAME);
        cTLvl2.addNewSuff().setVal(STLevelSuffix.SPACE);
        cTLvl2.addNewLvlText().setVal("%1.%2.");
        cTLvl2.addNewLvlJc().setVal(STJc.LEFT);

        CTInd ctInd2 = cTLvl2.addNewPPr().addNewInd();
        ctInd2.setLeft(BigInteger.valueOf(0));
        ctInd2.setFirstLine(BigInteger.valueOf(851));

        CTLvl cTLvl3 = cTAbstractNum.addNewLvl();
        cTLvl3.setIlvl(BigInteger.valueOf(2));
        cTLvl3.addNewStart().setVal(BigInteger.valueOf(1));
        cTLvl3.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
        cTLvl3.addNewPStyle().setVal(SUBTITLE_STYLE_3_NAME);
        cTLvl3.addNewSuff().setVal(STLevelSuffix.SPACE);
        cTLvl3.addNewLvlText().setVal("%1.%2.%3.");
        cTLvl3.addNewLvlJc().setVal(STJc.LEFT);

        CTInd ctInd3 = cTLvl3.addNewPPr().addNewInd();
        ctInd3.setLeft(BigInteger.valueOf(0));
        ctInd3.setFirstLine(BigInteger.valueOf(851));

        XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);
        XWPFNumbering numbering = document.createNumbering();
        BigInteger abstractNumID = numbering.addAbstractNum(abstractNum);
        subtitleNumId = numbering.addNum(abstractNumID);

        styles.addStyle(createStyle(SUBTITLE_STYLE_1_NAME, subtitleNumId, BigInteger.valueOf(0)));
        styles.addStyle(createStyle(SUBTITLE_STYLE_2_NAME, subtitleNumId, BigInteger.valueOf(1)));
        styles.addStyle(createStyle(SUBTITLE_STYLE_3_NAME, subtitleNumId, BigInteger.valueOf(2)));
    }

    private XWPFStyle createStyle(String name, BigInteger numId, BigInteger ilvl) {
        CTStyle ctStyle = CTStyle.Factory.newInstance();
        ctStyle.addNewName().setVal(name);
        ctStyle.addNewNext().setVal("a");
        ctStyle.addNewQFormat();

        CTPPr ctpPr = ctStyle.addNewPPr();
        ctpPr.addNewKeepNext();
        ctpPr.addNewKeepLines();
        ctpPr.addNewSuppressAutoHyphens();

        CTRPr ctrPr = ctStyle.addNewRPr();
        ctrPr.addNewB();
        ctrPr.addNewBCs();
        ctrPr.addNewKern().setVal(BigInteger.valueOf(32));
        ctrPr.addNewSz().setVal(BigInteger.valueOf(28));
        ctrPr.addNewSzCs().setVal(BigInteger.valueOf(32));

        CTFonts ctFonts = ctrPr.addNewRFonts();
        ctFonts.setAscii("Times New Roman");
        ctFonts.setEastAsia("Times New Roman");
        ctFonts.setHAnsi("Times New Roman");
        ctFonts.setCs("Arial");

        CTNumPr ctNumPr = ctpPr.addNewNumPr();
        ctNumPr.addNewIlvl().setVal(ilvl);
        ctNumPr.addNewNumId().setVal(numId);

        CTTabs ctTabs = ctpPr.addNewTabs();
        CTTabStop tab = ctTabs.addNewTab();
        tab.setVal(STTabJc.LEFT);
        tab.setPos(BigInteger.valueOf(8931));

        CTSpacing ctSpacing = ctpPr.addNewSpacing();
        ctSpacing.setBefore(BigInteger.valueOf(240));
        ctSpacing.setAfter(BigInteger.valueOf(0));
        ctSpacing.setLine(BigInteger.valueOf(360));
        ctSpacing.setLineRule(STLineSpacingRule.AUTO);

        ctpPr.addNewJc().setVal(STJc.BOTH);

        ctpPr.addNewOutlineLvl().setVal(BigInteger.valueOf(1));

        XWPFStyle xwpfStyle = new XWPFStyle(ctStyle);
        xwpfStyle.setType(STStyleType.PARAGRAPH);
        xwpfStyle.setStyleId(name);

        return xwpfStyle;
    }

    private void initSettings(XWPFDocument document) {
        CTSectPr ctSectPr = document.getDocument().getBody().addNewSectPr();

        CTPageSz ctPageSz = ctSectPr.addNewPgSz();
        ctPageSz.setH(BigInteger.valueOf(16838));
        ctPageSz.setW(BigInteger.valueOf(11906));

        CTPageMar ctPageMar = ctSectPr.addNewPgMar();
        ctPageMar.setTop(BigInteger.valueOf(1134));
        ctPageMar.setRight(BigInteger.valueOf(707));
        ctPageMar.setBottom(BigInteger.valueOf(1134));
        ctPageMar.setLeft(BigInteger.valueOf(1701));
        ctPageMar.setHeader(BigInteger.valueOf(708));
        ctPageMar.setFooter(BigInteger.valueOf(708));
        ctPageMar.setGutter(BigInteger.valueOf(0));

        ctSectPr.addNewCols().setSpace(BigInteger.valueOf(708));
        ctSectPr.addNewDocGrid().setLinePitch(BigInteger.valueOf(360));
    }

    public void insertTitle(XWPFDocument document) {
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleRun = title.createRun();
        titleRun.setText("ТЕКСТ ПРОГРАММЫ");
        titleRun.setBold(true);
        titleRun.setFontFamily("Times New Roman");
        titleRun.setFontSize(16);
    }

    public void insertProject(XWPFDocument document, Path projectPath, String project) {
        Path srcPath = projectPath.resolve(Path.of("src"));
        if (!srcPath.toFile().exists()) {
            System.out.printf("Microservice %s: src directory not found\n", project);
            return;
        }

        // Вставляем заголовок для микросервиса
        insertBoldSubtitle(document, String.format("Микросервис «%s»", project));
        insertText(document, "Назначение: ");

        // Сканируем интеграционные контракты
        Path contractsPath = srcPath.resolve("contracts");
        if (contractsPath.toFile().exists()) {
            try (Stream<Path> paths = Files.walk(contractsPath)) {
                List<Path> files = paths
                        .filter(p -> p.toFile().isFile())
                        .filter(p -> hasAnyExtension(p, srcExtensions))
                        .map(srcPath::relativize)
                        .toList();
                if (!files.isEmpty()) {
                    insertSubtitle(document, "Файлы с интеграционными контрактами:");
                    insertFileNames(document, files);
                } else {
                    System.out.printf("Microservice %s: contracts files not found\n", project);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.printf("Microservice %s: contracts directory not found\n", project);
        }

        // Сканируем исходники и скрипты миграции
        Path mainPath = srcPath.resolve("main");
        if (mainPath.toFile().exists()) {
            try (Stream<Path> paths = Files.walk(mainPath)) {
                List<Path> files = paths
                        .filter(p -> p.toFile().isFile())
                        .filter(p -> hasAnyExtension(p, srcExtensions))
                        .map(srcPath::relativize)
                        .toList();
                if (!files.isEmpty()) {
                    insertSubtitle(document, "Файлы с исходным кодом сервиса:");
                    insertFileNames(document, files);
                } else {
                    System.out.printf("Microservice %s: src/main files not found\n", project);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try (Stream<Path> paths = Files.walk(mainPath)) {
                List<Path> files = paths
                        .filter(p -> p.toFile().isFile())
                        .filter(p -> hasAnyExtension(p, migrationExtensions))
                        .map(srcPath::relativize)
                        .toList();
                if (!files.isEmpty()) {
                    insertSubtitle(document, "Файлы со скриптами миграции:");
                    insertFileNames(document, files);
                } else {
                    System.out.printf("Microservice %s: migration files not found\n", project);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.printf("Microservice %s: src/main directory not found\n", project);
        }

        // Для UI отдельная ветка
        // Если тут пропущена какая-то директория с исходниками из src, которая должна быть просканирована,
        // нужно добавить еще одну переменную Path и добавить ее ниже в generalStream
        Path uiAppPath = srcPath.resolve("app");
        Path assetsPath = srcPath.resolve("assets");
        Path commonUiPath = srcPath.resolve("common-ui");
        Stream<Path> generalStream = Stream.of(
                uiAppPath,
                assetsPath,
                commonUiPath
        );
        if (uiAppPath.toFile().exists()) {
            try (Stream<Path> paths = generalStream
                    .filter(p -> p.toFile().exists())
                    .map(p -> {
                        try {
                            return Files.walk(p);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .reduce(Stream::concat)
                    .get()) {
                List<Path> files = paths
                        .filter(p -> p.toFile().isFile())
                        .filter(p -> hasAnyExtension(p, srcExtensions))
                        .map(srcPath::relativize)
                        .toList();
                if (!files.isEmpty()) {
                    insertSubtitle(document, "Файлы с исходным кодом сервиса:");
                    insertFileNames(document, files);
                } else {
                    System.out.printf("Microservice %s: src/app files not found\n", project);
                }
            }
        } else {
            System.out.printf("Microservice %s: src/app directory not found\n", project);
        }

        // Сканируем файлы с тестами
        Path testPath = srcPath.resolve(Path.of("test"));
        if (testPath.toFile().exists()) {
            try (Stream<Path> paths = Files.walk(testPath)) {
                List<Path> files = paths
                        .filter(p -> p.toFile().isFile())
                        .filter(p -> hasAnyExtension(p, srcExtensions))
                        .map(srcPath::relativize)
                        .toList();
                if (!files.isEmpty()) {
                    insertSubtitle(document, "Файлы с модульными и интеграционными тестами:");
                    insertFileNames(document, files);
                } else {
                    System.out.printf("Microservice %s: test files not found\n", project);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.printf("Microservice %s: test directory not found\n", project);
        }
    }

    private void insertBoldSubtitle(XWPFDocument document, String text) {
        XWPFParagraph title = document.createParagraph();

        XWPFRun titleRun = title.createRun();
        titleRun.setText(text);
        titleRun.setBold(true);
        titleRun.setFontFamily("Times New Roman");
        titleRun.setFontSize(14);

        title.setStyle(SUBTITLE_STYLE_2_NAME);
        title.setNumID(subtitleNumId);
        title.setIndentationFirstLine(0);
    }

    private void insertText(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();

        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontFamily("Times New Roman");
        run.setFontSize(14);
    }

    private void insertSubtitle(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();

        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontFamily("Times New Roman");
        run.setFontSize(14);
        run.setBold(true);

        paragraph.setStyle(SUBTITLE_STYLE_3_NAME);
        paragraph.setNumID(subtitleNumId);
        paragraph.setIndentationFirstLine(0);
    }

    private void insertFileNames(XWPFDocument document, List<Path> paths) {
        CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
        cTAbstractNum.setAbstractNumId(BigInteger.valueOf(0));
        CTLvl cTLvl = cTAbstractNum.addNewLvl();
        cTLvl.setIlvl(BigInteger.valueOf(0));
        cTLvl.addNewNumFmt().setVal(STNumberFormat.BULLET);
        cTLvl.addNewLvlText().setVal("-");
        cTLvl.addNewLvlJc().setVal(STJc.LEFT);

        XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);
        XWPFNumbering numbering = document.createNumbering();
        BigInteger abstractNumID = numbering.addAbstractNum(abstractNum);
        BigInteger numId = numbering.addNum(abstractNumID);

        for (Path path : paths) {
            insertOneFileName(document, numId, path.toString());
        }
    }

    private void insertOneFileName(XWPFDocument document, BigInteger numId, String text) {
        XWPFParagraph bulletedPara = document.createParagraph();
        XWPFRun titleRun = bulletedPara.createRun();
        titleRun.setText(text);
        titleRun.setFontFamily("Times New Roman");
        titleRun.setFontSize(14);
        bulletedPara.setNumID(numId);

        CTSpacing ctSpacing = bulletedPara.getCTP().getPPr().addNewSpacing();
        ctSpacing.setLine(BigInteger.valueOf(360));
        ctSpacing.setLineRule(STLineSpacingRule.AUTO);
        ctSpacing.setAfter(BigInteger.valueOf(0));

        bulletedPara.getCTP().getPPr().addNewInd();

        bulletedPara.getCTP().getPPr().addNewJc().setVal(STJc.BOTH);
    }

    private boolean hasAnyExtension(Path path, List<String> extensions) {
        boolean result = false;
        for (String extension : extensions) {
            result = result || path.toAbsolutePath().toString().endsWith(extension);
        }
        return result;
    }

}
